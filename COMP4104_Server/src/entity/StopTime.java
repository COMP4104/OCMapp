package entity;

import java.io.Serializable;
import java.sql.Time;

public class StopTime implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tripId;
	private Integer stopSequence;
	private Time arriveTime;
	private Time departTime;
	private String stopId;

	public StopTime()
	{

	}

	public String getTripId()
	{
		return tripId;
	}

	public void setTripId(String tripId)
	{
		this.tripId = tripId;
	}

	public Integer getStopSequence()
	{
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence)
	{
		this.stopSequence = stopSequence;
	}

	public Time getArriveTime()
	{
		return arriveTime;
	}

	public void setArriveTime(Time arriveTime)
	{
		this.arriveTime = arriveTime;
	}

	public Time getDepartTime()
	{
		return departTime;
	}

	public void setDepartTime(Time departTime)
	{
		this.departTime = departTime;
	}

	public String getStopId()
	{
		return stopId;
	}

	public void setStopId(String stopId)
	{
		this.stopId = stopId;
	}

	public String toString()
	{
		return this.stopId + " " + this.tripId + " " + this.stopSequence;
	}

}
