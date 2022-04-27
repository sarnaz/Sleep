package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

abstract public class ProfileCommon extends UIViewPage {


    @Override
    protected void setUp(Page page) {
        MyImage.putImage(page, new int[] {250, 30}, 305, "logo");
        new MyRectangle(page, new int[] {200, 170}, new int[] {400, 290}, 30, Color.white);
        new MyRectangle(page, new int[] {200, 170}, new int[] {400, 80}, 30, ColourUtil.foregroundColour);

        String username = Database.getUsername();
        MyText.putTextCentred(page, new int[] {400, 200}, 18, "Profile", Color.white, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {400, 230}, 30, username, Color.white, "Helvetica", Font.BOLD);

        MyText.putTextCentred(page, new int[] {290, 295}, 27, "Height", Color.black, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {500, 295}, 27, "Weight", Color.black, "Helvetica", Font.BOLD);
    }
}
