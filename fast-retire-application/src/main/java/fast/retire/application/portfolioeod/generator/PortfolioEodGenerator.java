package fast.retire.application.portfolioeod.generator;

import fast.retire.api.currencyrates.CurrencyRate;
import fast.retire.api.currencyrates.CurrencyRateRepository;
import fast.retire.api.register.HistoryRegister;
import fast.retire.api.register.HistoryRegisterRepository;
import fast.retire.api.register.Price;
import fast.retire.application.assetaction.Trade;
import fast.retire.application.assetaction.TradeRepository;
import fast.retire.application.assetaction.TradeType;
import fast.retire.application.portfolioeod.PortfolioEod;
import fast.retire.application.portfolioeod.PortfolioEodRepository;
import fast.retire.application.user.User;
import fast.retire.application.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.Jar;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PortfolioEodGenerator {

    private final PortfolioEodRepository portfolioEodRepository;
    private final UserRepository userRepository;
    private final HistoryRegisterRepository historyRegisterRepository;
    private final CurrencyRateRepository currencyRateRepository;

    public List<PortfolioEod> generate(String userId, String targetCurrency) {
        List<PortfolioEod> portfolioEods = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow();
        List<Trade> trades = user.getTrades().stream()
                .sorted(Comparator.comparing(Trade::getDate))
                .toList();

        if (trades.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, List<Trade>> tradesPerAsset = trades.stream()
                .collect(Collectors.groupingBy(Trade::getAsset));

        for (String asset : tradesPerAsset.keySet()) {
            List<Trade> assetTrades = tradesPerAsset.get(asset).stream()
                    .sorted(Comparator.comparing(Trade::getDate))
                    .toList();

            LocalDate startDate = assetTrades.getFirst().getDate();
            LocalDate endDate = LocalDate.now();

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                LocalDate finalDate = date;
                List<Trade> effectiveTrades = assetTrades.stream()
                        .filter(trade -> finalDate.isAfter(trade.getDate()) || finalDate.isEqual(trade.getDate()))
                        .sorted(Comparator.comparing(Trade::getDate))
                        .toList();

                Optional<PortfolioEod> portfolioEodOpt = computePortfolio(asset, effectiveTrades, date, user, targetCurrency);

                if (portfolioEodOpt.isEmpty()) {
                    continue;
                }

                portfolioEods.add(portfolioEodOpt.get());
            }

        }

        portfolioEodRepository.deletePortfolioEodsByUser_Id(userId);
        portfolioEodRepository.flush();

        portfolioEodRepository.saveAll(portfolioEods);
        return portfolioEods;
    }

    private Optional<PortfolioEod> computePortfolio(String asset, List<Trade> trades,
                                                    LocalDate date,
                                                    User user,
                                                    String targetCurrency) {
        BigDecimal amount = new BigDecimal("0.00");
        for (var trade : trades) {
            if (TradeType.BUY.equals(trade.getTradeType())) {
                amount = amount.add(trade.getAmount());
            } else {
                amount = amount.subtract(trade.getAmount());
            }
        }


        Optional<HistoryRegister> historyPriceOpt = historyRegisterRepository
                .getHistoryRegisterByAssetAndRegisterDate(asset, date);

        if (historyPriceOpt.isEmpty()) {
            return Optional.empty();
        }

        HistoryRegister historyRegister = historyPriceOpt.get();
        Price historyPrice = historyRegister.getPrice();
        BigDecimal computedValue = amount.multiply(historyPrice.getValue());
        String currency = historyPrice.getCurrency();

        if (!targetCurrency.equals(currency)) {
            var currencyRate = currencyRateRepository
                    .findCurrencyRateByBaseCurrencyAndTargetCurrencyAndDate(historyPrice.getCurrency(),
                    targetCurrency, date);
            if (currencyRate.isEmpty()) {
                return Optional.empty();
            }

            computedValue = computedValue.multiply(currencyRate.get().getAmount());
            currency = currencyRate.get().getTargetCurrency();
        }

        return Optional.of(
                PortfolioEod.builder()
                        .id(UUID.randomUUID().toString())
                        .date(date)
                        .user(user)
                        .amount(amount)
                        .asset(asset)

                        .computedValue(computedValue)
                        .currency(currency)

                        .build()
        );

    }


}
