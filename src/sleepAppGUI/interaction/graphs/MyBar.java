package sleepAppGUI.interaction.graphs;

import sleepAppGUI.interaction.Page;

import java.awt.*;
import java.util.ArrayList;

public class MyBar extends MyGraph
{
    private ArrayList<Double> data = new ArrayList<>();
    private ArrayList<String> category = new ArrayList<>();

    public MyBar(Page page, int[] coordinates1, int[] coordinates2)
    {
        super(page, coordinates1, coordinates2, new Color(0xFFFFFF), new Color(0), new Color(0));
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        if(!upToDate)
        {
            setGraphScale(data, 1);
        }
    }

    public boolean addPoint(Object xValue, Object yValue)
    {
        if(xValue instanceof String && yValue instanceof Double)
        {
            category.add((String)(xValue));
            data.add((Double)(yValue));
            //Can be better
            upToDate = false;
            return true;
        }
        System.out.println("invalid data given to addPoint function");
        return false;
    }
}
