package sleepAppGUI.pages;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;

public class Example2Page extends UIViewPage {
    @Override
    protected int pageNumber() {
        return 124;
    }

    @Override
    protected void setUp(Page page) {
        new MyButton(page, "next", new int[]{70, 248}, new int[]{222, 271}, "next") {
            public void isClicked() {
                System.out.println("The button was pressed, go to Example2");
                Example2Page.this.push(new ExamplePage());
            }
        };
    }
}
