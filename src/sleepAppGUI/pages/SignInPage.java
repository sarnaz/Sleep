package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

public class SignInPage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 1;
    }

    @Override
    protected void setUp(Page page) {
        // Adds logo
        MyImage logo = new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        // Adds the box to go behind input fields
        MyImage input_frame = new MyImage(page, new int[] {275, 170}, new int[] {525, 350}, "box_behind", true);

        // Adds the username input box on first page
        MyText usernameText = new MyText(page, new int[] {350, 200}, new int[] {365, 220}, "Username:");
        MyTextField usernameInput = new MyTextField(main, page, new int[] {300, 210}, new int[] {500, 235});

        // Adds the password boxes
        MyText password = new MyText(page, new int[] {350, 265}, new int[] {365, 285}, "Password:");
        MyPasswordField passwordInput1 = new MyPasswordField(main, page, new int[] {300, 275}, new int[] {500, 300});
        MyImage openEye = new MyImage(page, new int[]{450, 250}, new int[]{470, 270}, "openEye", false);
        MyImage closedEye = new MyImage(page, new int[]{450, 250}, new int[]{470, 270}, "closedEye", true);

        page.pushToFront(openEye);
        page.pushToFront(closedEye);

        MyButton toggleShowPasswordButton = new MyButton(page, "show password", new int[] {450, 250}, new int[] {470, 270}, null)
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
        MyButton nextButton = new MyButton(page, "next", new int[] {360, 400}, new int[] {440, 435}, "next") {
            public void isClicked() {
                // basic input validation
                if(Database.validateUser(usernameInput.getText(), passwordInput1.getText()) == 1){
                    SignInPage.this.push(new HomePage());
                    System.out.println("More Info Page");
                }
                else{
                    System.out.println("User invalid");
                }
            }
        };

        MyButton createAccount = new MyButton(page, "create new account", new int[] {570, 500}, new int[] {730, 550}, "create_account"){
            public void isClicked(){
                SignInPage.this.push(new SignUpPage());
            }
        };
    }
}
