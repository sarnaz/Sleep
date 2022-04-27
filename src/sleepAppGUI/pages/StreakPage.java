package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourUtil;

import java.awt.*;

public class StreakPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 20;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {275, 30}, new int[] {530, 108}, "logo", true);
        new MyRectangle(page, new int[] {200, 170}, new int[] {400, 270}, 30, Color.white);
        new MyRectangle(page, new int[] {200, 170}, new int[] {400, 80}, 30, ColourUtil.foregroundColour);

        String username = Database.getUsername();
        MyText.putTextCentred(page, new int[] {400, 200}, new int[] {200, 18}, "Streaks", Color.white, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {400, 230}, new int[] {200, 30}, username, Color.white, "Helvetica", Font.BOLD);

        String currentStreak = String.valueOf(Database.getStreak());

        MyText.putText(page, new int[] {224, 283}, new int[] {300, 18}, "Your Current Streak:", Color.black);
        MyText.putTextCentred(page, new int[] {400, 375}, new int[] {100, 100}, currentStreak, ColourUtil.accentColour, "Helvetica", Font.BOLD);

        if(Database.getStreak() <= 1){
            MyText.putTextCentred(page, new int[] {400, 410}, new int[] {35, 30}, "Day", ColourUtil.accentColour, "Helvetica", Font.BOLD);
        } else {
            MyText.putTextCentred(page, new int[] {400, 410}, new int[] {35, 30}, "Days", ColourUtil.accentColour, "Helvetica", Font.BOLD);
        }

        MyButton backButton = new MyButton(page, "back", new int[] {275, 480}, new int[] {365, 510}, "back_button") {
            public void isClicked()
            {
                StreakPage.this.push(new HomePage());
                System.out.println("Main Menu Page");
            }
        };




    }
}
