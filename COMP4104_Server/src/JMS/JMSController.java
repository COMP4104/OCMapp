package JMS;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import events.EventHandler;

public class JMSController
{

	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private ArrayList<Destination> listeners; // maybe create class that
												// implements Destination?

	private Destination myListener;

	private ArrayList<MessageProducer> producers;

	private HashMap<String, EventHandler> handlers;

	private String brokerURL = "";

	public void createBroker(int port) throws Exception
	{
		BrokerService broker = new BrokerService();
		broker.setUseJmx(true);
		brokerURL = "tcp://localhost:" + port;
		broker.addConnector(brokerURL);
		broker.start();
	}

	public JMSController(HashMap<String, EventHandler> handlers)
			throws JMSException, InterruptedException
	{
		this.handlers = handlers;
		producers = new ArrayList<MessageProducer>();
		listeners = new ArrayList<Destination>();
	}

	public EventHandler getEventHandler(String name)
	{
		return handlers.get(name);
	}

	public void setUp(String myname) throws JMSException, InterruptedException
	{
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				ActiveMQConnection.DEFAULT_BROKER_URL);

		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		myListener = session.createQueue(myname);
		createConsumer(myListener);

	}

	// only used by servers
	public void addListener(String destName) throws JMSException,
			InterruptedException
	{
		Destination d = session.createQueue(destName);
		listeners.add(d);
		createConsumer(d);
	}

	public void createConsumer(Destination d) throws JMSException,
			InterruptedException
	{

		connection = connectionFactory.createConnection();
		connection.start();
		MessageConsumer consumer = session.createConsumer(d);
		Consumer myConsumer = new Consumer(this, session);
		connection.setExceptionListener(myConsumer);
		consumer.setMessageListener(myConsumer);
	}

	public void createServerProducer(String destname) throws JMSException
	{
		Destination destination = session.createQueue(destname);
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		producers.add(producer);
	}

	public void removeServerProducer(String destname) throws JMSException
	{
		Destination destination = session.createQueue(destname);
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		producers.remove(producer);
	}

	public void addProducer(Destination d) throws JMSException
	{
		producers.add(session.createProducer(d));
	}

	public void createProducerAndSendMessage(Destination destination,
			String type, Message messagebody) throws JMSException
	{
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		sendMessage(producer, type, messagebody);
	}

	public void sendMessage(MessageProducer producer, String type,
			Message messagebody) throws JMSException
	{
		producer.send(messagebody);
	}

	// clients use this to send to the server they are connected to
	// servers use this to send to all servers
	public void sendToAllProducers(String type, String messagebody)
			throws JMSException
	{

		for (MessageProducer p : producers)
		{
			System.out.println("sending to producer");
			sendTextMessage(p, type, messagebody);
		}
	}

	private void sendTextMessage(MessageProducer p, String type,
			String messagebody) throws JMSException
	{
		TextMessage message = session.createTextMessage(messagebody);
		message.setJMSType(type);
		message.setJMSReplyTo(myListener);
		p.send(message);
	}

	public static JMS.Message createMessage(Header header, Body body)
	{
		try
		{
			Class<?> MessageClass = Class.forName("JMS.HTTPMessage");
			Constructor<?> constructor = MessageClass.getConstructor(
					header.getClass(), body.getClass());

			JMS.Message message = (JMS.Message) constructor.newInstance(header,
					body);
			return message;

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public MapMessage createMapMessage(String type) throws JMSException
	{
		MapMessage message = session.createMapMessage();
		message.setJMSType(type);
		message.setJMSReplyTo(myListener);
		return message;
	}

}
