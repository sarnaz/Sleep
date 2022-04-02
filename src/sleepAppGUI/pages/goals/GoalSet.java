package sleepAppGUI.pages.goals;

import sleepAppGUI.interaction.*;
import sleepAppGUI.pages.GoalPage;

abstract public class GoalSet extends UIViewPage {
    @Override
    protected void setUp(Page page) {
        MyButton backButton = new MyButton(page, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                GoalSet.this.push(new GoalPage());
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(page, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                GoalSet.this.push(new GoalPage());
                System.out.println("Saved");
            }
        };
    }
}
