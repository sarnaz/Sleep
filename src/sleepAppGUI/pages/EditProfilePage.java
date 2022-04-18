package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

public class EditProfilePage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 8;
    }

    @Override
    protected void setUp(Page page) {
        new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        new MyImage(page, new int[] {275, 170}, new int[] {525, 470}, "profile_box", true);
        // add a function to database to return username only
        new MyText(page, new int[] {305, 230}, new int[] {340, 250}, "Username");
        MyTextField heightInput = new MyTextField(main, page, new int[] {370, 280}, new int[] {398, 305});
        MyText cm = new MyText(page, new int[] {401, 298}, new int[] {406, 316}, "cm");
        MyTextField weightInput = new MyTextField(main, page, new int[] {370, 345}, new int[] {398, 370});
        MyText kg = new MyText(page, new int[] {401, 363}, new int[] {406, 381}, "kg");

        MyButton cancelButton = new MyButton(page, "cancel", new int[]{295, 425}, new int[]{380, 450}, "cancel_button") {
            public void isClicked()
            {
                EditProfilePage.this.push(new ProfilePage());
                System.out.println("Profile Page");
            }
        };
        MyButton saveButton = new MyButton(page, "save", new int[]{435, 425}, new int[]{505, 450}, "save_button") {
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
                if(valid == true) {
                    Database.setUserWeight(newWeight);
                    Database.setUserHeight(newHeight);
                    EditProfilePage.this.push(new ProfilePage());
                    System.out.println("Profile Page");
                }
            }
        };
        MyButton deleteData = new MyButton (page, "delete data", new int[]{600, 475}, new int[]{750, 550}, "deleteData"){
            public void isClicked()
            {
                Database.removeUserData(Database.getCurrentUserId());
                System.out.println("Data removed");
            }
        };
    }
}
