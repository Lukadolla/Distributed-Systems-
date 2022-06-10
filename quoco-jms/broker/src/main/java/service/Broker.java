package service;

import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.util.concurrent.*;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.*;
import javax.jms.*;
/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */

public class Broker implements BrokerService {

	private static final Map<Long, ClientApplicationMessage> cache = new ConcurrentHashMap<>();

	public static void main(String[] args) throws JMSException {

		ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://"+ getHostFromArgs(args) +":61616");
		Connection connection = factory.createConnection();
		connection.setClientID("broker");
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

		Queue responseQueue = session.createQueue("RESPONSES");
		Queue requestQueue = session.createQueue("REQUESTS");
		Queue quotationsQueue = session.createQueue("QUOTATIONS");
		Topic topic = session.createTopic("APPLICATIONS");

		MessageConsumer requestConsumer = session.createConsumer(requestQueue);
		MessageProducer responseProducer = session.createProducer(responseQueue);
		MessageProducer applicationProducer = session.createProducer(topic);

		connection.start();

		ExecutorService executor = Executors.newFixedThreadPool(75);

		while(true) {

			Message msg = requestConsumer.receive();
			QuotationRequestMessage quotationRequest = null;

			if (msg instanceof ObjectMessage) {
				Object content = ((ObjectMessage) msg).getObject();

				if (content instanceof QuotationRequestMessage) {
					quotationRequest = (QuotationRequestMessage) content;
				}

				msg.acknowledge();

			} else {
				System.out.println("Unknown message type: " + msg.getClass().getCanonicalName());
			}

			msg.acknowledge();

			ClientApplicationMessage applicationMessage = new ClientApplicationMessage(quotationRequest.id, quotationRequest.info, new LinkedList<>());
			cache.put(quotationRequest.id, applicationMessage);

			QuotationRequestMessage finalRequest = quotationRequest;

			executor.submit(() -> {

				try {
					applicationProducer.send(msg);
					MessageConsumer quotationConsumer = session.createConsumer(quotationsQueue, "id = " + finalRequest.id);

					long startTime = System.currentTimeMillis();

					ExecutorService innerExecutor = Executors.newSingleThreadExecutor();

					Future<LinkedList<Quotation>> quotationFutures = innerExecutor.submit(() -> {

						LinkedList<Quotation> quotations = new LinkedList<>();

						while(System.currentTimeMillis() < startTime + 2000) {
							Message quotationMessage = quotationConsumer.receive(200L);

							if (quotationMessage == null) continue;

							if (quotationMessage instanceof ObjectMessage) {
								Object content = ((ObjectMessage) quotationMessage).getObject();

								if (content instanceof QuotationResponseMessage) {
									QuotationResponseMessage message = (QuotationResponseMessage) content;
									quotations.add(message.quotation);
								}
							}
						}
						return quotations;
					});

					ClientApplicationMessage clientApplication = cache.get(finalRequest.id);
					clientApplication.quotations = quotationFutures.get();

					cache.remove(finalRequest.id);
					quotationConsumer.close();

					Message response = session.createObjectMessage(clientApplication);
					responseProducer.send(response);

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}

		/*QuotationRequestMessage quotationRequest =
				new QuotationRequestMessage(SEED_ID++, clients[0]);
		Message request = session.createObjectMessage(quotationRequest);
		cache.put(quotationRequest.id, quotationRequest.info);
		producer.send(request);

		if (message instanceof ObjectMessage) {
			Object content = ((ObjectMessage) message).getObject();
			if (content instanceof QuotationResponseMessage) {
				QuotationResponseMessage response = (QuotationResponseMessage) content;
				ClientInfo info = cache.get(response.id);
				displayProfile(info);
				displayQuotation(response.quotation);
				System.out.println("\n");
			}
			message.acknowledge();
		} else {
			System.out.println("Unknown message type: " +
					message.getClass().getCanonicalName());
		}*/
	}

	@Override
	public List<Quotation> getQuotations(ClientInfo info) {
		return null;
	}

	private static String getHostFromArgs(String[] args){
		String host = "localhost";

		if(args.length > 0){
			host = args[0];
		}

		return host;
	}
}
