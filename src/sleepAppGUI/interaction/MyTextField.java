package sleepAppGUI.interaction;

import sleepAppGUI.visuals.Main;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends VObject //should really extend JTextField
{
    JTextField textField;

    public MyTextField(Main m, Page page, int[] coordinate1, int[] coordinate2)
    {
        super(page, coordinate1, coordinate2, true);
        textField = new JTextField();
        textField.setVisible(false);
        textField.setBounds(coordinate1[0], coordinate1[1], coordinate2[0] - coordinate1[0], coordinate2[1] - coordinate1[1]);
        page.addTextField(this);
        m.addTextField(textField);
    }

    public MyTextField(Main m, Page page, int[] coordinate1, int[] coordinate2, String fontName, int fontSize, int alignment) {
        super(page, coordinate1, coordinate2, true);
        textField = new JTextField();
        textField.setVisible(false);
        textField.setBounds(coordinate1[0], coordinate1[1], coordinate2[0] - coordinate1[0], coordinate2[1] - coordinate1[1]);

        Font font = new Font(fontName, Font.PLAIN, fontSize);
        textField.setFont(font);
        textField.setHorizontalAlignment(alignment);

        page.addTextField(this);
        m.addTextField(textField);
    }

    public void paint(Graphics g) { }

    public void setVisible(boolean vis)
    {
        super.setVisible(vis);
        textField.setVisible(isVisible());
        textField.setText("");
    }

    public String getText() { return textField.getText(); }
}

