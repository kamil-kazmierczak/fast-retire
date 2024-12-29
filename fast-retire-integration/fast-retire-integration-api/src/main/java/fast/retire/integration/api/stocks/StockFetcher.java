package fast.retire.integration.api.stocks;

public interface StockFetcher {

    StockResponse fetch(StockRequest request) throws Exception;
}
