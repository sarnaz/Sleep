package sleepAppGUI.pages.graphs;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.interaction.*;
import sleepAppGUI.interaction.graphs.MyScatter;

import java.awt.*;
import java.util.*;


public class AlcoholGraph extends GraphPage {

    @Override
    protected int pageNumber() {
        return 13;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyText.putText(page, new int[] {110, 158}, 40, "Alcohol", Color.white, "Helvetica", Font.BOLD);

        MyBar alcohol_bar = new MyBar(page, new int[] {80,200}, new int[] {380,450});
        alcohol_bar.setGraphScale(10);
        MyScatter alcohol_scatter = new MyScatter(page, new int[] {400,200}, new int[] {700,450});
        calendar = Calendar.getInstance();
        System.out.println("IMPORTANT!!! \n\n\n\n\n\n\n\n\n");
        for (int i = 0; i < 5; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH)+1;
            int day= calendar.get(Calendar.DATE)-1;
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][0]!=null){
                int j = (int) data[1][0];
                double d = 1.0*j;
                alcohol_bar.addPoint(dayOfWeek[7 - (day % 7)],d);
                if (data[1][3]!=null){
                    int k = (int) data[1][3];
                    double e = 1.0*k;
                    alcohol_scatter.addPoint(d,e);
                    System.out.println("d: "+d+" e: "+e);
                }
            }
            calendar.add(Calendar.DATE,-1);
        }
        alcohol_scatter.setGraphScale(10, 10);
    }
}
