package sleepAppGUI.pages.graphs;

import sleepAppGUI.interaction.MyButton;
import sleepAppGUI.interaction.MyImage;
import sleepAppGUI.interaction.Page;
import sleepAppGUI.interaction.UIViewPage;
import sleepAppGUI.pages.GraphVisual;
import sleepAppGUI.pages.HomePage;

abstract public class GraphPage extends UIViewPage {

    @Override
    protected void setUp(Page page) {
        new MyButton(page, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                GraphPage.this.push(new HomePage());
                System.out.println("home");
            }
        };

        new MyButton(page, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                GraphPage.this.push(new GraphVisual());
                System.out.println("graph");
            }
        };
    }
}
