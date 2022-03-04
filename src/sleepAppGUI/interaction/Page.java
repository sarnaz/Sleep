package sleepAppGUI.interaction;

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
        m.addPage(this);
    }

    private final int pageNumber;
    private ArrayList<VObject> visualObjects = new ArrayList<>();
    private ArrayList<MyButton> buttons = new ArrayList<>();

    /*package private*/ void addButton(MyButton b) { buttons.add(b); }
    /*package private*/ void addObject(VObject obj) { visualObjects.add(obj); }

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
            obj.paint(g);
        }
    }



    public static void setUpPages(Main main)
    {
        Page page1 = new Page(0, main);
        Page page2 = new Page(1, main);
        MyButton b1 = new MyButton(page1, "name", new int[] {100, 100}, new int[] {511, 259}, "virus button")
        {
            public void isClicked()
            {
                main.setCurrentPage(page2);
            }
        };
        MyText text = new MyText(page2, new int[] {200, 100}, new int[] { 500, 150}, "VIRUS INSTALLED");

        MyButton b2 = new MyButton(page2, "name", new int[] {0, 0}, new int[] {50, 50}, "back button")
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
