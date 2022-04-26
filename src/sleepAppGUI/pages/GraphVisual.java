package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;
import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.pages.graphs.*;

import java.util.Calendar;

public class GraphVisual extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 5;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        String[] dayofweek = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};

        MyImage water_frame = new MyImage(page, new int[] {10, 10}, new int[] {260, 265}, "box_behind", true);
        MyImage water = new MyImage(page, new int[] {60, 220}, new int[] {100, 260}, "waternotext", true);
        MyButton waterbutton = new MyButton(page, "water", new int[] {110, 220}, new int[] {220, 255}, "moreinfo")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new WaterGraph());
                System.out.println("water graph");
            }
        };
        MyBar water_bar = new MyBar(page, new int[] {15,30}, new int[] {255,210});
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][2]!=null){
                water_bar.addPoint(dayofweek[day%7],data[1][2]);
            }
            calendar.add(Calendar.DATE,-1);
        }

        MyImage exercise_frame = new MyImage(page, new int[] {270, 10}, new int[] {520, 265}, "box_behind", true);
        MyImage exercise = new MyImage(page, new int[] {320, 220}, new int[] {360, 260}, "exercisenotext", true);
        MyButton exercisebutton = new MyButton(page, "exercise", new int[] {370, 220}, new int[] {480, 255}, "moreinfo")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new ExerciseGraph());
                System.out.println("exercise graph");
            }
        };
        MyBar exercise_bar = new MyBar(page, new int[] {275,30}, new int[] {515,210});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][8]!=null){
                exercise_bar.addPoint(dayofweek[day%7],data[1][8]);
            }
            calendar.add(Calendar.DATE,-1);
        }

        MyImage screentime_frame = new MyImage(page, new int[] {530, 10}, new int[] {780, 265}, "box_behind", true);
        MyImage screentime = new MyImage(page, new int[] {580, 220}, new int[] {620, 260}, "screentimenotext", true);
        MyButton screenbutton = new MyButton(page, "screen", new int[] {630, 220}, new int[] {740, 255}, "moreinfo")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new ScreenTimeGraph());
                System.out.println("screen graph");
            }
        };
        MyBar screen_bar = new MyBar(page, new int[] {535,30}, new int[] {775,210});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][7]!=null){
                screen_bar.addPoint(dayofweek[day%7],data[1][7]);
            }
            calendar.add(Calendar.DATE,-1);
        }


        MyImage alcohol_frame = new MyImage(page, new int[] {10, 270}, new int[] {260, 530}, "box_behind", true);
        MyImage alcohol = new MyImage(page, new int[] {60, 485}, new int[] {100, 525}, "alcoholnotext", true);
        MyButton alcoholbutton = new MyButton(page, "alcohol", new int[] {110, 485}, new int[] {220, 520}, "moreinfo")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new AlcoholGraph());
                System.out.println("alcohol graph");
            }
        };
        MyBar alcohol_bar = new MyBar(page, new int[] {15,290}, new int[] {255,475});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][0]!=null){
                alcohol_bar.addPoint(dayofweek[day%7],data[1][0]);
            }
            calendar.add(Calendar.DATE,-1);
        }

        MyImage caffeine_frame = new MyImage(page, new int[] {270, 270}, new int[] {520, 530}, "box_behind", true);
        MyImage caffeine = new MyImage(page, new int[] {320, 485}, new int[] {360, 525}, "caffeinenotext", true);
        MyButton caffeinebutton = new MyButton(page, "caffeine", new int[] {370, 485}, new int[] {480, 520}, "moreinfo")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new CaffeineGraph());
                System.out.println("caffeine graph");
            }
        };
        MyBar caffeine_bar = new MyBar(page, new int[] {275,290}, new int[] {515,475});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][1]!=null){
                caffeine_bar.addPoint(dayofweek[day%7],data[1][1]);
            }

            calendar.add(Calendar.DATE,-1);
        }


        MyImage stress_frame = new MyImage(page, new int[] {530, 270}, new int[] {780, 530}, "box_behind", true);
        MyImage stress = new MyImage(page, new int[] {580, 485}, new int[] {620, 525}, "stressnotext", true);
        MyButton stessbutton = new MyButton(page, "stress", new int[] {630, 485}, new int[] {740, 520}, "moreinfo")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new StressGraph());
                System.out.println("stress graph");
            }
        };
        MyBar stress_bar = new MyBar(page, new int[] {535,290}, new int[] {775,475});
        calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day= calendar.get(Calendar.DATE);
            Object[][] data = Database.getDataForDate(year,month,day);
            if (data[1][1]!=null){
                stress_bar.addPoint(dayofweek[day%7],data[1][1]);
            }
            calendar.add(Calendar.DATE,-1);
        }


        MyButton homebutton = new MyButton(page, "home", new int[] {245, 240}, new int[] {290, 280}, "home")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new HomePage());
                System.out.println("home");
            }
        };

        MyButton settingsbutton = new MyButton(page, "setting", new int[] {505, 240}, new int[] {550, 285}, "setting")
        {
            public void isClicked()
            {
                GraphVisual.this.push(new MoreInfoPage());
                System.out.println("moreinfo");
            }
        };
    }
}
