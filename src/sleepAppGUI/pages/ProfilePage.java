package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

public class ProfilePage extends UIViewPage {

    @Override
    protected int pageNumber() {
        return 7;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);

        new MyImage(page, new int[] {275, 170}, new int[] {525, 470}, "profile_box", true);
        // add a function to database to return username only
        new MyText(page, new int[] {305, 230}, new int[] {340, 250}, "Username");

        String height = Integer.toString(Database.getUserHeight());
        String weight = Integer.toString(Database.getUserWeight());

        new MyText(page, new int[] {366, 298}, new int[] {388, 316}, height + " cm");
        new MyText(page, new int[] {366, 366}, new int[] {388, 384}, weight + " kg");

        // Add edit profile button
        new MyButton(page, "next", new int[] {400, 425}, new int[] {505, 450}, "edit_profile") {
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
