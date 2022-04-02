package sleepAppGUI.pages;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;

import java.awt.*;

public class ExamplePage extends UIViewPage {

    @Override
    protected int pageNumber() {
        return 123;
    }

    @Override
    protected void setUp(Page page) {
        new MyButton(page, "next", new int[]{70, 248}, new int[]{222, 271}, "next") {
            public void isClicked() {
                System.out.println("The button was pressed, go to Example2");
                ExamplePage.this.push(new Example2Page());
            }
        };
    }

    @Override
    protected void viewDidLoad() { // OPTIONAL
        super.viewDidLoad();

        System.out.println("The example page was loaded.");
    }

    @Override
    protected Color backgroundColor() { // OPTIONAL
        return new Color(0x9E32C9);
    }
}
