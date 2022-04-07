package sleepAppGUI.interaction;
import sleepAppGUI.visuals.Main;

import java.awt.*;

abstract public class UIViewPage {

    protected Main main;
    private UINavigator navigator;
    private Color defaultBackgroundColor = new Color(0xFFFFFF);

    public UIViewPage() {}

    public UIViewPage(Color defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public void setNavigator(UINavigator navigator) {
        if (this.navigator == null) {
            this.navigator = navigator;
        } else {
            throw new RuntimeException("Storyboard for UIViewPage can only be set once.");
        }
    }

    public UINavigator navigator() {
        return this.navigator;
    }

    abstract protected int pageNumber();

    protected void viewDidLoad() {}

    abstract protected void setUp(Page page);

    protected final void push(UIViewPage page) {
        if (this.navigator != null) {
            this.navigator.present(page);
        }
    }

    protected final void pop() {
        this.navigator.back();
    }

    protected Color backgroundColor() {
        return defaultBackgroundColor;
    }

}
