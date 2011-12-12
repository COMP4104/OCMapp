package JMS;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class HTTPMessage extends Message implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String html;
	private static int messageID = 0;

	public HTTPMessage(Header header, Body body)
	{
		super(header, body);
		html = "HTML version 1.0 \n" + header.senderID + "\n"
				+ header.senderName + "\n" + header.destID + "\n"
				+ header.type + "\n\n" + body.message;
		// TODO Auto-generated constructor stub
		
		messageID ++;
	}

	public Object thingToSend()
	{
		return html;
	}

	public HTTPMessage decodeMessage(Object m)
	{
		String http = null;
		try
		{
			http = ((TextMessage) m).getText();
		} catch (JMSException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new StringReader(http));
		String next = null;
		try
		{
			next = br.readLine();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		String type=null, senderName=null, text=null;
		int senderId=0, destId=0;
		while (next != null)
		{
			if (i == 1)
				senderId = Integer.parseInt(next);
			if (i == 2)
				senderName = next;
			if (i == 3)
				destId = Integer.parseInt(next);
			if (i == 4)
				type = next;
			if (i == 6)
				text = next;
			try
			{
				next = br.readLine();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		HTTPMessage message = new HTTPMessage(new Header(type, senderId,
				destId, senderName), new Body(text));
		return message;
	}
}
