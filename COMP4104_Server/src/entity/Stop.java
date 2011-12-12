package entity;

public class Stop
{
	private String id;
	private Integer stopCode;
	private String stopName;
	private Float longitude;
	private Float latitude;

	public Stop()
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

	public Integer getStopCode()
	{
		return stopCode;
	}

	public void setStopCode(Integer stopCode)
	{
		this.stopCode = stopCode;
	}

	public String getStopName()
	{
		return stopName;
	}

	public void setStopName(String stopName)
	{
		this.stopName = stopName;
	}

	public Float getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Float longitude)
	{
		this.longitude = longitude;
	}

	public Float getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Float latitude)
	{
		this.latitude = latitude;
	}

}
