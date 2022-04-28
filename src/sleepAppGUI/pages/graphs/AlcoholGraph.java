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
        MyScatter alcohol_scatter = new MyScatter(page, new int[] {400,200}, new int[] {700,450});
        for (int i = 0; i < 5; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][1]!=null){
                alcohol_bar.addPoint(dayOfWeek[day%7],data[1][0]);
                if (data[1][3]!=null){
                    alcohol_scatter.addPoint(data[1][0],data[1][3]);
                }
            }
            calendar.add(Calendar.DATE,-1);
        }
    }
}
