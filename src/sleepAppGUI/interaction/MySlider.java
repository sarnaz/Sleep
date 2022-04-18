package sleepAppGUI.interaction;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MySlider extends VObject {

	String sliderName;
	String sliderImage;
	String backgroundImage;
	String notchImage;
	
	// which notch will the slider output - independent from interpolation
	int currentNotch = 0;
	
	// how many intervals exists on the slider
	int intervalCount = 2;
	
	// the width between intervals
	int intervalWidth;
	
	
	// the width of the notch image
	int notchWidth = 3;
	
	// the width of the slider image
	int sliderWidth = 5;
	
	// the width of the background image
	int backgroundWidth;
	
	// the height of the background and all other images
	int backgroundHeight;
	
	// the 
	double interpolation = 0;
	
	boolean selected = false;
	
	public MySlider(Page page, String sliderName, int[] coordinate1, int[] coordinate2, String backgroundImageName, String sliderImageName, String notchImageName, int count)
    {
        super(page, coordinate1, coordinate2, true);
        page.addSlider(this);
        this.sliderName = sliderName;
        backgroundImage = backgroundImageName;
        sliderImage = sliderImageName;
        notchImage = notchImageName;

        backgroundWidth = corner2[0] - corner1[0];
		System.out.println("background width: " + Integer.toString(backgroundWidth));
        backgroundHeight = corner2[1] - corner1[1];
        
        currentNotch = 0;
        intervalCount = count;
        
        intervalWidth = (int)((double)backgroundWidth / (double)(intervalCount - 1));
    }

    public boolean onSlider(int[] coordinates)
    {
        return( ((coordinates[0] >= corner1[0] && coordinates[0] <= corner2[0]) ||
                 (coordinates[0] >= corner2[0] && coordinates[0] <= corner1[0]) ) &&
                ((coordinates[1] >= corner1[1] && coordinates[1] <= corner2[1]) ||
                 (coordinates[1] >= corner2[1] && coordinates[1] <= corner1[1]) )   );
    }
    
    private double map(double fromMin, double fromMax, double toMin, double toMax, double value)
    {
    	return toMin + ((value - fromMin) / (fromMax - fromMin)) * (toMax - toMin);
    }
    
    private int toNearestInt(double a)
    {
    	return (int)((a % 1 >= 0.5) ? Math.ceil(a) : Math.floor(a));
    }

	public void updatePosition(int[] dragCoordinates) {
		
		System.out.println("slider was updated");
		
		System.out.println(corner1[0]);
		System.out.println(corner2[0]);
		System.out.println(Math.max(Math.min(interpolation, corner2[0]), corner1[0]));
		
		// only want to do this if currently being dragged
		if (selected)
		{
			interpolation = map(corner1[0], corner2[0], 0.0, 1.0, Math.max(Math.min(dragCoordinates[0], corner2[0]), corner1[0]));
			
			// get the nearest notch to this interpolation
			currentNotch = toNearestInt(interpolation * (double)(intervalCount - 1));
			
			System.out.println("currentNotch: " + Integer.toString(currentNotch));
			
			System.out.println("interpolation: " + Double.toString(interpolation));
		}
	}

	public void setSelected(boolean b)
	{
		
		if (!b)
		{
			System.out.println("slider was released");
			updatePosition(new int[] {corner1[0] + currentNotch * intervalWidth, corner1[1]});
		}
		else 
			System.out.println("slider was pressed");

		selected = b;
	}
	
	public int getValue()
	{
		return currentNotch;
	}
	
	public void paint(Graphics g)
    {
        try
        {
        	int xOffset = (int)Math.floor((corner2[0] - corner1[0]) * interpolation);
            g.drawImage(ImageIO.read(new File("assets/"+backgroundImage+".png")), corner1[0], corner1[1], backgroundWidth, backgroundHeight, null);
            
            for (int i = 0; i < intervalCount; i++)
            {
            	g.drawImage(ImageIO.read(new File("assets/"+notchImage+".png")), corner1[0] + intervalWidth * i, corner1[1], notchWidth, backgroundHeight, null);
            }
            
            g.drawImage(ImageIO.read(new File("assets/"+sliderImage+".png")), corner1[0] + xOffset, corner1[1], sliderWidth, backgroundHeight, null);
        }
        catch(IOException f) { System.out.println(sliderImage +" couldn't be found"); }

    }
}
