package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

public class StreakPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 20;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        new MyImage(page, new int[] {275, 170}, new int[] {525, 470}, "sleepStreaks", true);
        String username = Database.getUsername();
        new MyText(page, new int[] {400, 230}, new int[] {435, 250}, username);

        String currentStreak = String.valueOf(Database.getStreak());
        new MyText(page, new int[] {390, 380}, new int[] {435, 450}, currentStreak);
        if(Database.getStreak() == 1){
            new MyText(page, new int[] {400, 500}, new int[]{435, 520}, "day");
        }
        else{
            new MyText(page, new int[] {380, 410}, new int[]{415, 430}, "days");
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
