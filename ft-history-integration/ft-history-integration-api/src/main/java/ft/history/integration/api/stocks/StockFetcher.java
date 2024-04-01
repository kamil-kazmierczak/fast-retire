package ft.history.integration.api.stocks;

public interface StockFetcher {

    StockResponse fetch(StockRequest request) throws Exception;
}
