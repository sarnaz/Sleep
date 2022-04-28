package sleepAppGUI.pages;

import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourPalette;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

import sleepAppDatabase.Database;

public class SuggestionPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 22;
    }

    @Override
    protected void viewDidLoad() {

    }
    
    private boolean calculateGoalMet(String type, Object target, Object actual) {
    	int targetValue;
    	int actualValue;
    	try {
    		targetValue = (int)target;
    		actualValue = (int)actual;
    	} catch (Exception e) {
    		return true;
    	}
    	if (targetValue == actualValue) return true;
    	return ( (targetValue < actualValue) == type.equals("maximise")) ? true : false;
    }

    @Override
    protected void setUp(Page page) {
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Object Goal_array[][] = Database.getGoalData();
        Object DailyQData_array[][] = Database.getDataForDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        
        // work out which goals have been met. for any goals that haven't been met, show suggestions for that goal
        
    	
        MyImage.putImage(page, new int[] {300, 30}, 200, "logo");
        new MyRectangle(page, new int[] {40, 120}, new int[] {720, 350}, 30, ColourPalette.foregroundColour);

        MyText.putText(page, new int[] {50, 160}, 30, "Suggestions", Color.white, "Helvetica", Font.BOLD);
        
        if (!calculateGoalMet("maximise", Goal_array[1][0], DailyQData_array[1][2])) {
        	MyText.putText(page, new int[] {50, 200}, 20, "WATER: You can try eating fruit/vegetables that have high levels of moisture.");
        }

        if (!calculateGoalMet("minimise", Goal_array[1][6], DailyQData_array[1][1])) {
        	MyText.putText(page, new int[] {50, 230}, 20, "CAFFEINE: You can try drinking more water, decaf coffee and check caffeine");
            MyText.putText(page, new int[] {50, 250}, 20, "mg on each product.");
        }
        
        if (!calculateGoalMet("minimise", Goal_array[1][3], DailyQData_array[1][0])) {
	        MyText.putText(page, new int[] {50, 280}, 20, "ALCOHOL: You can try drinking other liquids, taking fixed budged for drinks");
	        MyText.putText(page, new int[] {50, 300}, 20, "when going out.");
        }
        
        if (!calculateGoalMet("minimise", Goal_array[1][5], DailyQData_array[1][6])) {
        	MyText.putText(page, new int[] {50, 330}, 20, "STRESS: You can try having a healthy diet, exercise regularly and stop using");
        	MyText.putText(page, new int[] {50, 350}, 20, "nicotine products.");
        }
        
        if (!calculateGoalMet("maximise", Goal_array[1][2], DailyQData_array[1][8])) {
	        MyText.putText(page, new int[] {50, 380}, 20, "EXERCISE: You can try breaking workout down into small sessions,");
	        MyText.putText(page, new int[] {50, 400}, 20, "exercise with a friend.");
        }
        
        MyText.putText(page, new int[] {50, 430}, 20, "SCREEN-TIME: You can try scheduling \"Do Not Disturb\" or Bedtime mode");
        MyButton backButton = new MyButton(page, "back", new int[] {345, 500}, new int[] {455, 538}, "back_button")
        {
            public void isClicked()
            {
                SuggestionPage.this.push(new GoalPage());
            }
        };
    }
}
