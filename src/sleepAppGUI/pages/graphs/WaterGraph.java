package sleepAppGUI.pages.graphs;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyText;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.interaction.graphs.MyScatter;

import java.awt.*;
import java.util.Calendar;

public class WaterGraph extends GraphPage {
    @Override
    protected int pageNumber() {
        return 10;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyText.putText(page, new int[] {110, 158}, 40, "Water", Color.white, "Helvetica", Font.BOLD);

        MyBar water_bar = new MyBar(page, new int[] {80,200}, new int[] {380,450});
        MyScatter water_scatter = new MyScatter(page, new int[] {400,200}, new int[] {700,450});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH)+1;
            int day= calendar.get(Calendar.DATE)-1;
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][2]!=null){
                int j = (int) data[1][2];
                double d = 1.0*j;
                water_bar.addPoint(dayOfWeek[day%7],d);
                if (data[1][3]!=null){
                    int k = (int) data[1][3];
                    double e = 1.0*k;
                    water_scatter.addPoint(d,e);
                }
            }
            calendar.add(Calendar.DATE,-1);
        }
    }
}
