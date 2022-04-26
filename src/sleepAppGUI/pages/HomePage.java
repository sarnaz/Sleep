package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.questions.SleepQuestions;

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
        new MyImage(page, new int[] {150, 130}, new int[] {650, 530}, "home_page_layout", true);
        new MyImage(page, new int[] {260, 30}, new int[] {560, 108}, "logo", true);

        // show male icon for men and female icon for women
        new MyImage(page, new int[] {550, 157}, new int[] {610, 217}, "male_icon", true);
        new MyImage(page, new int[] {550, 157}, new int[] {610, 217}, "female_icon", true);

        // display username
        String username = Database.getUsername();
        new MyText(page, new int[] {270, 178}, new int[] {320, 198}, username);

        // buttons
        if(Database.askDailyQuestionsCheck() == true) {
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
                    HomePage.this.push(new Streaks());
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
