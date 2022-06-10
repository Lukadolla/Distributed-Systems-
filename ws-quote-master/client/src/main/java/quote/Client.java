package quote;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9000;

        // More Advanced flag-based configuration
        for (int i=0; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                    host = args[++i];
                    break;
                case "-p":
                    port = Integer.parseInt(args[++i]);
                    break;
                default:
                    System.out.println("Unknown flag: " + args[i] +"\n");
                    System.out.println("Valid flags are:");
                    System.out.println("\t-h <host>\tSpecify the hostname of the target service");
                    System.out.println("\t-p <port>\tSpecify the port number of the target service");
                    System.exit(0);
            }
        }

        URL wsdlUrl = new
                URL("http://" + host + ":" + port + "/getQuote?wsdl");

        QName serviceName = new QName("http://quote/", "StockPriceService");

        Service service = Service.create(wsdlUrl, serviceName);

        QName portName = new QName("http://quote/", "StockPricePort");
        StockPriceService stockService = service.getPort(portName, StockPriceService.class);

        System.out.println(stockService.GetStockQuote("IBM"));
    }

}
