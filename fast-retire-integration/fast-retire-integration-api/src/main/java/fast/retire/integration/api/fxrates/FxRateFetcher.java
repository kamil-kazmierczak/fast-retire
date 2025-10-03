package fast.retire.integration.api.fxrates;

// https://api.fxratesapi.com/latest?api_key=fxr_live_d374de9d27c3038b38513faad079dcfe6026&currencies=PLN&base=USD
public interface FxRateFetcher {

    FxRateResponse fetch(FxRateRequest request) throws Exception;
}
