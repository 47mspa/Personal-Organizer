import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class CalendarDrawArea extends JPanel implements MouseListener, KeyListener {
	private static Month currentMonth;
	private static Year currentYear;
	private static Day currentDay;
	private static Box cursor;
	private static ArrayList <Box> boxes;
	private int X_DIM, Y_DIM;
	private final double CONSTANT_A = 25.0/900;
	private final double CONSTANT_B = 75.0/800;
	private final double CONSTANT_C = 850.0/900;
	private final double CONSTANT_D = 700.0/800;
	private final Font TITLE_FONT = new Font ("Segoe UI", Font.BOLD,35);
	private final Font NORMAL_FONT = new Font ("Segoe UI", Font.PLAIN,16);
	private final Font EVENT_FONT = new Font ("Courier New", Font.PLAIN, 14);
	private final Font BOLD_FONT = new Font ("Segoe UI", Font.BOLD,16);
	private Image forwardArrow = loadImage("arrowforward.png");
	private Image backwardArrow = loadImage("arrowbackwards.png");
	private int y1, boxWidth, x1, each_x, each_y;
	private final Color light = new Color(175,175,175);
	
	public CalendarDrawArea (Year year, int X, int Y)
	
	{
		boxes = new ArrayList <Box>();
		Calendar currentDate = Calendar.getInstance();
		int m = currentDate.get(Calendar.MONTH);
		int d = currentDate.get(Calendar.DATE);
		currentYear = year;
		currentMonth = year.getMonths().get(m);
		currentDay = currentMonth.getDays().get(d-1);
		cursor = new Box(-1);
		cursor.setDayStored(currentDay);
		cursor.setMonthStored(currentMonth);
		cursor.setYearStored(currentYear);
		CalendarProject.setInformationPanel(cursor, CalendarProject.getHeadLines());
		  
        for (int j = 0;j<6;j++)//nested loop to add boxes, loop to go through weeks in a month
        {
        	for (int i = 0;i<7;i++)//loop to go through days in a week
        	{
        		int boxNumber = (j*7)+(i+1);//square number is the index number in the arraylist plus 1
        		boxes.add(new Box(boxNumber));//add square to list of squares  (square number, topLeftX, topLeftY)
        	}
        }
        X_DIM = X;
        Y_DIM = Y;
       
        setPreferredSize (new Dimension (X_DIM, Y_DIM)); // size
		addMouseListener(this);//add key and mouse listeners
		setFocusable(true);//have both listeners at the same time
		addKeyListener(this);
		requestFocusInWindow();

		
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
 		 x1 = (int)(CONSTANT_A*X_DIM);//determining the location to draw the outline of the calendar
		 y1 = (int)(CONSTANT_B*Y_DIM);
		 boxWidth = (int)(CONSTANT_C*X_DIM);//determining the dimensions of the box
		int boxHeight = (int)(CONSTANT_D*Y_DIM);
		
	    g.setFont(TITLE_FONT);
	    int extra = (currentMonth.getName()).length()-5;//calculate number of letters more than "March"
	    int constant = (int) (boxWidth/10.0);
	    int x_value = x1+(constant*4);
	    g.drawString(currentMonth.getName()+", "+currentYear.getNumber(), x_value-(5*extra),(int)(y1/1.5));//attempt to centre

	    
		g.drawImage(forwardArrow,boxWidth+x1-40,(int)(y1/3.0),null);//x values: 30-70, y values: 15-45
		g.drawImage(backwardArrow,x1,(int)(y1/3.0),null);//x values: 774-814
		
		g.drawRect(x1,y1,boxWidth, boxHeight);//line for date
				
		double h = 75.0/600;
		int y2 = (int)(h*Y_DIM);//position of the line
		g.drawLine(x1, y2,x1+boxWidth, y2);
	
		each_x = (int) (boxWidth/7.0);
		each_y = (int)((boxHeight-(y2-y1))/6.0);

		int var = x1;
		g.setFont(BOLD_FONT);
		g.drawString("Sunday",centre(var, var+=each_x,"Sunday"),(int)(y1*1.25));
		g.drawString("Monday",centre(var, var+=each_x,"Monday"),(int)(y1*1.25));
		g.drawString("Tuesday",centre(var, var+=each_x, "Tuesday"),(int)(y1*1.25));
		g.drawString("Wednesday",centre(var, var+=each_x, "Wednesday"),(int)(y1*1.25));
		g.drawString("Thursday",centre(var, var+=each_x, "Thursday"),(int)(y1*1.25));
		g.drawString("Friday",centre(var, var+=each_x, "Friday"),(int)(y1*1.25));
		g.drawString("Saturday",centre(var, var+=each_x, "Saturday"),(int)(y1*1.25));
		
		int variable_x = x1;
		int variable_y = y2;
		
		
		 for (int j = 0;j<6;j++)//nested loop to add boxes, loop to go through weeks in a month
	        {
			 variable_x = x1;
	        	for (int i = 0;i<7;i++)//loop to go through days in a week
	        	{
	        		int boxNumber = (j*7)+(i+1);//square number is the index number in the arraylist plus 1
	        		Box tempBox = boxes.get(boxNumber-1);
	        		tempBox.setTopLeft(variable_x, variable_y);
	        		variable_x+=each_x;
	        		tempBox.setOthers(each_x, each_y);
	        	}
	        	variable_y+=each_y;
	        }
		
		 g.setFont(NORMAL_FONT);
		 
		 int num = currentMonth.getFirstDay();
		 if (num ==0)
			 num=7;
		 
		 for (int i = 1;i<=currentMonth.getDaysInMonth();i++)
		 {
			 Box tempBox = boxes.get(i+num-1);
			 Day currentDay = currentMonth.getDays().get(i-1);
			 tempBox.setDayStored(currentDay);
			 tempBox.setMonthStored(currentMonth);
			 tempBox.setYearStored(currentYear);
			 int [] topLeft = tempBox.getTopLeft();
			 g=drawBox(tempBox,g,false);
			 g.drawString (currentDay.getNum()+"",topLeft[0]+10, topLeft[1]+20);
			 boxes.set(i+num-1, tempBox);
		 }
		 
		 Month previousMonth = currentYear.getMonths().get((CalendarFunctions.previousMonth(currentMonth.getNum())-1));
		 int k = previousMonth.getDaysInMonth()-num;
		 
		 for (int i = 0;i<num;i++)
		 {
			 Box tempBox = boxes.get(i);
			 Day currentDay = previousMonth.getDays().get(k);
			 k++;
			 tempBox.setDayStored(currentDay);
			 tempBox.setMonthStored(previousMonth);
			 tempBox.setYearStored(currentYear);
			 int [] topLeft = tempBox.getTopLeft();
			 g=drawBox(tempBox,g, false);
			 g.setColor(light);
			 g.drawString(currentDay.getNum()+"", topLeft[0]+10, topLeft[1]+20);
			 boxes.set(i, tempBox);
		 }
		
		 
		 Month nextMonth = currentYear.getMonths().get((CalendarFunctions.nextMonth(currentMonth.getNum())-1));
		 int r = 1;
		 for (int i = currentMonth.getDaysInMonth()+num;i<boxes.size();i++)
		 {
			 Box tempBox = boxes.get(i);
			 Day currentDay = nextMonth.getDays().get(r-1);
			 r++;
			 tempBox.setDayStored(currentDay);
			 tempBox.setMonthStored(nextMonth);
			 tempBox.setYearStored(currentYear);

			 int [] topLeft = tempBox.getTopLeft();
			 g=drawBox(tempBox,g,false);
			 g.setColor(light);
			 g.drawString(currentDay.getNum()+"", topLeft[0]+10, topLeft[1]+20);
			 boxes.set(i, tempBox);					 
		 }
		 
	
		 for (int i = 0;i<boxes.size();i++)
		 {
			 Box tempBox = boxes.get(i);
			 Day tempDay = tempBox.getDayStored();
			 Day cursorDay = cursor.getDayStored();
			 Month cursorMonth = cursor.getMonthStored();
			 Month boxMonth = tempBox.getMonthStored();
			 if (cursorDay.getNum()==tempDay.getNum()&&cursorMonth.getName().equals(boxMonth.getName()) )
				 g=drawBox(tempBox,g,true);
		 }
	
		 g.setColor(Color.black);
		 
	
		//for (int x = x1+each_x;x<x1+boxWidth;x+=each_x)
			//if (x1+boxWidth-x>(int)(0.9*each_x))
				//g.drawLine(x,y1, x,boxHeight+y1);
		
		//for (int y = y2;y<=y1+boxHeight;y+=each_y)
			//if (y1+boxHeight-y>(int)(0.9*each_y))//if the box created is greater than atleast 90% of the normal size 
				//g.drawLine(x1, y, x1+boxWidth, y);
	
		
		//System.out.println(x*X_DIM);
		//System.out.println(y*Y_DIM);
		//g.drawRect(20,60,900,600);//draw the outline of the calender 
		
	}
	
	public int centre (int x1, int x2, String s)
	{
		int num = s.length();
		int eachside = num/2;
		int middle = ((x2-x1)/2) + x1 ;
		return middle-(int)(9.5*eachside);
		
	}
	
	public void updateBoxes()
	{
		 int num = currentMonth.getFirstDay();
		 if (num ==0)
			 num=7;
		 
		 for (int i = 1;i<=currentMonth.getDaysInMonth();i++)
		 {
			 Box tempBox = boxes.get(i+num-1);
			 Day currentDay = currentMonth.getDays().get(i-1);
			 tempBox.setDayStored(currentDay);
			 tempBox.setMonthStored(currentMonth);
			 boxes.set(i+num-1, tempBox);
		 }
		 
		 Month previousMonth = currentYear.getMonths().get((CalendarFunctions.previousMonth(currentMonth.getNum())-1));
		 int k = previousMonth.getDaysInMonth()-num;
		 
		 for (int i = 0;i<num;i++)
		 {
			 Box tempBox = boxes.get(i);
			 Day currentDay = previousMonth.getDays().get(k);
			 k++;
			 tempBox.setDayStored(currentDay);
			 tempBox.setMonthStored(previousMonth);
			 boxes.set(i, tempBox);
		 }
		
		 
		 Month nextMonth = currentYear.getMonths().get((CalendarFunctions.nextMonth(currentMonth.getNum())-1));
		 int r = 1;
		 for (int i = currentMonth.getDaysInMonth()+num;i<boxes.size();i++)
		 {
			 Box tempBox = boxes.get(i);
			 Day currentDay = nextMonth.getDays().get(r-1);
			 r++;
			 tempBox.setDayStored(currentDay);
			 tempBox.setMonthStored(nextMonth);
			 boxes.set(i, tempBox);					 
		 }
		 
	}
	
	 public Image loadImage (String file)  // this method reads and loads the image
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
	 
	
	public void setSize (int x, int y)
	{
		X_DIM =x;
		Y_DIM = y;
		
		this.revalidate();
		this.repaint();
	}
	
	public void setSize (Dimension d)
	{
		X_DIM = (int)d.getWidth();
		Y_DIM = (int)d.getHeight();
		this.revalidate();
		this.repaint();
		
	}
	
	public Graphics drawBox(Box tempBox ,Graphics g, boolean cursors)//draw the square for normal squares
	{
		int [] topLeft = tempBox.getTopLeft();
		int [] topRight = tempBox.getTopRight();
		int [] bottomLeft = tempBox.getBottomLeft();
		int [] bottomRight = tempBox.getBottomRight();
		g.setColor(Color.black);
		
		if (cursors==false)
		{
			g.drawLine(topLeft[0],topLeft[1],topRight[0],topRight[1]);
			g.drawLine(bottomLeft[0],bottomLeft[1],topLeft[0],topLeft[1]);
		
			if (tempBox.getBoxNumber()%7!=0)//if the box isn't one in the last column
			{
				g.drawLine(topRight[0],topRight[1],bottomRight[0],bottomRight[1]);//draw line
				
			}
			if (tempBox.getBoxNumber()<35)//if the box isn't in the last row
			{
				g.drawLine(bottomRight[0],bottomRight[1],bottomLeft[0],bottomLeft[1]);//drawline
			}
		}
		
		else
		{   
			g.setColor(Color.red);
			g.fillRect(topLeft[0],topLeft[1]-1,each_x,4);
			g.fillRect(topRight[0]-1,topRight[1]-1,4,each_y+3);
			g.fillRect(bottomLeft[0],bottomLeft[1]-1,each_x,4);
			g.fillRect(topLeft[0],topLeft[1]-1,4,each_y);
		}
		
		g.setColor(Color.black);
		
		try{
		Day d = tempBox.getDayStored();
		ArrayList <Event> events = d.getEvents();
		g.setFont(EVENT_FONT);
		
		for (int i = 0;i<events.size();i++)//goes through the list of events and prints the name of the events 
		{
			Event e = events.get(i);
			String name = e.getName();
			int diff = topRight[0]-topLeft[0];//number of pixels between the two sides of the square
			int maxChar = (diff/10) - 4;//maximum number of characters
			String s = name;;
			if(name.length()>maxChar)
			{
				s = name.substring(0,maxChar);
				s = s +"...";
			}
			g.drawString("- "+s, topLeft[0]+10,topLeft[1]+35+(20*i));
		}
		
		g.setFont(NORMAL_FONT);

		}
		catch (Exception E)
		{
			
		}
		
		return g;
	}
	
	public void clicked (int X, int Y, int num)
	{
		
		
		int i=currentMonth.getNum();
		if (X>(boxWidth+x1-40)&&X<(x1+boxWidth)&&Y>(int)(y1/3.0)&&Y<(int)(y1/3.0)+33)//user selected the forward arrow
		{
			if (i==12)
			{
				updateNewYear();
			}
			else{
			i = CalendarFunctions.nextMonth(currentMonth.getNum());
			currentMonth = currentYear.getMonths().get(i-1);
			}
		}
		
		else if (X>x1&&X<x1+40&&Y>(int)(y1/3.0)&&Y<(int)(y1/3.0)+32)//user selects the back arrow
		{
			if (i==1)
			{
				updateLastYear();
			}
			else{
				i = CalendarFunctions.previousMonth(currentMonth.getNum());
				currentMonth = currentYear.getMonths().get(i-1);
			}
		}
	
		for (int j = 0;j<boxes.size();j++)
			{
				Box tempBox = boxes.get(j);
				int [] topLeft = tempBox.getTopLeft();
				int [] topRight = tempBox.getTopRight();
				int [] bottomLeft = tempBox.getBottomLeft();
				if (X>topLeft[0]&&X<topRight[0]&&Y>topLeft[1]&&Y<bottomLeft[1])
				{	
					cursor = new Box (tempBox);
					EditWindow window;
					if (num>=2)
						 window = new EditWindow(cursor);
					CalendarProject.setInformationPanel(cursor);
					
				}
			}
						
		repaint();
	}
	
	public void updateDrawAreaNext ()//advance the month
	{
		if (currentMonth.getNum()==12)//end of the year
		{
			updateNewYear();
		}
		else
		{
			ArrayList <Month> list = currentYear.getMonths();
			currentMonth = list.get(CalendarFunctions.nextMonth(currentMonth.getNum())-1);			
		}
		
		updateBoxes();
		Day cursorDay = cursor.getDayStored();
		setCursor(cursorDay.getNum());
	}
	
	public void updateNewYear()//go to the next year
	{
		ArrayList <Year> years = CalendarProject.getYears();//get the current arraylist of years
		Year newYear = new Year (currentYear.getNumber()+1,CalendarFunctions.nextDay((currentYear.getLastDayOfYear())));//pass in number of the new year and the first day of the year
		years.add(newYear);//add a new year to the list
		ArrayList <Month>list = newYear.getMonths();
		currentYear = newYear;
		currentMonth = list.get(0);
				
	}
	
	public void updateLastYear()//go back a year
	{
		int temp = currentYear.getNumber();//the year before switching years
		ArrayList <Year> years = CalendarProject.getYears();//get the years from the Calendar project
		if(currentYear.getNumber()>2013)
			currentYear = years.get(currentYear.getNumber()-2013-1);
		ArrayList <Month>list = currentYear.getMonths();//list of months
		
		if(temp==2013)//if the user is already on the start of the calendar, year 2013
		{			
			currentMonth= list.get(0);
			updateBoxes();
			Day cursorDay = cursor.getDayStored();
			setCursor(cursorDay.getNum());
		}
		else{
			currentMonth = list.get(11);
			updateBoxes();
			Day cursorDay = cursor.getDayStored();
			setCursor(cursorDay.getNum());
		}
			
	}
	public void updateDrawAreaPrevious()
	{
		if (currentMonth.getNum()==1)//end of the year
		{
			updateLastYear();
		
		}
		else
		{
			ArrayList <Month> list = currentYear.getMonths();
			currentMonth = list.get(CalendarFunctions.previousMonth(currentMonth.getNum())-1);		
			updateBoxes();
			Day cursorDay = cursor.getDayStored();
			setCursor(cursorDay.getNum());
		}
		
		
	}
	
	public void setCursor (int n)
	{
		for (int i = 0;i<boxes.size();i++)
		{
			Box b = boxes.get(i);
			Day d = b.getDayStored();
			if (currentMonth.getNum()==b.getMonthStored().getNum()&&n==d.getNum())
				cursor = new Box (b);
		}
		repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
		
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
	
		this.requestFocusInWindow();//request focus when the user clicks on a date
		clicked(e.getX(),e.getY(), e.getClickCount());
		
		
		
	}

	public void mouseReleased(MouseEvent e)
	{
		
	}

	public void keyPressed(KeyEvent e) 
	{
		int E = e.getKeyCode();
		
		if (E==KeyEvent.VK_RIGHT||E==KeyEvent.VK_LEFT||E==KeyEvent.VK_UP||E==KeyEvent.VK_DOWN)//user does keyboard input
		{
			int value;
			Box b = cursor;
			if (E==KeyEvent.VK_RIGHT)
				value = cursor.getBoxNumber();//move one to the right
			else if (E==KeyEvent.VK_LEFT)
				value = cursor.getBoxNumber()-2;//move one the left
			else if (E==KeyEvent.VK_UP)
				value = cursor.getBoxNumber()-8;//move a week earlier
			else//down key is pressed
				value = cursor.getBoxNumber()+6;//move a week later
			
			if (value>=0 && value<boxes.size())		//if they have not reached the next/previous month by moving the cursor		
				b = boxes.get(value);
			
			Month storedCursor = b.getMonthStored();//get the month stored in the current box
			cursor = new Box (b);
			if (CalendarFunctions.nextMonth(currentMonth.getNum())==storedCursor.getNum())
				updateDrawAreaNext();
			else if (CalendarFunctions.previousMonth(currentMonth.getNum())==storedCursor.getNum())
				updateDrawAreaPrevious();
			repaint();
		}
		
		else if (E==KeyEvent.VK_ENTER)
		{
			CalendarProject.setInformationPanel(cursor);
			EditWindow window = new EditWindow(cursor);
		}
		
	}

	public void keyReleased(KeyEvent e) {
		
		
		
	}

	public void keyTyped(KeyEvent e) {
		
		
	}

	
	public static void addEventInTime(Event e)//adds the passed in event into the date in history
	{
		Day d = cursor.getDayStored();//the current the c
		d.addEvent(e);
		CalendarProject.saveEvents(cursor);//saves the file into text 
			
		
//		ArrayList <Day> listOfDays= currentMonth.getDays();
//		listOfDays.set(d.getNum()-1,d);
//		currentMonth.setDays(listOfDays);
//		ArrayList <Month> listOfMonths = currentYear.getMonths();
//		listOfMonths.set(currentMonth.getNum()-1, currentMonth);
//		currentYear.setMonths(listOfMonths);
//		CalendarProject.updates(currentYear);
//		CalendarProject.setInformationPanel(cursor);
//		
	}
	
	
	
	
}
