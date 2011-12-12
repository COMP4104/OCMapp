package events;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.TextMessage;

import database.ConvertBytes;

import entity.Route;
import entity.StopTime;

import JMS.Body;
import JMS.Header;
import JMS.JMSController;
import JMS.Message;

import server.Server;

public class AllRoutesRequestEvent implements EventHandler
{
	String name;
	Server server;

	public AllRoutesRequestEvent(Server server, String name)
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

		List<Route> routes = server.getAllRoutes();
		byte[] bytes = ConvertBytes.toByteArray(routes);
		MapMessage message;
		try
		{
			message = server.controller.createMapMessage("ALLROUTERESPONSE");

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
