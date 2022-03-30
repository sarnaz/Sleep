package sleepAppGUI.interaction;

import sleepAppGUI.visuals.Main;

import javax.swing.*;

public class MyPasswordField extends MyTextField //should really extend JTextField
{
    private boolean textIsVisible;

    public MyPasswordField(Main m, Page page, int[] coordinate1, int[] coordinate2)
    {
        super(m, page, coordinate1, coordinate2);
        textField = new JPasswordField();
        textField.setVisible(false);
        textIsVisible = false;
        textField.setBounds(coordinate1[0], coordinate1[1], coordinate2[0] - coordinate1[0], coordinate2[1] - coordinate1[1]);
        page.addTextField(this);
        m.addTextField(textField);
    }
    
    public void setTextVisibility(boolean show)
    {
    	textIsVisible = show;
    	
    	// this is hacky, but it's the most efficient solution.
    	// dynamic type is JTextField, static type is JPasswordField
    	((JPasswordField)textField).setEchoChar((show) ? '\0' : '•');
    }
    
    public boolean getTextVisibility()
    {
    	return textIsVisible;
    }
}

