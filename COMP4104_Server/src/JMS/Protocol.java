package JMS;


public class Protocol
{
	public int port;
	public String messageClassName;
	public String messageProtocol;

	public Protocol()
	{

	}

	public Protocol(int port, String messageClassName, String messageProtocol)
	{
		this.port = port;
		this.messageClassName = messageClassName;
		this.messageProtocol = messageProtocol;
	}
}
