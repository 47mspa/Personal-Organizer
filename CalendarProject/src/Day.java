import java.io.Serializable;
import java.util.*;

public class Day implements Serializable  {
	
	private int num;
	private int day;
	private String name;
	private ArrayList <Event> events;
	
	public Day (int n, int d)
	{
		num = n;
		day = d;
		name = CalendarFunctions.numToDay(d);
		events = new ArrayList <Event> ();
	}
	
	public Day (Day d)
	{
		this.num = d.getNum();
		this.day = d.getDay();
		this.name = d.getName();
		this.events = d.getEvents();
	}
	
	public int getNum()
	{
		return num;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<Event> getEvents ()
	{
		return events;
	}
	
	public void addEvent(Event e)
	{
		events.add(e);
	}
	
	

}
