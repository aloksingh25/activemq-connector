package io.camunda.connector.activemq.outbound;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;

import io.camunda.connector.activemq.common.model.ActiveMqDestinationType;
import io.camunda.connector.activemq.outbound.model.ActiveMqRequest;
import io.camunda.connector.activemq.supplier.ConnectionFactorySupplier;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;

import jakarta.jms.Connection;
import jakarta.jms.DeliveryMode;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.TopicSubscriber;


@OutboundConnector(
		name = "ActiveMQ Producer",
		inputVariables = {"authentication", "routing", "message"},
		type = "io.camunda:connector-activemq:1")
@ElementTemplate(
		id = "io.camunda.connector.activemq.Template.v1",
		name = "ActiveMQ Producer Connector",
		version = 1,
		description = "ActiveMQ Producer",
		icon = "icon.svg",
		documentationRef = "https://docs.camunda.io/docs/components/connectors/out-of-the-box-connectors/available-connectors-overview/",
		propertyGroups = {
				@ElementTemplate.PropertyGroup(id = "authentication", label = "Authentication"),
				@ElementTemplate.PropertyGroup(id = "Routing", label = "routing")
		},
		inputDataClass = ActiveMqRequest.class)
public class ActiveMqFunction implements OutboundConnectorFunction {

	private static final String NON_PERSISTENT = "NON_PERSISTENT";

	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqFunction.class);

	private final ConnectionFactorySupplier connectionFactorySupplier;

	public ActiveMqFunction() {
		this.connectionFactorySupplier = new ConnectionFactorySupplier();
	}

	public ActiveMqFunction(final ConnectionFactorySupplier connectionFactorySupplier) {
		this.connectionFactorySupplier = connectionFactorySupplier;
	}

	@Override
	public Object execute(OutboundConnectorContext context) throws JMSException, Exception {
		final var connectorRequest = context.bindVariables(ActiveMqRequest.class);
		return executeConnector(connectorRequest);
	}

	private ActiveMqResult executeConnector(final ActiveMqRequest request) throws JMSException, Exception {
		LOGGER.info("Executing ActiveMqConnector with request {}", request);
	
		try (Connection connection = openConnection(request)) {
			connection.setClientID("myuniqueid");
			connection.start();
			
			try(Session session = openSession(connection)) { 
				//To verify Topic messages only
				if(request.getRouting().getDestinationType() == ActiveMqDestinationType.topic) {
					consumeMessage(session, request.getRouting().getTopicname());
				}
				try{
					Destination destination = getDestination(session, request); 
					try (MessageProducer producer = session.createProducer(destination)){

						TextMessage message = session.createTextMessage(request.getMessage().getBody().toString());
						
						setMessageDeliveryMode(request, producer);
						producer.send(message);
					}
				}catch(JMSException jmse) {
					throw new Exception(jmse);
				}
				return ActiveMqResult.success();
			}
		}
	}

	private void setMessageDeliveryMode(ActiveMqRequest request, MessageProducer producer) throws JMSException {
		if(request.getMessage().getDeliveryMode().equals(NON_PERSISTENT)) {
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		}
		
	}

	private Destination getDestination(Session session, ActiveMqRequest request) throws JMSException {
		Destination destination = null;
		if(request.getRouting().getDestinationType() == ActiveMqDestinationType.queue) {
			destination = session.createQueue(request.getRouting().getQueuename());
		}else if (request.getRouting().getDestinationType() == ActiveMqDestinationType.topic) {
			destination = session.createTopic(request.getRouting().getTopicname());
		}
		return destination;
	}

	private Session openSession(Connection connection) throws JMSException {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
	}

	private Connection openConnection(ActiveMqRequest request) throws Exception {
		return connectionFactorySupplier
				.createFactory(request.getAuthentication(), request.getRouting())
				.createConnection();
	}
	
	
	
	private static void consumeMessage(Session session, String subject) throws JMSException {
		TopicSubscriber durableSubscriber = session.createDurableSubscriber(session.createTopic(subject), "test_topic_sub");
		
		MessageListener listener = new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				try {
					System.out.println("Message received as: "+((TextMessage)message).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		};		
		durableSubscriber.setMessageListener(listener);
	}
}