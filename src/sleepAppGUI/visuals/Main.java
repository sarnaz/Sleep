package sleepAppGUI.visuals;

import sleepAppGUI.interaction.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

public class Main
{
    private final GUI gui;
    private ArrayList<Page> pages = new ArrayList<>();
    private Page currentPage;

    public Main(int guiWidth, int guiHeight)
    {
        gui = new GUI(guiWidth, guiHeight, this);
        Page.setUpPages(this);
    }

    public void addTextField(JTextField t) { gui.addTextField(t); }
    public void addPage(Page p) { pages.add(p); }
    public Page getCurrentPage() { return currentPage; }
    public void setCurrentPage(Page page)
    {
        if(currentPage != null) { currentPage.exitPage(); }
        currentPage = page;
        currentPage.enterPage();
        //gui.setT1Vis(!gui.getT1Vis());
        gui.repaintCanvas();
    }

    public static void main(String[] args)
    {
        Main main = new Main(800, 600);
        Page.setUpPages(main);

    }
}
