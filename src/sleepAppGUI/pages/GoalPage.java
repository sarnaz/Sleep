package sleepAppGUI.pages;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.goals.*;

public class GoalPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 23;
    }

    @Override
    protected void setUp(Page page) {
        MyImage layout = new MyImage(page, new int[] {0, 20}, new int[] {800, 476}, "goal_layout", true);

        // example of how to display goal
        MyText goal_example = new MyText(page, new int[] {110, 200}, new int[] {320, 230}, "5 / 10");

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
