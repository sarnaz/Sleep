package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.goals.*;
import sleepAppGUI.visuals.ColourPalette;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

public class GoalPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 23;
    }
    
    private Color calculateColour(String type, Object target, Object actual) {
    	int targetValue;
    	int actualValue;
    	try {
    		targetValue = (int)target;
    		actualValue = (int)actual;
    	} catch (Exception e) {
    		return Color.black;
    	}
    	if (targetValue == actualValue) return Color.green;
    	return ( (targetValue < actualValue) == type.equals("maximise")) ? Color.GREEN : Color.RED;
    }
    
    @Override
    protected void setUp(Page page) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Object Goal_array[][] = Database.getGoalData();
        Object DailyQData_array[][] = Database.getDataForDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        new MyRectangle(page, new int[]{40, 21}, new int[]{720, 455}, 40, ColourPalette.lightForegroundColour);
        new MyRectangle(page, new int[]{40, 21}, new int[]{720, 80}, 40, ColourPalette.foregroundColour);
        MyText.putText(page, new int[]{56, 75}, 40, "Goals", Color.white, "Helvetica", Font.BOLD);

        new MyRectangle(page, new int[]{70, 127}, new int[]{152, 140}, 20, Color.white);
        new MyRectangle(page, new int[]{247, 125}, new int[]{152, 140}, 20, Color.white);
        new MyRectangle(page, new int[]{435, 243}, new int[]{290, 105}, 20, Color.white);
        new MyRectangle(page, new int[]{70, 306}, new int[]{152, 140}, 20, Color.white);
        new MyRectangle(page, new int[]{435, 360}, new int[]{290, 105}, 20, Color.white);
        new MyRectangle(page, new int[]{247, 306}, new int[]{152, 140}, 20, Color.white);
        new MyRectangle(page, new int[]{435, 123}, new int[]{290, 105}, 20, Color.white);
        // Water
        MyImage.putImage(page, new int[]{122, 160}, 50, "glass_icon");
        MyText.putTextCentred(page, new int[]{145, 150}, 18, "Water", Color.black, "Helvetica", Font.BOLD);
        if (DailyQData_array[1][2] != null) {
            MyText.putTextCentred(page, new int[]{145, 200}, 30, DailyQData_array[1][2] + " / " + Goal_array[1][0], calculateColour("maximise", Goal_array[1][0], DailyQData_array[1][2]));
        } else {
            MyText.putTextCentred(page, new int[]{145, 200}, 30, "Disabled", Color.black);
        }
        // Sleep
        MyImage.putImage(page, new int[]{295, 172}, 50, "bed_icon");
        MyText.putTextCentred(page, new int[]{323, 150}, 18, "Sleep", Color.black, "Helvetica", Font.BOLD);
        if (DailyQData_array[1][2] != null) {
            MyText.putTextCentred(page, new int[]{323, 200}, 30, DailyQData_array[1][3] + " / " + Goal_array[1][1], calculateColour("maximise", Goal_array[1][1], DailyQData_array[1][3]));
        } else {
            MyText.putTextCentred(page, new int[]{323, 200}, 30, "Disabled", Color.black);
        }
        // Stress
        MyImage.putImage(page, new int[] {559, 277}, 40, "stress_icon");
        MyText.putTextCentred(page, new int[] {579, 267}, 18, "Stress", Color.black, "Helvetica", Font.BOLD);
        if (DailyQData_array[1][6] != null) {
            MyText.putTextCentred(page, new int[]{579, 310}, 30, DailyQData_array[1][6] + " / " + Goal_array[1][5], calculateColour("minimise", Goal_array[1][5], DailyQData_array[1][6]));
        } else {
            MyText.putTextCentred(page, new int[]{579, 310}, 30, "Disabled", Color.black);
        }
        // Exercise
        MyImage.putImage(page, new int[] {117, 350}, 60, "exercise_icon");
        MyText.putTextCentred(page, new int[] {145, 330}, 18, "Exercise", Color.black, "Helvetica", Font.BOLD);
        if (DailyQData_array[1][8] != null) {
            MyText.putTextCentred(page, new int[]{145, 390}, 30, DailyQData_array[1][8] + " / " + Goal_array[1][2], calculateColour("maximise", Goal_array[1][2], DailyQData_array[1][8]));
        } else {
            MyText.putTextCentred(page, new int[]{145, 390}, 30, "Disabled", Color.black);
        }
        // Tea
        MyImage.putImage(page, new int[] {460, 390}, 210, "caffeine_icon");
        MyText.putTextCentred(page, new int[] {570, 384}, 14, "Tea", Color.black, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {570, 420}, 18, DailyQData_array[1][1] + " / " + Goal_array[1][7], calculateColour("minimise", Goal_array[1][7], DailyQData_array[1][1]));
        // Coffee
        MyText.putTextCentred(page, new int[] {477, 384}, 14, "Coffee", Color.black, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {477, 420},18, DailyQData_array[1][1] + " / " + Goal_array[1][6], calculateColour("minimise", Goal_array[1][6], DailyQData_array[1][1]));
        // Energy Drink
        MyText.putTextCentred(page, new int[] {658, 384}, 14, "Energy Drink", Color.black, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {658, 420}, 18, DailyQData_array[1][1] + " / " + Goal_array[1][8], calculateColour("minimise", Goal_array[1][8], DailyQData_array[1][1]));
        // Screen Time
        MyImage.putImage(page, new int[] {555, 157}, 60, "computer_icon");
        MyText.putTextCentred(page, new int[] {585, 147}, 18, "Screen Time", Color.black, "Helvetica", Font.BOLD);
        if (DailyQData_array[1][7] != null) {
            MyText.putTextCentred(page, new int[]{585, 190}, 30, DailyQData_array[1][7] + " / " + Goal_array[1][4], calculateColour("minimise", Goal_array[1][4], DailyQData_array[1][7]));
        } else {
            MyText.putTextCentred(page, new int[]{585, 190}, 30, "Disabled", Color.black);
        }
        // Alcohol
        MyImage.putImage(page, new int[] {303, 340}, 50, "alcohol_icon");
        MyText.putTextCentred(page, new int[] {325, 330}, 18, "Alcohol", Color.black, "Helvetica", Font.BOLD);
        if (DailyQData_array[1][7] != null) {
            MyText.putTextCentred(page, new int[]{325, 380}, 30, DailyQData_array[1][0] + " / " + Goal_array[1][3], calculateColour("minimise", Goal_array[1][3], DailyQData_array[1][0]));
        } else {
            MyText.putTextCentred(page, new int[]{325, 380}, 30, "Disabled", Color.black);
        }
        // buttons to edit goals
        new MyButton(page, "editWater", new int[]{70, 248}, new int[]{222, 271}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new WaterGoal());
                System.out.println("Edit Water Goal Page");
            }
        };
        new MyButton(page, "editSleep", new int[]{247, 248}, new int[]{399, 271}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new SleepGoal());
                System.out.println("Edit Sleep Goal Page");
            }
        };
        new MyButton(page, "editExercise", new int[]{70, 427}, new int[]{222, 450}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new ExerciseGoal());
                System.out.println("Edit Exercise Goal Page");
            }
        };
        new MyButton(page, "editAlcohol", new int[]{247, 427}, new int[]{399, 450}, "edit_goal_1"){
            public void isClicked()
            {
                GoalPage.this.push(new AlcoholGoal());
                System.out.println("Edit Alcohol Goal Page");
            }
        };
        new MyButton(page, "editScreenTime", new Rectangle(708, 123, 23,105), "edit_goal_2"){
            public void isClicked()
            {
                GoalPage.this.push(new ScreenGoal());
                System.out.println("Edit Screen Time Goal Page");
            }
        };
        new MyButton(page, "editStress", new Rectangle(708, 243, 23,105), "edit_goal_2"){
            public void isClicked()
            {
                GoalPage.this.push(new StressGoal());
                System.out.println("Edit Stress Goal Page");
            }
        };
        new MyButton(page, "editCaffeine", new Rectangle(708, 360, 23,105), "edit_goal_2"){
            public void isClicked()
            {
                GoalPage.this.push(new CaffeineGoal());
                System.out.println("Edit Caffeine Goal Page");
            }
        };
        new MyButton(page, "back", new int[] {100, 490}, new int[] {190, 520}, "back_button") {
            public void isClicked()
            {
                GoalPage.this.push(new HomePage());
                System.out.println("Home Page");
            }
        };
        new MyButton(page, "suggestions", new int[] {520, 490}, new int[] {662, 520}, "suggestions_button") {
            public void isClicked()
            {
                GoalPage.this.push(new SuggestionPage());
                System.out.println("Suggestions Page");
            }
        };
    }
}
