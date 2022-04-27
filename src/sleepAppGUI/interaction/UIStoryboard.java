package sleepAppGUI.interaction;

import sleepAppGUI.visuals.Main;

import java.awt.*;

public class UIStoryboard implements UINavigator {

    private final Main main;
    protected Color backgroundColor = Color.white;

    public UIStoryboard(Main main) {
        this.main = main;
    }

    public UIStoryboard(Main main, Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.main = main;
    }

    public final void present(UIViewPage page) {
        if (page.navigator() == null) {
            page.setNavigator(this);
        } else {
            throw new RuntimeException("Presenting a page on a storyboard requires a new page to be created.");
        }

        Color backgroundColor;
        if (page.backgroundColor() != null) {
            backgroundColor = page.backgroundColor();
        } else {
            backgroundColor = this.backgroundColor;
        }

        Page newPage = new Page(page.pageNumber(), this.main, backgroundColor);
        page.main = this.main;
        page.setUp(newPage);
        this.main.setCurrentPage(newPage);
        page.viewDidLoad();
    }

    public final void back() {
        throw new IllegalStateException("Cannot call back on Storyboard navigation type.");
    }
}
