package sleepAppGUI.pages.goals;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyTextField;
import sleepAppGUI.interaction.Page;

public class StressGoal extends GoalSet{
    @Override
    protected int pageNumber() {
        return 0;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[]{0, 10}, new int[]{800, 519}, "stress_goal", true);
        MyTextField input = new MyTextField(main, page, new int[]{475, 170}, new int[]{525, 210});

        super.setUp(page);
    }
}
