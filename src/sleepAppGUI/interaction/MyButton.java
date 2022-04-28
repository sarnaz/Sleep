package sleepAppGUI.interaction;

import javax.imageio.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyButton extends VObject
{
    private final String buttonName; //redundant????
    private final String buttonImage;

    public MyButton(Page page, String buttonName, int[] coordinate1, int[] coordinate2, String imageName)
    {
        super(page, coordinate1, coordinate2, true);
        page.addButton(this);
        this.buttonName = buttonName;
        buttonImage = imageName;
    }

    public MyButton(Page page, String buttonName, Rectangle rectangle, String imageName) {
        super(page, new int[] {rectangle.x, rectangle.y}, new int[] {rectangle.x + rectangle.width, rectangle.y + rectangle.height}, true);
        page.addButton(this);
        this.buttonName = buttonName;
        buttonImage = imageName;
    }


    public boolean onButton(int[] coordinates)
    {
        return( ((coordinates[0] >= corner1[0] && coordinates[0] <= corner2[0]) ||
                 (coordinates[0] >= corner2[0] && coordinates[0] <= corner1[0]) ) &&
                ((coordinates[1] >= corner1[1] && coordinates[1] <= corner2[1]) ||
                 (coordinates[1] >= corner2[1] && coordinates[1] <= corner1[1]) )   );
    }

    public void toggleVisible() {
        this.setVisible(!this.isVisible());
    }

    public void isClicked()
    {
        System.out.println(buttonName+" was clicked");
    }
    public void paint(Graphics g)
    {
    	if (buttonImage == null) return;
        try
        {
            g.drawImage(ImageIO.read(new File("assets/"+buttonImage+".png")), corner1[0], corner1[1], corner2[0] - corner1[0], corner2[1] - corner1[1], null);
        }
        catch(IOException f) { System.out.println(buttonImage+" couldn't be found"); }

    }
}
