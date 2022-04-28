package sleepAppGUI.pages.questions;

import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.MyRectangle;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;
import sleepAppGUI.visuals.ColourPalette;

abstract public class QuestionsPage extends UIViewPage {

    @Override
    protected void setUp(Page page) {
        //logo
        MyImage.putImage(page, new int[] {200, 30}, 400, "logo");
        // behind frame
        new MyRectangle(page, new int[] {120, 170}, new int[] {560, 210}, 30, ColourPalette.foregroundColour);
        // hide bed
        new MyRectangle(page, new int[] {185, 15}, new int[] {150, 145}, ColourPalette.backgroundColour);
    }
}
