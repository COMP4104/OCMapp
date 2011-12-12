package JMS;



import java.io.Serializable;

public class Header implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	int senderID;
	int destID;
	String senderName;

	public Header()
	{

	}

	public Header(String type, int senderID, int destID, String senderName)
	{
		this.type = type;
		this.senderID = senderID;
		this.senderName = senderName;
		this.destID = destID;

	}

	public Header(String type, int senderID, String senderName)
	{
		this(type, senderID, -1, senderName);
	}

	public int getDestID()
	{
		return destID;
	}

	public void setDestID(int destID)
	{
		this.destID = destID;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public void setSenderName(String senderName)
	{
		this.senderName = senderName;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getSenderID()
	{
		return senderID;
	}

	public void setSenderID(int senderID)
	{
		this.senderID = senderID;
	}

}