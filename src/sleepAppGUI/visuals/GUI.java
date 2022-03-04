package sleepAppGUI.visuals;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class GUI
{
    private final Main main;
    private int width;
    private int height;
    //might be redundant
    private int mx = 0;
    private int my = 0;
    private JPanel canvas;

    /*
    MyButton button1 = new MyButton(page, "button1", new int[] {100, 100}, new int[] {500, 250}, "virus button")
    {
        public void isClicked()
        {
            System.out.println("isClicked override");
        }
    };
    */


    public GUI(int wwidth, int hheight, Main mmain)
    {
        main = mmain;
        width = wwidth;
        height = hheight;
        JFrame window = new JFrame("main window");

        canvas = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if(main.getCurrentPage() != null)
                {
                    main.getCurrentPage().paintObjects(g);
                }
                else
                {
                    System.out.println("page in main is uninitialised");
                }
            }
        };

        canvas.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mouseClicked(e);
                mx = e.getX();
                my = e.getY();
                System.out.println(mx+", "+my);
                main.getCurrentPage().checkButtons(new int[] {mx, my});
            }
        });

        window.add(canvas);
        window.setPreferredSize(new Dimension(width, height));
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

    public void repaintCanvas() { canvas.repaint(); }
}
