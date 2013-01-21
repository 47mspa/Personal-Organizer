import java.io.Serializable;
import java.util.ArrayList;


public class Year implements Serializable {
	
	private int number;
	private ArrayList <Month> months;
	private int lastDayOfYear;
	
	public Year (int num, int firstDayOfYear)
	{
		months = new ArrayList <Month> ();
		number = num; 
		Month January = new Month(1, firstDayOfYear,'n');//first day of the year is also first day of the month of january
		months.add(January);
		//System.out.println(CalendarFunctions.numToMonth(1)+" ,"+CalendarFunctions.numToDay(firstDayOfYear));		
		for (int i = 2;i<=12;i++)//loop to add months to year
		{
			Month previous = months.get(i-2);//get the previous month object, the index is one less so subtract 2
			int lastDay = previous.getLastDay();//get the last day of the previous month
			int firstDayOfMonth = CalendarFunctions.nextDay(lastDay);//first day of new month
			Month next; 
			
			if((i==2)&&(num%4==0))//leap year
				next = new Month(i,firstDayOfMonth,'y');//add object, it is a leap year
			else
				next = new Month(i,firstDayOfMonth,'n');//add object
			
			months.add(next);
			
			//System.out.println(CalendarFunctions.numToMonth(i)+" ,"+CalendarFunctions.numToDay(firstDayOfMonth));//displays month and first day of month
			if (i==12)
				lastDayOfYear = next.getLastDay();
				
			
		}
			
	}
	
	public Year (Year y)
	{
		this.months = y.getMonths();
		this.number = y.getNumber();
		this.lastDayOfYear = y.getLastDayOfYear();
	}
	
	
	public int getNumber()
	{
		return number;
	}
	
	public int getLastDayOfYear ()
	{
		return lastDayOfYear;
	}
	
	public ArrayList <Month> getMonths()
	{
		return months;
	}
	
	public void setMonths(ArrayList <Month> m)
	{
		months = m;
	}
	
		
}
