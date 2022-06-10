package quote;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface StockPriceService {
    @WebMethod
    double GetStockQuote(String StockName);
}
