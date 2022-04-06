package sleepAppGUI.pages;

import sleepAppGUI.interaction.*;

public class EditProfilePage extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 8;
    }

    @Override
    protected void setUp(Page page) {
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
                EditProfilePage.this.push(new ProfilePage());
                System.out.println("Profile Page");
            }
        };
        MyButton deleteData = new MyButton (page, "delete data", new int[]{600, 475}, new int[]{750, 550}, "deleteData"){
            public void isClicked()
            {
                System.out.println("delete data (NOT IMPLEMENTED)");
            }
        };
    }
}
