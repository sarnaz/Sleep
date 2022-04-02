package sleepAppGUI.interaction;

import sleepAppGUI.visuals.Main;

import java.util.ArrayList;

public class UINavigationController implements UINavigator {

    private final Main main;
    private final ArrayList<Page> stack = new ArrayList<>();

    public UINavigationController(Main main) {
        this.main = main;
    }

    public final void present(UIViewPage page) {
        if (page.navigator() == null) {
            page.setNavigator(this);
        } else {
            throw new RuntimeException("Pushing a page on a navigation controller requires a new page to be created.");
        }

        Page newPage = new Page(page.pageNumber(), this.main, page.backgroundColor());
        page.main = this.main;
        page.setUp(newPage);
        this.main.setCurrentPage(newPage);
        page.viewDidLoad();

        stack.add(newPage);
    }

    public final void back() {
        stack.remove(stack.size() - 1);
        this.main.setCurrentPage(stack.get(stack.size() - 1));
    }
}
