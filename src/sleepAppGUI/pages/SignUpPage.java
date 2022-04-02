package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

public class SignUpPage extends UIViewPage {

    @Override
    protected int pageNumber() {
        return 2;
    }

    @Override
    protected void viewDidLoad() {
        System.out.println("Page loaded");
    }

    @Override
    protected void setUp(Page page) {
        // ADD THINGS TO FIRST PAGE
        // Adds logo
        MyImage logo = new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        // Adds the box to go behind input fields
        MyImage input_frame = new MyImage(page, new int[] {275, 170}, new int[] {525, 400}, "box_behind", true);
        // Adds the username input box on first page
        MyText usernameText = new MyText(page, new int[] {350, 200}, new int[] {365, 220}, "Username:");
        MyTextField usernameInput = new MyTextField(main, page, new int[] {300, 210}, new int[] {500, 235});
        // Adds the password boxes
        MyText password = new MyText(page, new int[] {350, 265}, new int[] {365, 285}, "Password:");
        MyPasswordField passwordInput1 = new MyPasswordField(main, page, new int[] {300, 275}, new int[] {500, 300});
        MyText passwordReenter = new MyText(page, new int[] {305, 330}, new int[] {320, 350}, "Re-enter Password:");
        MyPasswordField passwordInput2 = new MyPasswordField(main, page, new int[] {300, 340}, new int[] {500, 365});

        MyImage openEye = new MyImage(page, new int[]{450, 250}, new int[]{470, 270}, "openEye", false);
        MyImage closedEye = new MyImage(page, new int[]{450, 250}, new int[]{470, 270}, "closedEye", true);


        MyButton toggleShowPasswordButton = new MyButton(page, "show password", new int[] {450, 250}, new int[] {470, 270}, null)
        {
            public void isClicked()
            {
                System.out.println("clicked: " + ((passwordInput1.getTextVisibility()) ? "hiding" : "showing"));
                passwordInput1.setTextVisibility(!passwordInput1.getTextVisibility());
                passwordInput2.setTextVisibility(!passwordInput2.getTextVisibility());
                openEye.setVisible(passwordInput1.getTextVisibility());
                closedEye.setVisible(!passwordInput1.getTextVisibility());
            }
        };

        // Add next button
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 435}, "next")
        {
            public void isClicked()
            {
                // basic input validation
                // don't need to check for conflicting username because this is the only user
                if (passwordInput1.getText().equals(passwordInput2.getText()) &&
                        !usernameInput.getText().equals("") &&
                        !passwordInput1.getText().equals("")
                )
                {
                    Database.addUser(usernameInput.getText(), passwordInput1.getText());
                    SignUpPage.this.push(new MoreInfoPage());
                    System.out.println("More Info Page");
                }
                else
                {
                    System.out.println("password doesn't match or username not valid");
                }
            }
        };
    }

    @Override
    protected int backgroundColor() {
        return 0xC7EFF9;
    }
}
