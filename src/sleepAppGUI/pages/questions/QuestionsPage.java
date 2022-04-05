package sleepAppGUI.pages.questions;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;

abstract public class QuestionsPage extends UIViewPage {

    @Override
    protected void setUp(Page page) {
        //logo
        new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        // behind frame
        new MyImage(page, new int[] {120, 170}, new int[] {680, 380}, "box_behind", true);
        // hide bed
        new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "hideSquare", true);
    }
}
