import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class EditWindow extends JFrame {
	
	private JTextField eventName,place;
	private JSpinner spinner, spinner2;
	private JTextArea descrip;
	private Box b;
	private Day d;
	private Month m;
	private Year y;
	private final Image icon = loadImage("logo.png");

	public EditWindow(Box box)
	{
		Font used = new Font ("Segoe UI", Font.PLAIN, 20);
		Font other = new Font ("Segoe UI", Font.PLAIN, 14);
		JPanel content = new JPanel (new GridBagLayout());//panel to display the information
		GridBagConstraints c = new GridBagConstraints();//layout
		Box b = box;
		d = b.getDayStored();//get the day stored by the box that is double clicked by the user
		m = b.getMonthStored();
		y = b.getYearStored();
		JLabel date = new JLabel ();
		JLabel eventTitle = new JLabel("Event Title: ");
		JLabel startTime = new JLabel("Start Time: ");
		JLabel endTime = new JLabel("End Time: ");
		eventName = new JTextField("",25);
		JLabel location = new JLabel("Location: ");
		place = new JTextField ("",25);
		descrip = new JTextArea ("",6,25);
	    JScrollPane pane = new JScrollPane(descrip, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		descrip.setLineWrap(true);
		JLabel description = new JLabel ("Description: ");
		Date dater = new Date();
		SpinnerDateModel sm = new SpinnerDateModel(dater, null, null, Calendar.HOUR_OF_DAY);
		SpinnerDateModel sm2 = new SpinnerDateModel(dater, null, null, Calendar.HOUR_OF_DAY);
		spinner = new JSpinner(sm);
		spinner2 = new JSpinner(sm2);
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "h:mm a");
		JSpinner.DateEditor de2 = new JSpinner.DateEditor(spinner2, "h:mm a");
		spinner.setEditor(de);
		spinner2.setEditor(de2);
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		

		date.setText("Date:                "+d.getName()+", "+ m.getName()+" "+d.getNum()+", "+y.getNumber());
		descrip.setFont(used);
		description.setFont(used);
		date.setFont(used);
		spinner.setFont(used);
		startTime.setFont(used);
		eventTitle.setFont(used);
		eventName.setFont(used);
		place.setFont(used);
		location.setFont(used);
		endTime.setFont(used);
		spinner2.setFont(used);
		ok.setFont(used);
		ok.addActionListener(new ButtonPress());
		cancel.setFont(used);
		cancel.addActionListener(new ButtonPress());
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 10;
		c.gridheight = 1;
		content.add(date,c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 10,0);
		content.add(eventTitle,c);
		c.gridwidth =9;
		c.gridx = 1;
		c.insets = new Insets (10,15,10,0);
		content.add(eventName,c);
		c.insets = new Insets (0,0,0,0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		content.add(startTime,c);
		c.gridx = 1;
		c.insets = new Insets (10,15,0,0);
		c.gridwidth = 3;
		content.add(spinner,c);
		c.gridx = 4;
		c.gridwidth = 2;
		c.insets = new Insets (10,30,0,0);
		content.add(endTime,c);
		c.insets = new Insets (10,20,0,0);
		c.gridx = 6;
		c.gridwidth = 1;
		content.add(spinner2,c);
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets (0,0,0,0);
		c.gridwidth = 1;
		content.add(location,c);
		c.insets = new Insets (10,15,0,0);
		c.gridx = 1;
		c.gridwidth =9;
		content.add(place,c);
		c.insets = new Insets (0,0,10,0);
		c.gridwidth = 1;
		c.gridx=0;
		c.gridy = 4;
		content.add(description,c);
		c.insets = new Insets(10,15,10,0);
		c.gridx = 1;
		c.gridheight = 5;
		c.gridwidth = 9;
		content.add(pane,c);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 4;
		c.gridy = 9;
		content.add(ok,c);
		c.gridx = 5;
		c.gridwidth = 2;
		content.add(cancel,c);
//		c.gridx =1;
//		c.gridwidth = 7;
//		content.add(place,c);
		
		this.setContentPane(content);
		this.setResizable(false);
		this.setSize(700,450);
		this.setTitle(d.getName()+", "+ m.getName()+" "+d.getNum()+", " + y.getNumber());
		this.setLocationRelativeTo(null);
		this.setIconImage(icon);
		this.setAlwaysOnTop(true);
		this.setVisible(true);

		
	}
	
public class ButtonPress implements ActionListener
{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if (e.getActionCommand().equals("Ok"))
		{
			SimpleDateFormat format = new SimpleDateFormat("h:mm a");
			Date time = (Date)spinner.getValue();
			Date time2 = (Date)spinner2.getValue();
			String startTime = format.format(time);
			String endTime = format.format(time2);			
			Event evt = new Event(eventName.getText(), startTime, endTime, place.getText(), descrip.getText());
			CalendarDrawArea.addEventInTime(evt);			
		}
		
		 EditWindow.super.dispose();//closes the window

		
	}
	
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
	
}

