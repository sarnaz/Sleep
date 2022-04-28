package sleepAppGUI.interaction.graphs;

import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.VObject;

import java.awt.*;
import java.util.ArrayList;

public abstract class MyGraph extends VObject
{
    protected final int[] graphCorner1;
    protected final int[] graphCorner2;
    protected final double offEdge = 0.05;
    protected final int[] offSides;
    protected boolean upToDate;
    protected final Color background, axis, points;

    final protected int maxSteps = 10;
    final protected int minSteps = 4;
    protected double[] step = new double[2], start = new double[2], end = new double[2];
    protected int[] noOfSteps = new int[2];

    public MyGraph(Page page, int[] coordinates1, int[] coordinates2, Color bbackground, Color aaxis, Color ppoints)
    {
        super(page, coordinates1, coordinates2, true);

        //double offEdge = 0.05;
        offSides = new int[] {(int)(offEdge * (corner2[0] - corner1[0])), (int)(offEdge * (corner2[1] - corner1[1]))};
        graphCorner1 = new int[] {corner1[0] + (2 * offSides[0]), corner1[1] + offSides[1]};
        graphCorner2 = new int[] {corner2[0] - offSides[0], corner2[1] - (2 * offSides[1])};
        System.out.println("graph created");

        background = bbackground;
        axis = aaxis;
        points = ppoints;
    }



    public void paint(Graphics g)
    {
        System.out.println("painting graph");
        //DEFINE ALL VARIABLES IN CONSTRUCTOR SO PAINTING IS QUICKER

        System.out.println("graph corner 1: "+graphCorner1[0]+", "+graphCorner1[1]+"\ngraph corner 2: "+graphCorner2[0]+", "+graphCorner2[1]);

        g.setColor(background);
        g.fillRect(corner1[0], corner1[1], corner2[0] - corner1[0], corner2[1] - corner1[1]);
        g.setColor(axis);
        g.drawLine(graphCorner1[0], graphCorner1[1], graphCorner1[0], corner2[1] - offSides[1]);
        g.drawLine(corner1[0] + offSides[0], graphCorner2[1], graphCorner2[0], graphCorner2[1]);

        //very inefficient to do every paint
    }

    protected void setGraphScale(double maxValue, int axis)
    {
        start[axis] = 0;
        end[axis] = maxValue;
        int msd = msdAndMag(end[axis] - start[axis])[0];
        if(msd >= minSteps && msd <= maxSteps)
        {
            noOfSteps[axis] = msd;
        }
        else if(msd * 10 <= maxSteps)
        {
            noOfSteps[axis] = msd * 10;
        }
        else
        {
            noOfSteps[axis] = msd * 2;
        }
        step[axis] = (end[axis] - start[axis])/noOfSteps[axis];
        upToDate = true;
    }

    //axis: 0 for x axis, 1 for y
    //sets step, noOfSteps, start, end;
    //max steps doesnt work
    protected boolean setGraphScale(ArrayList<Double> data, int axis)
    {
        System.out.println("##########################################");
        System.out.println("graph scale reset \n\n\n\n\n");
        System.out.println("##########################################");
        if(data.size() < 3) { System.out.println("not enough data"); return false; }

        double greatest = data.get(0);
        double smallest = data.get(0);

        for(Double d : data)
        {
            if(d > greatest)  { greatest = d; }
            else if(d < smallest)  { smallest = d; }
        }


        double difference = greatest - smallest; //difference between smallest and greatest

        //Spaghetti code from here on, good luck debugging
        //luckily it (mostly) works
        //first time test if maximal number of steps can be used
        int[] result = msdAndMag(difference * 2);
        int msd = result[0];
        int mag = result[1];


        if(msd >= minSteps && msd <= maxSteps)
        {
            step[axis] = Math.pow(10, msdAndMag(difference)[1]) / 2;
        }
        else
        {
            //testing if minimal number of steps works
            result = msdAndMag(difference /2);
            msd = result[0];
            mag = result[1];
            if(msd >= minSteps && msd <= maxSteps)
            {
                step[axis] = Math.pow(10, msdAndMag(difference)[1]) * 2;
            }

            //nothing else works, use normal set up
            else
            {
                result = msdAndMag(difference);
                msd = result[0];
                mag = result[1];

                step[axis] = Math.pow(10, mag);
            }
        }

        noOfSteps[axis] = msd + 3; //msd will depend on which section was run
        System.out.println("number of steps set to "+noOfSteps[axis]);


        int mod = 0;
        if(smallest % step[axis] == 0) { mod = 1; }
        start[axis] = smallest - (smallest % step[axis]) - (mod * step[axis]);
        end[axis] = start[axis] + (step[axis] * (noOfSteps[axis] -1)); //probably not needed

        System.out.println("Scale set. axis: "+axis+" \nstep: "+step[axis]+"\nnumber of steps: "+noOfSteps[axis]+"\nstart: "+start[axis]+"\nend: "+end[axis]);
        return true;
    }

    protected static int[] msdAndMag(double difference)
    {
        int msd; //most significant digit of difference
        int mag = 0; //magnitude of difference
        while(true)
        {
            if( difference / Math.pow(10, mag) < 1) { mag--; }
            else if( difference / Math.pow(10, mag) > 10) { mag++; }
            else { msd = (int)(difference / Math.pow(10, mag)); break; }
        }

        return new int[] {msd, mag};
    }

    public abstract boolean addPoint(Object xValue, Object yValue);

    public static float pmcc(ArrayList<Double> listX, ArrayList<Double> listY)
    {
        return (float)( sAB(listX, listY) / Math.sqrt(sAB(listX, listX) * sAB(listY, listY)) );
    }

    public static double getGradient(ArrayList<Double> listX, ArrayList<Double> listY)
    {
        return sAB(listX, listY) / sAB(listX, listX);
    }

    public static double getIntercept(ArrayList<Double> listX, ArrayList<Double> listY)
    {
        return (sumOf(listY) / listY.size()) - (getGradient(listX, listY) * sumOf(listX) / listX.size());
    }

    private static double sAB(ArrayList<Double> listA, ArrayList<Double> listB)
    {
        if(listA.size() != listB.size()) { return 0; }
        double sumAB = 0;
        for(int i = 0; i < listA.size(); i++)
        { sumAB += listA.get(i) * listB.get(i); }

        return sumAB - (sumOf(listA) * sumOf(listB) / listA.size());
    }
    private static double sumOf(ArrayList<Double> list)
    {
        double sum = 0;
        for(double d : list)  { sum += d; }
        return sum;
    }
}
