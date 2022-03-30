package sleepAppGUI.interaction;

import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.interaction.graphs.MyScatter;
import sleepAppGUI.visuals.*;

import java.awt.*;
import java.util.ArrayList;

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
    private ArrayList<VObject> visualObjects = new ArrayList<>();
    private ArrayList<MyButton> buttons = new ArrayList<>();
    private ArrayList<MyTextField> textFields = new ArrayList<>();

    private void pushToFront(VObject o)
    {
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
    public void addObject(VObject obj) { visualObjects.add(obj); }
    public void addTextField(MyTextField t) { textFields.add(t); }
    public Color getColour() { return backgroundColour; }


    public void checkButtons(int[] clickCoordinates)
    {
        for(MyButton button : buttons)
        {
            if(button.onButton(clickCoordinates))
            {
                button.isClicked();
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



    public static void setUpPages(Main main)
    {
        Page page1 = new Page(0, main);
        Page page2 = new Page(1, main, new Color(0xff0000));

        MyText cardDetailsText = new MyText(page1, new int[] {350, 150}, new int[] {450, 175}, "Credit card details:");
        MyTextField cardDetailsInput = new MyTextField(main, page1, new int[] {300, 200}, new int[] { 500, 240});

        MyText virusConsentText = new MyText(page1, new int[] {100, 350}, new int[] {550, 400}, "consent to virus installation ");

        MyImage tick = new MyImage(page1, new int[] {610, 335}, new int[] {660, 385}, "Tick", false);

        MyButton tickBox = new MyButton(page1, "n" ,new int[] {600, 350}, new int[] {650, 400}, "Box")
        {
            public void isClicked()
            {
                tick.setVisible(!tick.isVisible());
            }
        };

        MyText virusInstalledText = new MyText(page2, new int[] {200, 100}, new int[] { 500, 150}, "VIRUS INSTALLED");

        MyButton downloadButton = new MyButton(page1, "name", new int[] {195, 400}, new int[] {605, 550}, "virus button")
        {
            public void isClicked()
            {
                if(tick.isVisible())
                {
                    System.out.println("user consents to virus installation");
                }
                else { System.out.println("user does not consent to virus installation");}

                virusInstalledText.setText(cardDetailsInput.getText());

                main.setCurrentPage(page2);
            }
        };

        //puts the tick on top of the tick box
        page1.pushToFront(tick);

        MyButton backButton = new MyButton(page2, "name", new int[] {0, 0}, new int[] {50, 50}, "back button")
        {
            public void isClicked()
            {
                main.setCurrentPage(page1);
                System.out.println("virus uninstalled");
            }
        };




        main.setCurrentPage(page1);
    }
}
