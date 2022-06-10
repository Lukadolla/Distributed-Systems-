package service.broker;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class URIList {
    public ArrayList<String> URIs = new ArrayList<>(Arrays.asList(
            "http://auldfellas:8081/quotations",
            "http://dodgydrivers:8082/quotations",
            "http://girlpower:8083/quotations"
    ));
}
