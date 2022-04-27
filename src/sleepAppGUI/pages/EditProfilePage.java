package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;
import sleepAppGUI.visuals.ColourUtil;

import javax.swing.*;

public class EditProfilePage extends ProfileCommon {
    @Override
    protected int pageNumber() {
        return 8;
    }

    @Override
    protected void setUp(Page page) {
        super.setUp(page);

        //change height and weight here
        MyTextField heightInput = new MyTextField(main, page, new int[] {230, 310}, new int[] {350, 380}, "Helvetica", 50, JTextField.CENTER);
        MyTextField weightInput = new MyTextField(main, page, new int[] {440, 310}, new int[] {560, 380}, "Helvetica", 50, JTextField.CENTER);

        MyText.putTextCentred(page, new int[] {290, 400}, 20, "cm", ColourUtil.accentColour);
        MyText.putTextCentred(page, new int[] {500, 400}, 20, "kg", ColourUtil.accentColour);
        new MyButton(page, "cancel", new int[]{285, 420}, new int[]{390, 450}, "cancel_button") {
            public void isClicked()
            {
                EditProfilePage.this.push(new ProfilePage());
                System.out.println("Profile Page");
            }
        };

        new MyButton(page, "save", new int[]{425, 420}, new int[]{515, 450}, "save_button") {
            public void isClicked()
            {
                boolean valid = true;
                int newWeight = 0;
                int newHeight = 0;
                try{
                    newWeight = Integer.parseInt(weightInput.getText());
                    newHeight = Integer.parseInt(heightInput.getText());
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid");
                    valid = false;
                }
                if (valid) {
                    Database.setUserWeight(newWeight);
                    Database.setUserHeight(newHeight);
                    EditProfilePage.this.push(new ProfilePage());
                    System.out.println("Profile Page");
                }
            }
        };

        new MyButton (page, "delete data", new int[]{620, 475}, new int[]{770, 550}, "deleteData"){
            public void isClicked()
            {
                Database.removeUserData(Database.getCurrentUserId());
                System.out.println("Data removed");
            }
        };
    }
}
