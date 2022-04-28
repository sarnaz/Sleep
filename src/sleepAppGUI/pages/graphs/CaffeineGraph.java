package sleepAppGUI.pages.graphs;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyText;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.interaction.graphs.MyScatter;
import sleepAppGUI.pages.GraphVisual;
import sleepAppGUI.pages.HomePage;

import java.awt.*;
import java.util.Calendar;

public class CaffeineGraph extends GraphPage{
    @Override
    protected int pageNumber() {
        return 12;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyText.putText(page, new int[] {110, 158}, 40, "Caffeine", Color.white, "Helvetica", Font.BOLD);

        MyBar caffeine_bar = new MyBar(page, new int[] {80,200}, new int[] {380,450});
        MyScatter caffeine_scatter = new MyScatter(page, new int[] {400,200}, new int[] {700,450});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 5; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH)+1;
            int day= calendar.get(Calendar.DATE)-1;
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][1]!=null){
                int j = (int) data[1][1];
                double d = 1.0*j;
                caffeine_bar.addPoint(dayOfWeek[day%7],d);
                if (data[1][3]!=null){
                    int k = (int) data[1][3];
                    double e = 1.0*k;
                    caffeine_scatter.addPoint(d,e);
                }
            }


            calendar.add(Calendar.DATE,-1);
        }
    }
}
