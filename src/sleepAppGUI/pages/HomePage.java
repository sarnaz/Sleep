package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.SleepQuestions;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

public class HomePage extends UIViewPage {

    @Override
    protected int pageNumber() {
        return 6;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        new MyRectangle(page, new int[] {151, 130}, new int[] {500, 400}, 40, Color.white);
        new MyRectangle(page, new int[] {151, 130}, new int[] {500, 115}, 40, ColourUtil.foregroundColour);
        new MyImage(page, new int[] {275, 30}, new int[] {530, 108}, "logo", true);
        MyText.putText(page, new int[] {198, 210}, new int[] {200, 20}, "Let's check your activities", Color.white);

        //new MyImage(page, new int[] {150, 130}, new int[] {650, 530}, "home_page_layout", true);

        // show male icon for men and female icon for women
        new MyImage(page, new int[] {540, 137}, new int[] {640, 237}, "male_icon", true);
        new MyImage(page, new int[] {540, 137}, new int[] {640, 237}, "female_icon", true);

        // display username
        String username = Database.getUsername();
        MyText.putText(page, new int[] {198, 180}, new int[] {300, 30}, "Hello, " + username, Color.white, "Helvetica", Font.BOLD);

        // buttons
        if (Database.askDailyQuestionsCheck()) {
            MyButton dailyQuestions = new MyButton(page, "dailyQuestions", new int[]{170, 260}, new int[]{404, 361}, "questions") {
                public void isClicked() {
                    HomePage.this.push(new SleepQuestions());
                    System.out.println("Sleep questions");
                }
            };
        }
        else {
            MyButton streaksPage = new MyButton(page, "streakButton", new int[]{170, 260}, new int[]{404, 361}, "streak"){
                public void isClicked () {
                    HomePage.this.push(new StreakPage());
                    System.out.println("Streaks");
                }
            };
        }

        new MyButton(page, "goals", new int[]{170, 375}, new int[]{405, 515}, "goals"){
            public void isClicked()
            {
                HomePage.this.push(new GoalPage());
                System.out.println("Goal Page");
            }
        };

        new MyButton(page, "activity", new int[]{420, 260}, new int[]{634, 405}, "activity"){
            public void isClicked()
            {
                HomePage.this.push(new GraphVisual());
                System.out.println("Graph Page");
            }
        };

        new MyButton(page, "profile", new int[]{420, 418}, new int[]{634, 514}, "profile") {
            public void isClicked()
            {
                HomePage.this.push(new ProfilePage());
                System.out.println("Profile Page");
            }
        };
    }
}
