package fast.retire.application.portfolioeod.changecalculator;

import fast.retire.application.portfolioeod.PortfolioEod;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PortfolioChangeCalculator {

    public Map<String, PortfolioChanges> calculate(
            List<PortfolioEod> currentPortfolios,
            List<PortfolioEod> dayBeforePortfolios,
            List<PortfolioEod> weekBeforePortfolios,
            List<PortfolioEod> monthBeforePortfolios) {

        Map<String, PortfolioChanges> result = new HashMap<>();

        for (var currentPortfolio : currentPortfolios) {
            var assetName = currentPortfolio.getAssetName();
            BigDecimal currentValue = currentPortfolio.getComputedValue();
            BigDecimal dayBeforeValue = findByAssetName(assetName, dayBeforePortfolios).getComputedValue();
            BigDecimal weekBeforeValue = findByAssetName(assetName, weekBeforePortfolios).getComputedValue();
            BigDecimal monthBeforeValue = findByAssetName(assetName, monthBeforePortfolios).getComputedValue();

            result.put(assetName, PortfolioChanges.builder()
                            .dayBeforeValueChange(computePercentageChange(currentValue, dayBeforeValue))
                            .weekBeforeValueChange(computePercentageChange(currentValue, weekBeforeValue))
                            .monthBeforeValueChange(computePercentageChange(currentValue, monthBeforeValue))
                    .build());
        }
        return result;
    }

    private static PortfolioEod findByAssetName(String assetName, List<PortfolioEod> portfolioEods) {
        return portfolioEods.stream()
                .filter(portfolioEod -> assetName.equals(portfolioEod.getAssetName()))
                .findFirst().get();
    }

    private static BigDecimal computePercentageChange(BigDecimal currentValue, BigDecimal historicalValue) {
        if (currentValue == null
                || historicalValue == null
                || currentValue.equals(BigDecimal.ZERO)
                || historicalValue.equals(BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        if (currentValue.compareTo(historicalValue) > 0) {
            return currentValue
                    .multiply(new BigDecimal(100))
                    .divide(historicalValue, MathContext.DECIMAL32)
                    .subtract(new BigDecimal(100));
        }
        else if (currentValue.compareTo(historicalValue) < 0) {
            return new BigDecimal(100)
                    .subtract(currentValue
                    .multiply(new BigDecimal(100))
                    .divide(historicalValue, MathContext.DECIMAL32));
        }
        else return BigDecimal.ZERO;
    }


}
