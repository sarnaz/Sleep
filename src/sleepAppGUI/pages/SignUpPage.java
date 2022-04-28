package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

import java.awt.*;

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
        // Adds background
        new MyImage(page, new int[] {0, 1}, new int[] {800, 600}, "first-page", ".jpg", true);

        new MyText(page, new int[] {400, 190}, new int[] {440, 215}, "Username:", Color.white, "Helvetica", Font.BOLD);
        MyTextField usernameInput = new MyTextField(main, page, new int[] {400, 205}, new int[] {750, 235});

        // Adds the password boxes
        new MyText(page, new int[] {400, 280}, new int[] {440, 305}, "Password:", Color.white, "Helvetica", Font.BOLD);
        MyPasswordField passwordInput1 = new MyPasswordField(main, page, new int[] {400, 295}, new int[] {750, 325});

        new MyText(page, new int[] {400, 370}, new int[] {440, 395}, "Re-enter Password:", Color.white, "Helvetica", Font.BOLD);
        MyPasswordField passwordInput2 = new MyPasswordField(main, page, new int[] {400, 385}, new int[] {750, 415});

        MyImage openEye = new MyImage(page, new int[]{540, 262}, new int[]{560, 282}, "openEye", false);
        MyImage closedEye = new MyImage(page, new int[]{540, 262}, new int[]{560, 282}, "closedEye", true);

        new MyButton(page, "show password", new int[]{540, 262}, new int[]{560, 282}, null)
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
        new MyButton(page, "next", new int[] {680, 420}, new int[] {750, 470}, "next-button")
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
    protected Color backgroundColor() {
        return new Color(0x6D3FB2);
    }
}
