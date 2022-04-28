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

public class ExerciseGraph extends GraphPage{
    @Override
    protected int pageNumber() {
        return 11;
    }

    @Override
    protected void setUp(Page page) {

        MyText.putText(page, new int[] {110, 158}, 40, "Exercise", Color.white, "Helvetica", Font.BOLD);

        MyBar exercise_bar = new MyBar(page, new int[] {80,200}, new int[] {380,450});
        MyScatter exercise_scatter = new MyScatter(page, new int[] {400,200}, new int[] {700,450});
        for (int i = 0; i < 5; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][8]!=null){
                exercise_bar.addPoint(dayOfWeek[day%7],data[1][8]);
                if (data[1][3]!=null){
                    exercise_scatter.addPoint(data[1][8],data[1][3]);
                }
            }
            calendar.add(Calendar.DATE,-1);
        }
        super.setUp(page);
    }
}
