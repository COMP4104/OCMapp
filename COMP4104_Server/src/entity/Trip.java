package entity;

import java.io.Serializable;

public class Trip implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String routeId;

	public Trip()
	{

	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getRouteId()
	{
		return routeId;
	}

	public void setRouteId(String routeId)
	{
		this.routeId = routeId;
	}

	public String toString()
	{
		return id + " " + routeId;
	}
}
