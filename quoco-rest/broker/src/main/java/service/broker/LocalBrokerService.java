package service.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.core.ClientApplication;
import service.core.ClientInfo;
import service.core.NoSuchQuotationException;
import service.core.Quotation;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */

@RestController
public class LocalBrokerService {

	private final HashMap<Integer, ClientApplication> clientMap = new HashMap<>();
	private int applicationNum = 0;
	private ArrayList<String> URIArray;

	@Autowired
	public LocalBrokerService(URIList URIList) {
		this.URIArray = URIList.URIs;
	}

	@RequestMapping(value="/applications",method= RequestMethod.POST)
	public ClientApplication getQuotations(@RequestBody ClientInfo info) {

		ArrayList<Quotation> quotations = new ArrayList<>();

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<ClientInfo> request = new HttpEntity<>(info);

		for(int i = 0; i < URIArray.size(); i++){
			quotations.add(restTemplate.postForObject(URIArray.get(i), request, Quotation.class));
		}

		ClientApplication clientApplication = new ClientApplication(info, quotations, applicationNum);
		clientMap.put(applicationNum, clientApplication);
		applicationNum++;

		return clientApplication;
	}

	@RequestMapping(value="/applications",method= RequestMethod.GET)
	public List<ClientApplication> getClientApplication() {
		return new ArrayList<>(clientMap.values());
	}

	@RequestMapping(value="/applications/{application-number}",method= RequestMethod.GET)
	public ClientApplication getApplication(@PathVariable ("application-number") int applicationNumber) {
		if (!(clientMap.containsKey(applicationNumber))) {
			throw new NoSuchQuotationException();
		}

		return clientMap.get(applicationNumber);
	}
}
