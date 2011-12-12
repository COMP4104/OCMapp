import java.util.List;

import javax.jms.JMSException;

import server.Server;

import database.Database;

import JMS.Body;
import JMS.Header;
import JMS.JMSController;
import JMS.Message;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Server server = new Server();

		// // Testing stuff

		Message m = JMSController.createMessage(new Header("TRIPREQUEST",
				-1, "Client"), new Body("87-115"));
		try
		{
			server.controller.createServerProducer("SERVER");
			server.controller.sendToAllProducers(m.getHeader().getType(),
					(String) m.thingToSend());
		} catch (JMSException e)
		{
			e.printStackTrace();
		}
	}

}
