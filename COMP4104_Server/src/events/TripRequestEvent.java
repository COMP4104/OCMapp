package events;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.TextMessage;

import database.ConvertBytes;

import entity.StopTime;
import entity.Trip;

import JMS.Body;
import JMS.Header;
import JMS.JMSController;
import JMS.Message;

import server.Server;

public class TripRequestEvent implements EventHandler
{
	String name;
	Server server;

	public TripRequestEvent(Server server, String name)
	{
		this.server = server;
		this.name = name;
	}

	public void dispatch(javax.jms.Message inMes)
	{
		Message temp = JMSController.createMessage(new Header(), new Body());
		Message m = null;

		m = (Message) temp.decodeMessage(inMes);
		System.out.println(m.thingToSend());

		String id = m.getBody().getMessage();
		List<Trip> stopTimes = server.getTrips(id);
		byte[] bytes = ConvertBytes.toByteArray(stopTimes);
		MapMessage message;
		try
		{
			message = server.controller.createMapMessage("TRIPRESPONSE");

			message.setBytes("OBJECT", bytes);
			server.controller.createProducerAndSendMessage(
					inMes.getJMSReplyTo(), message.getJMSType(), message);
		} catch (JMSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
