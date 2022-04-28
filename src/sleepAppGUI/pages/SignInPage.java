package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

import java.awt.*;

public class SignInPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 1;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {0, 0}, new int[] {800, 600}, "first-page", ".jpg", true);

        // Adds the username input box on first page
        MyText.putText(page, new int[] {400, 230}, new int[] {70, 24}, "Username:", Color.white, "Helvetica", Font.BOLD);
        MyTextField usernameInput = new MyTextField(main, page, new int[] {400, 245}, new int[] {750, 275});

        // Adds the password boxes
        MyText.putText(page, new int[] {400, 310}, new int[] {70, 24}, "Password:", Color.white, "Helvetica", Font.BOLD);
        MyPasswordField passwordInput1 = new MyPasswordField(main, page, new int[] {400, 325}, new int[] {750, 355});

        MyImage openEye = new MyImage(page, new int[]{450, 250}, new int[]{470, 270}, "openEye", false);
        MyImage closedEye = new MyImage(page, new int[]{450, 250}, new int[]{470, 270}, "closedEye", true);

        page.pushToFront(openEye);
        page.pushToFront(closedEye);

        new MyButton(page, "show password", new int[] {450, 250}, new int[] {470, 270}, null)
        {
            public void isClicked()
            {
                System.out.println("clicked: " + ((passwordInput1.getTextVisibility()) ? "hiding" : "showing"));
                passwordInput1.setTextVisibility(!passwordInput1.getTextVisibility());
                openEye.setVisible(passwordInput1.getTextVisibility());
                closedEye.setVisible(!passwordInput1.getTextVisibility());
            }
        };

        // Add next button
        new MyButton(page, "next", new int[] {680, 365}, new int[] {750, 415}, "next-button") {    public void isClicked() {
            // basic input validation
            if (Database.validateUser(usernameInput.getText(), passwordInput1.getText()) == 1){
                SignInPage.this.push(new HomePage());
                System.out.println("Home Page");
            } else {
                System.out.println("Validate User Failed.");
            }
        }
        };

        new MyButton(page, "create new account", new int[] {620, 500}, new int[] {740, 550}, "create_account"){
            public void isClicked(){
                SignInPage.this.push(new SignUpPage());
            }
        };
    }

    @Override
    protected Color backgroundColor() {
        return new Color(0x6D3FB2);
    }
}
