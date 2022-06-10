package quote;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.*;
import javax.xml.ws.Endpoint;
import java.util.Map;
import java.util.TreeMap;

@WebService
@SOAPBinding(style = Style.RPC, use = Use.LITERAL)
public class StockPrice {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:9000/getQuote", new StockPrice());
    }

    private Map<String, Double> prices = new TreeMap<>();

    {
        prices.put("IBM", 143.79);
        prices.put("GOOGL", 1209.70);
        prices.put("MSFT", 137.44);
        prices.put("FB", 175.25);
        prices.put("TWTR", 40.22);
    }

    @WebMethod public double GetStockQuote(String StockName) {
        Double price = prices.get(StockName);
        return price == null ? -1:price;
    }
}

