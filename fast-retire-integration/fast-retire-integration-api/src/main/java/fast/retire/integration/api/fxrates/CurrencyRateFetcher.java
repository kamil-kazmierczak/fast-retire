package fast.retire.integration.api.fxrates;

public interface CurrencyRateFetcher {

    CurrencyRateResponse fetch(CurrencyRateRequest request) throws Exception;
}
