import java.io.Serializable;


public class Event implements Serializable  {
	
	String name,startTime, endTime, location, description;
	
	public Event(String n, String sT, String eT, String l, String d)
	{
		name = n;
		startTime = sT;
		endTime = eT;
		location = l;
		description = d;
	}
	
	public void setName (String n)
	{
		name = n;
	}
	
	public void setStartTime (String sT)
	{
		startTime = sT;
	}
	
	public void setEndTime (String eT)
	{
		endTime = eT;
	}
	
	public void setLocation (String l)
	{
		location = l;
	}
	
	public void setDescription (String d)
	{
		description = d;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getStartTime ()
	{
		return startTime;
	}
	
	public String getEndTime ()
	{
		return endTime;
	}

	public String getLocation()
	{
		return location;
	}
	
	public String getDescription()
	{
		return description;
	}
}
