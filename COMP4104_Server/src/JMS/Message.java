package JMS;


public abstract class Message
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	Header header;
	Body body;

	public Message(Header header, Body body)
	{
		this.header = header;
		this.body = body;
	}

	public Header getHeader()
	{
		return header;
	}

	public void setHeader(Header header)
	{
		this.header = header;
	}

	public Body getBody()
	{
		return body;
	}

	public void setBody(Body body)
	{
		this.body = body;
	}

	public Object thingToSend()
	{
		return null;
	}

	public abstract Object decodeMessage(Object m);
	
	public String toString()
	{
		return ("Message: " + header.getType() + " from: "+ header.senderName + "to "+ header.destID + "Body: "+ body.getMessage());
	}

}
