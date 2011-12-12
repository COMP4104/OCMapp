package events;

import javax.jms.Message;

public interface EventHandler
{

	public void dispatch(Message m);

	public String getName();

	public void setName(String name);

}
