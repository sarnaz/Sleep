package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.goals.*;

import java.util.Calendar;

public class GoalPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 23;
    }

    @Override
    protected void setUp(Page page) {
        Object Goal_array[][] = Database.getGoalData();
        Object DailyQData_array[][] = Database.getDataForDate(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);


        MyImage layout = new MyImage(page, new int[] {0, 20}, new int[] {800, 476}, "goal_layout", true);

        // example of how to display goal
        MyText watergoal = new MyText(page, new int[] {110, 200}, new int[] {320, 230}, DailyQData_array[1][2] + " / " + Goal_array[1][0]);
        MyText sleepgoal = new MyText(page, new int[] {280, 200}, new int[] {320, 230}, DailyQData_array[1][3] + " / " + Goal_array[1][1]);
        MyText stressgoal = new MyText(page, new int[] {540, 290}, new int[] {740, 320}, DailyQData_array[1][6] + " / " + Goal_array[1][5]);
        MyText exercisegoal = new MyText(page, new int[] {110, 380}, new int[] {520, 410}, DailyQData_array[1][8] + " / " + Goal_array[1][2]);
        MyText caffeinegoal = new MyText(page, new int[] {540, 410}, new int[] {740, 440}, DailyQData_array[1][1] + " / " + Goal_array[1][6]);
        MyText screentimegoal = new MyText(page, new int[] {540, 180}, new int[] {740, 210}, DailyQData_array[1][7] + " / " + Goal_array[1][4]);
        MyText alcoholgoal = new MyText(page, new int[] {280, 380}, new int[] {320, 410}, DailyQData_array[1][0] + " / " + Goal_array[1][3]);

        // buttons to edit goals
        MyButton EditWaterGoal = new MyButton(page, "editWater", new int[]{70, 248}, new int[]{222, 271}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new WaterGoal());
                System.out.println("Edit Water Goal Page");
            }
        };
        MyButton EditSleepGoal = new MyButton(page, "editSleep", new int[]{247, 248}, new int[]{399, 271}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new SleepGoal());
                System.out.println("Edit Sleep Goal Page");
            }
        };
        MyButton EditExerciseGoal = new MyButton(page, "editExercise", new int[]{70, 427}, new int[]{222, 450}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new ExerciseGoal());
                System.out.println("Edit Exercise Goal Page");
            }
        };
        MyButton EditAlcoholGoal = new MyButton(page, "editAlcohol", new int[]{247, 427}, new int[]{399, 450}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new AlcoholGoal());
                System.out.println("Edit Alcohol Goal Page");
            }
        };
        MyButton EditScreenTimeGoal = new MyButton(page, "editScreenTime", new int[]{709, 113}, new int[]{731, 196}, "edit_goal_2"){
            public void isClicked()
            {
                GoalPage.this.push(new ScreenGoal());
                System.out.println("Edit Screen Time Goal Page");
            }
        };
        MyButton EditStressGoal = new MyButton(page, "editStress", new int[]{709, 220}, new int[]{731, 321}, "edit_goal_2"){
            public void isClicked()
            {
                GoalPage.this.push(new StressGoal());
                System.out.println("Edit Stress Goal Page");
            }
        };
        MyButton EditCaffeineGoal = new MyButton(page, "editCaffeine", new int[]{709, 345}, new int[]{731, 450}, "edit_goal_2"){
            public void isClicked()
            {
                GoalPage.this.push(new CaffeineGoal());
                System.out.println("Edit Caffeine Goal Page");
            }
        };
        MyButton backButton = new MyButton(page, "back", new int[] {100, 490}, new int[] {190, 520}, "back_button") {
            public void isClicked()
            {
                GoalPage.this.push(new HomePage());
                System.out.println("Home Page");
            }
        };
        MyButton suggestionsButton = new MyButton(page, "suggestions", new int[] {520, 490}, new int[] {662, 520}, "suggestions_button") {
            public void isClicked()
            {
                GoalPage.this.push(new SuggestionPage());
                System.out.println("Suggestions Page");
            }
        };
    }
}
