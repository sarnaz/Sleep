package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourPalette;

import java.awt.*;

public class ProfilePage extends ProfileCommon {

    @Override
    protected int pageNumber() {
        return 7;
    }


    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        String height = Integer.toString(Database.getUserHeight());
        String weight = Integer.toString(Database.getUserWeight());

        MyText.putTextCentred(page, new int[] {290, 360}, 50, height, ColourPalette.accentColour, "Helvetica", Font.BOLD);
        MyText.putTextCentred(page, new int[] {500, 360}, 50, weight, ColourPalette.accentColour, "Helvetica", Font.BOLD);

        MyText.putTextCentred(page, new int[] {290, 400}, 20, "cm", ColourPalette.accentColour);
        MyText.putTextCentred(page, new int[] {500, 400}, 20, "kg", ColourPalette.accentColour);

        // Add edit profile button
        new MyButton(page, "edit", new int[] {350, 425}, new int[] {450, 450}, "edit_profile") {
            public void isClicked()
            {
                ProfilePage.this.push(new EditProfilePage());
                System.out.println("Edit Profile Page");
            }
        };
        MyButton backButton = new MyButton(page, "back", new int[] {275, 480}, new int[] {365, 510}, "back_button") {
            public void isClicked()
            {
                ProfilePage.this.push(new HomePage());
                System.out.println("Main Menu Page");
            }
        };
        MyButton logoutButton = new MyButton(page, "logout", new int[] {405, 480}, new int[] {525, 510}, "logout_button") {
            public void isClicked()
            {
                ProfilePage.this.push(new SignInPage());
                System.out.println("Sign In Page");
            }
        };
    }
}
