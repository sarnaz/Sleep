package sleepAppGUI.pages.goals;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyTextField;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.pages.GoalPage;
import sleepAppGUI.pages.graphs.StressGraph;

public class StressGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 29;
    }

    @Override
    protected void setUp(Page page) {
        MyTextField input = new MyTextField(main, page, new int[]{375, 205}, new int[]{425, 245});

        MyButton saveButton = new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                boolean valid = true;
                try {
                    Database.setGoals("stress", Integer.parseInt(input.getText()));
                    System.out.println("Stress Goal Saved");
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    valid = false;
                }
                if (valid) {
                    Object Goal_array[][] = Database.getGoalData();
                }
                StressGoal.this.push(new GoalPage());
            }
        };

        super.setUp(page);
    }

    @Override
    protected String pageTitle() {
        return "Stress";
    }

    @Override
    protected String unit() {
        return "(average level)";
    }

    @Override
    protected String imageName() {
        return "stress_goal";
    }
}
