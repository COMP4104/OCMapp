package JMS;



import java.io.Serializable;

public class Body implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	public Body()
	{
		
	}
	public Body(String message)
	{
		this.message = message;
		
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}