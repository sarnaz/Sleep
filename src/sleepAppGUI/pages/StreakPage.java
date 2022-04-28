package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourPalette;

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
        new MyRectangle(page, new int[] {200, 170}, new int[] {400, 80}, 30, ColourPalette.foregroundColour);

        String username = Database.getUsername();
        MyText.putTextCentred(page, new int[] {400, 200}, 18, "Streaks", Color.white, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {400, 230}, 40, username, Color.white, "Helvetica", Font.BOLD);

        String currentStreak = String.valueOf(Database.getStreak());

        MyText.putText(page, new int[] {224, 283}, new int[] {300, 18}, "Your Current Streak:", Color.black);
        MyText.putTextCentred(page, new int[] {400, 325}, 50, currentStreak, ColourPalette.accentColour, "Helvetica", Font.BOLD);

        if(Database.getStreak() == 1){
            MyText.putTextCentred(page, new int[] {400, 360}, 25, "Day", ColourPalette.accentColour, "Helvetica", Font.BOLD);
        } else {
            MyText.putTextCentred(page, new int[] {400, 360}, 25, "Days", ColourPalette.accentColour, "Helvetica", Font.BOLD);
        }

        MyText.putText(page, new int[] {224, 390}, new int[] {300, 18}, "Your Current Level:", Color.black);
        int level = 0;
        int points = Database.getPoints();
        System.out.println(points);
        boolean higher = true;
        int calculate_level = 1;
        int level_points;
        String points_til_next = "0";
        while(higher == true){
            level_points = (5*calculate_level*calculate_level) + (5*calculate_level);
            //higher = false;
            if(level_points <= points){
                calculate_level++;
                level++;
            }
            else{
                points_til_next = String.valueOf(level_points - points);
                higher = false;
            }
        };
        String current_level = String.valueOf(level);
        MyText.putText(page, new int[] {400, 390}, new int[] {300, 18}, current_level, Color.black);
        MyText.putText(page, new int[] {224, 420}, new int[] {300, 18}, "Points Til Next Level:", Color.black);
        MyText.putText(page, new int[] {400, 420}, new int[] {300, 18}, points_til_next, Color.black);
        MyButton backButton = new MyButton(page, "back", new int[] {275, 480}, new int[] {365, 510}, "back_button") {
            public void isClicked()
            {
                StreakPage.this.push(new HomePage());
                System.out.println("Main Menu Page");
            }
        };




    }
}
