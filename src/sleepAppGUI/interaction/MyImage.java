package sleepAppGUI.interaction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MyImage extends VObject
{
    String image;

    public MyImage(Page page, int[] coordinates1, int[] coordinates2, String imageName, boolean defaultVisible)
    {
        super(page, coordinates1, coordinates2, defaultVisible);
        image = imageName;
    }

    public void paint(Graphics g)
    {
        try
        {
            g.drawImage(ImageIO.read(new File("assets/"+image+".png")), corner1[0], corner1[1], corner2[0] - corner1[0], corner2[1] - corner1[1], null);
        }
        catch(IOException f) { System.out.println(image+" couldn't be found"); }
    }
}
