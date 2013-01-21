import java.io.Serializable;
import java.util.*;

public class Month implements Serializable  {
	
	private ArrayList <Day> days ;            
	private String name;
	private int num;
	private int daysInMonth;
	private int lastDay;
	private int firstDay;
	
	public Month (int num, int firstDayOfMonth, char leap)//method for january of year
	{
		this.num = num;
		this.firstDay = firstDayOfMonth;
		name = CalendarFunctions.numToMonth(num);
		days = new ArrayList <Day> ();// days in month
		if (leap=='n')
			daysInMonth = CalendarFunctions.numberOfDays(num);
		else
			daysInMonth = CalendarFunctions.numberOfDays(-2);

		days.add(new Day (1, firstDayOfMonth));//first day of month		
		
		for (int i = 2 ; i <=daysInMonth; i++)//loop to add days to month
		{
			Day previous = days.get(i-2);//get day before, the index is one less so subtract 2
			int previousDay = previous.getDay();//previous day
			int NextDay = CalendarFunctions.nextDay(previousDay);
			days.add(new Day (i, NextDay));//next day
			
			if (i==daysInMonth)//last day
				lastDay = NextDay;//the last day of the month is the current month			
		}
		
	}
	
	public Month (Month m)
	{
		this.days = m.getDays();
		this.name = m.getName();
		this.daysInMonth = m.getDaysInMonth();
		this.lastDay = m.getLastDay();
		this.num = m.getNum();
	}
	
	public boolean equals (Month m)
	{
		if (m.getNum()==this.getNum())
			return true;
		else 
			return false;
	}
	
	public String getName ()
	{
		return name;
	}
	
	public int getNum()
	{
		return num;
	}
	
	public int getLastDay()
	{
		return lastDay;
	}
	
	public int getDaysInMonth()
	{
		return daysInMonth;
	}
	
	public ArrayList<Day> getDays()
	{
		return days;
	}
	
	public int getFirstDay()
	{
		return firstDay;
	}
	
	public void setDays(ArrayList <Day> list)
	{
		days = list;
	}
	
}
