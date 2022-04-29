package sleepAppGUI.pages.goals;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.GoalPage;

public class ExerciseGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 26;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        MyTextField input = new MyTextField(main, page, new int[]{375, 205}, new int[]{425, 245});

        MyButton saveButton = new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                boolean valid = true;
                try {
                    Database.setGoals("exerciseDuration", Integer.parseInt(input.getText()));
                    System.out.println("Exercise Goal Saved");
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                    valid = false;
                }
                if (valid) {
                    Database.getGoalData();
                }
                ExerciseGoal.this.push(new GoalPage());
            }
        };
    }

    @Override
    protected String pageTitle() {
        return "Exercise";
    }

    @Override
    protected String unit() {
        return "hours";
    }

    @Override
    protected String imageName() {
        return "exercise_goal";
    }
}

