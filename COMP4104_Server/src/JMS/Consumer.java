package JMS;


import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Consumer implements MessageListener, ExceptionListener
{

	JMSController controller;
	Session session;

	public Consumer(JMSController controller, Session session)
	{
		this.controller = controller;
		this.session = session;
	}

	public void onMessage(javax.jms.Message message)
	{

			try
			{
				if (controller.getEventHandler(message.getJMSType()) != null)
					controller.getEventHandler(message.getJMSType()).dispatch(
							message);
				else
				{
					System.out.println("Message Type Not Found");
				}
			} catch (JMSException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public void onException(JMSException e)
	{

		e.printStackTrace();

	}
}
