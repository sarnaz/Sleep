package sleepAppGUI.pages.graphs;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.interaction.graphs.MyScatter;
import sleepAppGUI.pages.GraphVisual;
import sleepAppGUI.pages.HomePage;

import java.util.Calendar;

public class CaffeineGraph extends GraphPage{
    @Override
    protected int pageNumber() {
        return 12;
    }

    @Override
    protected void setUp(Page page) {

        MyBar caffeine_bar = new MyBar(page, new int[] {80,200}, new int[] {380,450});
        MyScatter caffeine_scatter = new MyScatter(page, new int[] {400,200}, new int[] {700,450});
        Calendar calendar = Calendar.getInstance();
        String[] dayofweek = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};
        for (int i = 0; i < 5; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][1]!=null){
                caffeine_bar.addPoint(dayofweek[day%7],data[1][1]);
                if (data[1][3]!=null){
                    caffeine_scatter.addPoint(data[1][1],data[1][3]);
                }
            }


            calendar.add(Calendar.DATE,-1);
        }

        super.setUp(page);
    }
}
