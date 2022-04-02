package sleepAppGUI.pages.goals;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyTextField;
import sleepAppGUI.interaction.Page;

public class ExerciseGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 26;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[]{0, 10}, new int[]{800, 519}, "exercise_goal", true);
        MyTextField input = new MyTextField(main, page, new int[]{375, 170}, new int[]{425, 210});

        super.setUp(page);
    }
}

