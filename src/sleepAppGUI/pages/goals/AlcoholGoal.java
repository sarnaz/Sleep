package sleepAppGUI.pages.goals;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyTextField;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.GoalPage;

public class AlcoholGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 27;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[]{0, 10}, new int[]{800, 519}, "alcohol_goal", true);
        MyTextField input = new MyTextField(main, page, new int[]{375, 170}, new int[]{425, 210});

        MyButton saveButton = new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                boolean valid = true;
                try {
                    Database.setGoals("units", Integer.parseInt(input.getText()));
                    System.out.println("Alcohol Goal Saved");
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    valid = false;
                }
                if (valid == true) {
                    Object Goal_array[][] = Database.getGoalData();
                }
                AlcoholGoal.this.push(new GoalPage());
            }
        };

        super.setUp(page);
    }
}
