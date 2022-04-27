package sleepAppGUI.interaction.graphs;

import sleepAppGUI.interaction.Page;

import java.awt.*;
import java.util.ArrayList;

public class MyBar extends MyGraph
{
    private final ArrayList<Double> data = new ArrayList<>();
    private final ArrayList<String> category = new ArrayList<>();

    public MyBar(Page page, int[] coordinates1, int[] coordinates2)
    {
        super(page, coordinates1, coordinates2, new Color(0xFFFFFF), new Color(0), new Color(0x0000FF));
    }

    public void setGraphScale(double maxYValue)
    {
        setGraphScale(maxYValue, 1);

        if(data.size() != 0) { step[0] = (graphCorner2[0] - graphCorner1[0]) / data.size(); }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        if(data.size() < 3) { return; }

        if(!upToDate)
        {
            setGraphScale(data, 1);

            step[0] = (graphCorner2[0] - graphCorner1[0]) / data.size();
        }

        //y axis lines?
        g.setColor(axis);
        int off = (int)(offEdge * (graphCorner2[0] - graphCorner1[0]));
        int increment = (graphCorner2[1] - graphCorner1[1]) / noOfSteps[1];
        g.setFont(new Font("Century Gothic", Font.PLAIN, off -2));
        for(int i = 0; i < noOfSteps[1] + 1; i++)
        {
            g.drawLine(graphCorner1[0] - off, graphCorner2[1] - (i * increment), graphCorner1[0], graphCorner2[1] - (i * increment));
            g.drawString( start[1] + (i * step[1]) + "", graphCorner1[0] - (2 * off), graphCorner2[1] - (i * increment));
        }

        off = (int)(offEdge * (graphCorner2[1] - graphCorner1[1]));

        //bars
        for(int i = 0; i < data.size(); i++)
        {
            int height = (int)( (graphCorner2[1] - graphCorner1[1]) * (data.get(i) - start[1]) / (end[1] - start[1]) );
            g.setColor(points);
            g.fillRect(graphCorner1[0] + (int)(step[0] * i), graphCorner2[1] - height, (int)step[0], height );
            g.setColor(axis);
            g.drawRect(graphCorner1[0] + (int)(step[0] * i), graphCorner2[1] - height, (int)step[0], height);
            g.drawString( category.get(i), graphCorner1[0] + (int)(i * step[0]), graphCorner2[1] + (off * (i % 2)) + off);
        }
    }

    public boolean addPoint(Object xValue, Object yValue)
    {
        //if(xValue instanceof String && yValue instanceof Double)
        try
        {
            category.add((String)(xValue));
            data.add((Double)(yValue));
            //Can be better
            if((Double)(yValue) > end[1])
            {
                upToDate = false;
                System.out.println((Double)(yValue)+" bigger than "+end[1]);
                System.out.println("      Up to date false");
            }
            step[0] = (graphCorner2[0] - graphCorner1[0]) / data.size();
            return true;
        }
        catch( ClassCastException e)
        {
            System.out.println("invalid data given to addPoint function");
            return false;
        }
    }
}
