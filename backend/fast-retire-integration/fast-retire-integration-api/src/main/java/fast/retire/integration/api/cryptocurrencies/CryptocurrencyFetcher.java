package fast.retire.integration.api.cryptocurrencies;

public interface CryptocurrencyFetcher {

    CryptocurrencyResponse fetch(CryptocurrencyRequest request) throws Exception;
}
