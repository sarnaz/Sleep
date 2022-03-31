package sleepAppGUI.interaction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyImage extends VObject
{
    String imageFileName;
    String extension;

    public MyImage(Page page, int[] coordinates1, int[] coordinates2, String imageName, boolean defaultVisible)
    {
        super(page, coordinates1, coordinates2, defaultVisible);
        imageFileName = imageName;
        extension = ".png";
    }

    public MyImage(Page page, int[] coordinates1, int[] coordinates2, String imageName, String fileExtension, boolean defaultVisible) {
        super(page, coordinates1, coordinates2, defaultVisible);
        imageFileName = imageName;
        extension = fileExtension;
    }

    public static MyImage putImage(Page page, int[] coordinates1, int width, String imageName) {
        final int[] coordinates2;

        final int[] imageDimensions = getImageFileDimensions(imageName);
        if (imageDimensions[0] == 0) {
            imageDimensions[0] = 100;
            System.out.println("There was an error loading image.");
        }
        coordinates2 = new int[] {coordinates1[0] + width, (int) (coordinates1[1] + imageDimensions[1] * ((float) width/imageDimensions[0]))};

        return new MyImage(page, coordinates1, coordinates2, imageName, true);
    }

    public static MyImage putImage(Page page, int[] coordinates1, int[] size, String imageName) {
        final int[] coordinates2 = new int[] {coordinates1[0] + size[0], coordinates1[1] + size[1]};

        return new MyImage(page, coordinates1, coordinates2, imageName, true);
    }

    public static int[] getImageFileDimensions(String fileName, String extension) {
        final BufferedImage image;
        try {
            image = ImageIO.read(new File("assets/" + fileName + extension));
        } catch (IOException e) {
            return new int[] {0, 0};
        }

        return new int[] {image.getWidth(), image.getHeight()};
    }

    public static int[] getImageFileDimensions(String fileName) {
        return getImageFileDimensions(fileName, ".png");
    }

    public void paint(Graphics g)
    {
        try
        {
            g.drawImage(ImageIO.read(new File("assets/" + imageFileName + extension)), corner1[0], corner1[1], corner2[0] - corner1[0], corner2[1] - corner1[1], null);
        }
        catch(IOException f) { System.out.println(imageFileName + " couldn't be found"); }
    }
}
