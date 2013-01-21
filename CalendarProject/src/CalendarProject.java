import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;


public class CalendarProject extends JFrame implements ComponentListener {//all event information stored in CalendarProject function
	
	private static ArrayList<Year> myYears;
	private int HEIGHT = 800;
	private int WIDTH = 1200;
	private JSplitPane contents;
	private static CalendarDrawArea display;
	private static JTextField eventName;
	private static JLabel dayLabel = new JLabel();
	private static JLabel dateLabel = new JLabel();
	private static JLabel eventLabel = new JLabel();
	private static JPanel eventsPanel = new JPanel();//pane to display the events of that day 
	private static ArrayList <JLabel> listOfLabels = new ArrayList<JLabel>();//list of JLabels
	private static File homeFile;
	private final Font BOLD_FONT = new Font ("Segoe UI", Font.BOLD,20);
	private static ArrayList <Year> years = new ArrayList<Year> ();
	private final Image icon = loadImage("logo.png");

	public CalendarProject (int y,int n)//pass in start year and day of January 1st
	{
		 myYears = createYears(y,n);
		 
		 JPanel content = new JPanel (new GridBagLayout());
		 JPanel info = new JPanel (new GridBagLayout());
		 JButton addEventButton = new JButton();
		 JButton modifyButton = new JButton("Modify...");
		 JButton addButton = new JButton("Add Event");
		 JButton removeButton = new JButton("Remove");
		 modifyButton.addActionListener(new ButtonPress());
		 addEventButton.addActionListener(new ButtonPress());
		 removeButton.addActionListener(new ButtonPress());
		 GridBagConstraints c = new GridBagConstraints();//Calendar layout
		 GridBagConstraints d = new GridBagConstraints();//information layout
		 JMenuBar menuBar = new JMenuBar();
		 menuBar.add(new JMenu("File"));
		 
		 //File chooser
		 JFileChooser f = new JFileChooser();
		 f.setFileSelectionMode (JFileChooser.DIRECTORIES_ONLY);
		 f.setDialogTitle("Select existing home directory or location of new directory");
		 f.showDialog(this,"Ok");
		 
		 try{//ask for the directory
			 
		 homeFile = new File(f.getSelectedFile().toString()+"/Calendar");
	 
		 }
		 catch (Exception e)//presses cancel or closes 
		 {
			 System.exit(0);
		 }
		 homeFile.mkdir();//make the directory if it isn't already there

		File file = new File (homeFile+"/years.cal");

//		 if(file.exists())
//		 {
//			 try{
//			 FileInputStream saveFile = new FileInputStream(file);
//			 ObjectInputStream restore = new ObjectInputStream(saveFile);
//			 Object obj = restore.readObject();
//			 Year y2= (Year) restore.readObject();
//			 System.out.println(y2.getNumber());
////			 display = new CalendarDrawArea(years.get(0), (int)(0.735*WIDTH) ,(int)(0.91*HEIGHT));	
//			 restore.close();
//			  }
//			 catch(Exception e)
//			 {
//				e.printStackTrace();
//			 }
//		 }
//		 
//		 else
//		 {
//		 }
		 Year year1 = new Year (y,n);

		 display = new CalendarDrawArea(year1, (int)(0.735*WIDTH) ,(int)(0.91*HEIGHT));				 

		 display.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		 display.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"pressed");//make the computer react when the space bar is pressed
		 display.getActionMap().put("pressed",new Action());//when the pressed key is called 
		//layout for the calendar draw area panel 
		c.fill=GridBagConstraints.BOTH;
		c.anchor=GridBagConstraints.CENTER;
		c.weightx =0.25;
		c.weighty=0.25;
		c.gridwidth=10;
		c.gridheight=20;
		c.gridx=0;
		c.gridy=0;
		content.add(display,c);
		
		//layout for information panel
		d.fill=GridBagConstraints.NONE;
		d.anchor=GridBagConstraints.CENTER;
		d.gridx = 0;
		d.gridy = 0;
		d.gridwidth = 10;
		d.gridheight = 1;
		d.insets = new Insets(0, 10, 0,0);
		info.add(dayLabel,d);
		d.gridy = 1;
//		d.anchor = GridBagConstraints.PAGE_END;
		info.add(dateLabel,d);
//		info.add(new JLabel("\n\n\n\n\n\n\n\nd\nd"));
		d.gridy = 2;
		d.gridx = 1;
		d.gridheight = 10;
		d.weighty = 1.0;
		info.add(eventsPanel,d);
		d.gridy = 13;
		d.gridx = 1;
		d.gridwidth = 1;
//		info.add(modifyButton,d);
		d.gridx = 2;
//		info.add(addButton,d);
				
		contents = new JSplitPane (JSplitPane.HORIZONTAL_SPLIT,info,content);
		contents.setDividerLocation((int)(0.25*WIDTH));
		contents.setEnabled(false);
		dateLabel.setFont(BOLD_FONT);
		dayLabel.setFont(BOLD_FONT);
		
		
		//set the properties of the JFrame
		addComponentListener(this);
		this.setResizable(true);
		this.setContentPane (contents);
		this.setMinimumSize(new Dimension (1000, 700));
		this.setSize(WIDTH,HEIGHT);
		this.setLocationRelativeTo (null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Michelle's Calendar");
		this.setJMenuBar(menuBar);
		this.setIconImage(icon);//set icon for program
	    display.requestFocusInWindow();//so the mouse listener works
	    
//	    this.addWindowListener(new WindowAdapter()
//	    {
//	    	public void windowClosing(WindowEvent e)
//	    	{
//	    		try{
//	    			final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(homeFile+"/years.cal"));
//	    			for (int i = 0;i<years.size();i++)
//	    				out.writeObject(years.get(i));
//	    		    out.close();    		    
//	    			}
//	    		catch(Exception e1)
//	    		{
//	    			System.out.println(e1.getMessage());
//	    		}
//	    		System.exit(0);//exit the program
//	    	}
//	    });

		this.setVisible(true);//set the window visible
		

	}
	public static ArrayList <Year> getYears()
	{
		return years;
	}
	
	public static void saveEvents (Box b)
	{
		display.repaint();
		Month m = b.getMonthStored();
		Day d = b.getDayStored();

		File f2 = new File (homeFile.toString()+"/"+m.getName());
		f2.mkdir();
		File f = new File (f2.toString()+"/"+d.getNum()+".cal") ;
		BufferedWriter out;
		if(f.exists())//if the text file for that number already exits
		{
			try{
			 out = new BufferedWriter(new FileWriter(f.toString(), true));
			}
			catch(Exception e)
			{
				out=null;
			}
			
		}
		else//if the textfile for that number does not exist
		{
			try{
			FileWriter fstream = new FileWriter (f.toString());
			out = new BufferedWriter(fstream);
				
			}
			catch (Exception e)
			{
				out=null;
			}
		}
		
		writeMethod(out,b);		
		

	
	}
	
	private static void writeMethod(BufferedWriter out, Box b)
	{
		Day d = b.getDayStored();
		ArrayList <Event> list = d.getEvents();
		String s1 = "$%$%$!~";//identify space between event attributes
		String s2 = "!@#$*$%";//identify the end of a day
		try{

			Event e = list.get(list.size()-1);
			if(e.getName()!=null)
				out.write(e.getName());//write the name
			if(e.getLocation()!=null)
				out.write(s1+e.getLocation());//write the location
			if(e.getStartTime()!=null)
				out.write(s1+e.getStartTime());//write the start time
			if(e.getEndTime()!=null)
				out.write(s1+e.getEndTime());//write the end time
			if(e.getDescription()!=null)
				out.write(s1+e.getDescription());
			
			out.newLine();
		
		out.write(s2);
		out.newLine();
		out.close();

		}
		catch(Exception e)
		{
			
		}
		
	}
	

	private ArrayList <Year> createYears (int y,int n)
	{
		myYears = new ArrayList <Year> ();//years in the calendar program
		Year FirstYear = new Year (y,n);//first year
		myYears.add(FirstYear);//add the first year to the list
	
		for (int i = 2;i<=10;i++)//10 years of data
		{
			Year previous = myYears.get(i-2);
			int lastDay = previous.getLastDayOfYear();
			int firstDayOfYear = CalendarFunctions.nextDay(lastDay);//the first day of the new year is one day after the last day of the old year :P
			Year next = new Year(i-1+y, firstDayOfYear);
			myYears.add(next);
			
		}
			
		return myYears;
		
	}
	
	 public  Image loadImage (String file)  // this method reads and loads the image
	    {
	        try
	        {
	        	InputStream m = this.getClass().getResourceAsStream(file);
	            return ImageIO.read (m);
	        }
	        catch (IOException e)
	        {
	            System.out.println ("Error: File " + file + " not found.");
	            return null;
	        }
	    }
	 
	private void resize()
	{
		contents.setDividerLocation((int)(0.25*WIDTH));
		display.setSize((int)(0.735*WIDTH),(int)(0.91*HEIGHT));
		this.revalidate();
		
	}
	
	public static void main(String[] args) throws Exception
	{
		CalendarProject myCalendar = new CalendarProject (2013,2);//'0' indicates the first day of the year is a sunday;

    }

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		HEIGHT = this.getHeight();
		WIDTH = this.getWidth();
		resize();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void setInformationPanel(Box b)
	{
		Day d = b.getDayStored();
		Month m = b.getMonthStored();
		Year y = b.getYearStored();
		dayLabel.setText(d.getName().toUpperCase());
		dateLabel.setText(m.getName()+" "+d.getNum()+", "+y.getNumber());
		GridBagConstraints c = new GridBagConstraints();
		eventsPanel.setLayout(new GridBagLayout());//panel to display the list of activities
		listOfLabels.clear();
		eventsPanel.removeAll();
		int num = 0;//used to update the y position
		c.gridx=0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;

		try{
			
			ArrayList <Event> listOfEvents = d.getEvents();
			for (int i = 0;i<listOfEvents.size();i++)
			{
				String name = listOfEvents.get(i).getName();
				listOfLabels.add(new JLabel(name));
			}
		}
		catch (Exception e)
		{
			listOfLabels.add(new JLabel("No Events"));		
		}
		
		for (int i = 0;i<listOfLabels.size();i++)
		{
			eventsPanel.add(listOfLabels.get(i),c);
			num++;
			c.gridy = num;
		}
		
	}
	
	public static void updates(Year year)//update the events in the year and the displayed event
	{
		myYears.set(0, year);
	}

	public class ButtonPress implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			
		}
		
	}
	
	public class Action extends AbstractAction 
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("weeee");
			
		}
		
	}
	

}

	