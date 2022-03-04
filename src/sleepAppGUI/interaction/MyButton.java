package sleepAppGUI.interaction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyButton extends VObject
{
    private String buttonName; //redundant????
    private String buttonImage;

    public MyButton(Page page, String bbuttonName, int[] coordinate1, int[] coordinate2, String imageName)
    {
        super(page, coordinate1, coordinate2);
        page.addButton(this);
        buttonName = bbuttonName;
        buttonImage = imageName;
    }

    public boolean onButton(int[] coordinates)
    {
        return( ((coordinates[0] >= corner1[0] && coordinates[0] <= corner2[0]) ||
                 (coordinates[0] >= corner2[0] && coordinates[0] <= corner1[0]) ) &&
                ((coordinates[1] >= corner1[1] && coordinates[1] <= corner2[1]) ||
                 (coordinates[1] >= corner2[1] && coordinates[1] <= corner1[1]) )   );
    }

    public void isClicked()
    {
        System.out.println(buttonName+" was clicked");
    }
    /*package private*/ void paint(Graphics g)
    {
        g.setColor(new Color(0xFF0000));
        //g.fillRect(corner1[0], corner1[1], corner2[0] - corner1[0], corner2[1] - corner1[1]);
        try
        {
            g.drawImage(ImageIO.read(new File("assets/"+buttonImage+".png")), corner1[0], corner1[1], corner2[0] - corner1[0], corner2[1] - corner1[1], null);
        }
        catch(IOException f) { System.out.println(buttonImage+" couldn't be found"); }

    }
}
