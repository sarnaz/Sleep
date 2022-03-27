package sleepAppGUI.interaction.graphs;

import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.*;

import java.awt.*;
import java.util.ArrayList;

public class MyScatter extends MyGraph
{

    private final ArrayList<Double[]> data = new ArrayList<>();

    public MyScatter(Page page, int[] coordinates1, int[] coordinates2)
    {
        super(page, coordinates1, coordinates2, new Color(0xFFFFFF), new Color(0), new Color(0));
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        //updates graph scale
        if(!upToDate)
        {
            for(int axis = 0; axis < 2; axis++)
            {
                ArrayList<Double> axisData = new ArrayList<>();
                for (Double[] d : data)
                {
                    axisData.add(d[axis]);
                }
                System.out.println("setting graph scale for axis "+axis);
                if(!setGraphScale(axisData, axis)) { return; }
                axisData.clear();
            }
            upToDate = true;
        }

        //x axis lines
        int off = (int)(offEdge * (graphCorner2[1] - graphCorner1[1]));
        int increment = (graphCorner2[0] - graphCorner1[0]) / noOfSteps[0];
        g.setFont(new Font("Century Gothic", Font.PLAIN, off -1));
        for(int i = 0; i < noOfSteps[0] + 1; i++)
        {
            g.drawLine(graphCorner1[0] + (i * increment), graphCorner2[1], graphCorner1[0] + (i * increment), graphCorner2[1] + off);
            g.drawString( start[0] + (i * step[0]) + "", graphCorner1[0] + (i * increment), graphCorner2[1] + (2 * off));
        }

        //y axis lines?
        off = (int)(offEdge * (graphCorner2[0] - graphCorner1[0]));
        increment = (graphCorner2[1] - graphCorner1[1]) / noOfSteps[1];
        for(int i = 0; i < noOfSteps[1] + 1; i++)
        {
            g.drawLine(graphCorner1[0] - off, graphCorner2[1] - (i * increment), graphCorner1[0], graphCorner2[1] - (i * increment));
            g.drawString( start[1] + (i * step[1]) + "", graphCorner1[0] - (2 * off), graphCorner2[1] - (i * increment));
        }

        //drawing actual points
        g.setColor(points);
        for(Double[] point : data)
        {
            int[] pointCoordinate = new int[2];
            pointCoordinate[0] = (int)( ((point[0] - start[0]) / (end[0] - start[0])) * (graphCorner2[0] - graphCorner1[0]) ) + graphCorner1[0];
            pointCoordinate[1] = (int)( ((point[1] - start[1]) / (end[1] - start[1])) * (graphCorner2[1] - graphCorner1[1]) ) + graphCorner1[1];

            System.out.println("point coordinate: "+pointCoordinate[0]+", "+pointCoordinate[1]);

            g.fillOval(pointCoordinate[0], pointCoordinate[1], 5, 5);
        }
    }

    //max steps does not work

    //MUST TAKE 2 DOUBLES
    public boolean addPoint(Object xValue, Object yValue)
    {
        //if(xValue instanceof Double && yValue instanceof Double)
        try
        {
            //ONLY WORKS FOR 2D GRAPHS
            data.add(new Double[]{(double) (xValue), (double) (yValue)});
            upToDate = false; //CAN BE OPTIMISED
            //return true;
            return true;
        }
        catch(ClassCastException e)
        {
            System.out.println("bad input to addPoint");
            return false;
        }
    }


}
