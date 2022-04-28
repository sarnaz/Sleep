package sleepAppGUI.interaction;

import sleepAppGUI.pages.*;
import sleepAppGUI.visuals.*;

import java.awt.*;
import java.util.ArrayList;

import sleepAppDatabase.Database;

//whole class might be redundant
//might be better to make class "Page" instead, page contains list of buttons and graphical
//information, page id. buttons can lead to pages
public class Page
{
    public Page(int number, Main m)
    {
        pageNumber = number;
        backgroundColour = new Color(0x555555);
        m.addPage(this);
    }

    public Page(int number, Main m, Color c)
    {
        pageNumber = number;
        backgroundColour = c;
        m.addPage(this);
    }

    private final int pageNumber;
    private final Color backgroundColour;
    private final ArrayList<VObject> visualObjects = new ArrayList<>();
    private final ArrayList<MyButton> buttons = new ArrayList<>();
    private final ArrayList<MySlider> sliders = new ArrayList<>();
    private final ArrayList<MyTextField> textFields = new ArrayList<>();

    public void pushToFront(VObject o) {
        visualObjects.remove(o);
        visualObjects.add(o);
    }

    public void enterPage()
    {
        /* OLD
        for( MyTextField t : textFields)
        {
            t.setVisible(true);
        }
        //*/

        for( VObject obj : visualObjects)
        {
            obj.setVisible(obj.getDefVis());
        }
    }
    public void exitPage()
    {
        for(MyTextField t : textFields)
        {
            t.setVisible(false);
        }
    }

    public void addButton(MyButton b) { buttons.add(b); }
    public void addSlider(MySlider b) { sliders.add(b); }
    public void addObject(VObject obj) { visualObjects.add(obj); }
    public void addTextField(MyTextField t) { textFields.add(t); }
    public Color getColour() { return backgroundColour; }


    public void checkButtons(int[] clickCoordinates)
    {
        for (MyButton button : buttons)
        {
            if(button.onButton(clickCoordinates))
            {
                button.isClicked();
                return;
            }
        }

        for (MySlider slider : sliders)
        {
        	if (slider.onSlider(clickCoordinates))
        	{
            	slider.updatePosition(clickCoordinates);
        		slider.setSelected(true);
        	}
        }
    }

    public void checkSliderDrag(int[] dragCoordinates)
    {
        for(MySlider slider : sliders)
        {
            if(slider.onSlider(dragCoordinates))
            {
            	slider.updatePosition(dragCoordinates);
                return;
            }
        }
    }

    public void checkSliderRelease(int[] releaseCoordinates)
    {
        for(MySlider slider : sliders)
        {
            if(slider.onSlider(releaseCoordinates))
            {
            	slider.updatePosition(releaseCoordinates);
            	slider.setSelected(false);
                return;
            }
        }
    }

    public void paintObjects(Graphics g)
    {
        for(VObject obj : visualObjects)
        {
            if(obj.isVisible())
            {
                obj.paint(g);
            }
        }
    }

    public static void setUpPages(Main main) {
        UIStoryboard storyboard = new UIStoryboard(main, ColourPalette.backgroundColour);

        if (!Database.databaseExists()) {
        	Database.initialiseDatabase();
        }

        if (Database.checkForUsers()) {
            storyboard.present(new SignInPage());
        } else {
            storyboard.present(new SignUpPage());
        }
    }


}
