
public class Box {
	
	private final int boxNumber;
	private Day dayStored;
	private Month monthStored;
	private Year yearStored;
	private int [] topLeft = new int[2];
	private int [] topRight = new int[2];
	private int [] bottomLeft = new int [2];
	private int [] bottomRight = new int[2];
	
	public Box (int num)
	{
		boxNumber = num;		
	}
	
	public Box (Box b)
	{
		boxNumber = b.getBoxNumber();
		dayStored = b.getDayStored();
		monthStored = b.getMonthStored();
		yearStored = b.getYearStored();
		topLeft = b.getTopLeft();
		topRight = b.getTopRight();
		bottomLeft = b.getBottomLeft();
		bottomRight =b.getBottomRight();
	}
		
    public Day getDayStored ()
    {
    	return dayStored;
    }
	
    public void setDayStored (Day d)
    {
    	dayStored = new Day (d);
    }
    
    public Month getMonthStored()
    {
    	return monthStored;
    }
    
    public Year getYearStored()
    {
    	return yearStored;
    }
    
    public void setYearStored (Year y)
    {
    	yearStored = new Year (y);
    }
    
    public void setMonthStored(Month m)
    {
    	monthStored = new Month (m);
    }
	public void setTopLeft(int x, int y)
	{
		topLeft[0] = x;
		topLeft[1] = y;
	}
	
	public void setOthers(int x_diff, int y_diff)
	{
		topRight[0] = topLeft[0]+x_diff;
		topRight[1]= topLeft[1];
		
		bottomLeft[0] = topLeft[0];
		bottomLeft[1] = topLeft[1]+y_diff;
		
		bottomRight[0] =topRight[0];
		bottomRight[1] = bottomLeft[1];
	}
	
	public int getBoxNumber()
	{
		return boxNumber;
	}
	
	public int [] getTopLeft ()
	{
		return topLeft;
	}
	
	public int [] getTopRight()
	{
		return topRight;
	}
	
	public int [] getBottomLeft()
	{
		return bottomLeft;
	}
	
	public int [] getBottomRight ()
	{
		return bottomRight;
	}
	

}
