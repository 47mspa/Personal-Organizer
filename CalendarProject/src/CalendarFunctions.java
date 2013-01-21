import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public final class CalendarFunctions {

	
	public static String numToDay (int num)
	{
		String name;
		if (num==1)
			name = "Monday";
		else if (num==2)
			name = "Tuesday";
		else if (num==3)
			name = "Wednesday";
		else if (num==4)
			name = "Thursday";
		else if (num==5)
			name = "Friday";
		else if (num==6)
			name = "Saturday";
		else if (num==0) 
			name = "Sunday";
		else
			name = "ERRORR :P";
		return name;		
	}
	
	public static int nextDay(int day)
	{
		
		if (day==6)
			return 0;
		else
			return day+1;
	}
	
	public static int nextMonth (int month)
	{
		if (month==12)
			return 1;
		else 
			return month+1;
	}
	
	public static int previousMonth (int month)
	{
		if (month==1)
			return 12;
		else
			return month-1;
	}
	
	public static int dayToNum(String name)
	{
		int num; 
		if (name.equals("Monday"))
			num = 1;
		else if (name.equals("Tuesday"))
			num = 2;
		else if (name.equals("Wednesday"))
			num=3;
		else if (name.equals("Thursday"))
			num = 4;
		else if (name.equals("Friday"))
			num=5;
		else if (name.equals("Saturday"))
			num = 6;
		else if (name.equals("Sunday"))
			num = 0;
		else
			num = -1;
		return num;
	}
	
	public static String numToMonth (int num)
	{
		String name; 
		if(num==1)
			name = "January";
		else if (num==2)
			name = "February";
		else if (num==3)
			name = "March";
		else if (num==4)
			name="April";
		else if (num==5)
			name = "May";
		else if (num==6)
			name = "June";
		else if (num==7)
			name = "July";
		else if (num==8)
			name = "August";
		else if (num==9)
			name = "September";
		else if (num ==10)
			name = "October";
		else if (num==11)
			name = "November";
		else if (num==12)
			name = "December";
		else
			name = "ERROR :D";
		return name;
	}
	
	public static int monthToNum (String name)
	{
		int num;
		if (name.equals("January"))
			num=1;
		else if(name.equals("February"))
			num = 2;
		else if(name.equals("March"))
			num = 3;
		else if (name.equals("April"))
			num = 4;
		else if (name.equals("May"))
			num = 5;
		else if (name.equals("June"))
			num = 6;
		else if (name.equals("July"))
			num = 7;
		else if (name.equals("August"))
			num = 8;
		else if (name.equals("September"))
			num = 9;
		else if (name.equals("October"))
			num = 10;
		else if (name.equals("November"))
			num = 11;
		else if (name.equals("December"))
			num = 12;
		else 
			num = -1;
		
		return num;
	}
	
	public static int numberOfDays (int num)//enter month number
	{
		if (num==1||num==3||num==5||num==7||num==8||num==10||num==12)
			return 31;
		else if (num==2)
			return 28;
		else if (num==-2)//leap year
			return 29;
		else
			return 30;
	}
	
	 public  Image loadImage (String file)  // this method reads and loads the image
	    {
	        try
	        {
	      	  InputStream m = this.getClass().getResourceAsStream(file);
	          return (ImageIO.read (m));
	        }
	        catch (IOException e)
	        {
	            System.out.println ("Error: File " + file + " not found.");
	            return null;
	        }
	    }
	
}
