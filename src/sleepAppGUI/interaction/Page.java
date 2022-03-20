package sleepAppGUI.interaction;

import sleepAppGUI.visuals.*;

import java.awt.*;
import java.util.ArrayList;

//whole class might be redundant
//might be better to make class "Page" instead, page contains list of buttons and graphical
//information, page id. buttons can lead to pages
public class Page
{
    public Page(int number, Main m)
    {
        pageNumber = number;
        backgroundColour = new Color(0x555555);
        m.addPage(this);
    }

    public Page(int number, Main m, Color c)
    {
        pageNumber = number;
        backgroundColour = c;
        m.addPage(this);
    }

    private final int pageNumber;
    private final Color backgroundColour;
    private ArrayList<VObject> visualObjects = new ArrayList<>();
    private ArrayList<MyButton> buttons = new ArrayList<>();
    private ArrayList<MyTextField> textFields = new ArrayList<>();

    private void pushToFront(VObject o)
    {
        visualObjects.remove(o);
        visualObjects.add(o);
    }

    public void enterPage()
    {
        /* OLD
        for( MyTextField t : textFields)
        {
            t.setVisible(true);
        }
        //*/

        for( VObject obj : visualObjects)
        {
            obj.setVisible(obj.getDefVis());
        }
    }
    public void exitPage()
    {
        for(MyTextField t : textFields)
        {
            t.setVisible(false);
        }
    }

    public void addButton(MyButton b) { buttons.add(b); }
    public void addObject(VObject obj) { visualObjects.add(obj); }
    public void addTextField(MyTextField t) { textFields.add(t); }
    public Color getColour() { return backgroundColour; }


    public void checkButtons(int[] clickCoordinates)
    {
        for(MyButton button : buttons)
        {
            if(button.onButton(clickCoordinates))
            {
                button.isClicked();
                return;
            }
        }
    }

    public void paintObjects(Graphics g)
    {
        for(VObject obj : visualObjects)
        {
            if(obj.isVisible())
            {
                obj.paint(g);
            }
        }
    }



    public static void setUpPages(Main main)
    {
        Page page1 = new Page(0, main);
        Page page2 = new Page(1, main, new Color(0xff0000));

        MyText cardDetailsText = new MyText(page1, new int[] {350, 150}, new int[] {450, 175}, "Credit card details:");
        MyTextField cardDetailsInput = new MyTextField(main, page1, new int[] {300, 200}, new int[] { 500, 240});



        MyText virusConsentText = new MyText(page1, new int[] {100, 350}, new int[] {550, 400}, "consent to virus installation ");

        MyImage tick = new MyImage(page1, new int[] {610, 335}, new int[] {660, 385}, "Tick", false);

        MyButton tickBox = new MyButton(page1, "n" ,new int[] {600, 350}, new int[] {650, 400}, "Box")
        {
            public void isClicked()
            {
                tick.setVisible(!tick.isVisible());
            }
        };

        MyText virusInstalledText = new MyText(page2, new int[] {200, 100}, new int[] { 500, 150}, "VIRUS INSTALLED");

        MyButton downloadButton = new MyButton(page1, "name", new int[] {195, 400}, new int[] {605, 550}, "virus button")
        {
            public void isClicked()
            {
                if(tick.isVisible())
                {
                    System.out.println("user consents to virus installation");
                }
                else { System.out.println("user does not consent to virus installation");}

                virusInstalledText.setText(cardDetailsInput.getText());

                main.setCurrentPage(page2);
            }
        };

        //puts the tick on top of the tick box
        page1.pushToFront(tick);

        MyButton backButton = new MyButton(page2, "name", new int[] {0, 0}, new int[] {50, 50}, "back button")
        {
            public void isClicked()
            {
                main.setCurrentPage(page1);
                System.out.println("virus uninstalled");
            }
        };

         // MY CODE HERE
        // Create the pages here nice color: 0xC7EFF9
        Page username_password_initial = new Page(2, main, new Color(0xC7EFF9));
        Page more_info_page = new Page(3, main, new Color(0xC7EFF9));
        Page account_created = new Page(4, main, new Color(0xC7EFF9));

        // ADD THINGS TO FIRST PAGE
        // Adds logo
        MyImage logo = new MyImage(username_password_initial, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        // Adds the box to go behind input fields
        MyImage input_frame = new MyImage(username_password_initial, new int[] {275, 170}, new int[] {525, 400}, "box_behind", true);
        // Adds the username input box on first page
        MyText usernameText = new MyText(username_password_initial, new int[] {350, 200}, new int[] {365, 220}, "Username:");
        MyTextField usernameInput = new MyTextField(main, username_password_initial, new int[] {300, 210}, new int[] {500, 235});
        // Adds the password boxes
        MyText password = new MyText(username_password_initial, new int[] {350, 265}, new int[] {365, 285}, "Password:");
        MyTextField passwordInput1 = new MyTextField(main, username_password_initial, new int[] {300, 275}, new int[] {500, 300});
        MyText passwordReenter = new MyText(username_password_initial, new int[] {305, 330}, new int[] {320, 350}, "Re-enter Password:");
        MyTextField passwordInput2 = new MyTextField(main, username_password_initial, new int[] {300, 340}, new int[] {500, 365});


        // Add next button
        MyButton nextButton = new MyButton(username_password_initial, "next", new int[] {360, 400}, new int[] {440, 435}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(more_info_page);
                System.out.println("More Info Page");
            }
        };

        // ADD THINGS TO SECOND PAGE
        MyImage inputFrame = new MyImage(more_info_page, new int[] {150, 15}, new int[] {650, 560}, "box_behind", true);
        // height
        MyText height = new MyText(more_info_page, new int[] {275, 50}, new int[] {290, 70}, "Height:");
        MyTextField heightInput = new MyTextField(main, more_info_page, new int[] {282, 60}, new int[] {318, 85});
        MyText metres = new MyText(more_info_page, new int[] {320, 77}, new int[]{335, 92}, "m");
        // weight
        MyText weight = new MyText(more_info_page, new int[] {375, 50}, new int[] {390, 70}, "Weight:");
        MyTextField weightInput = new MyTextField(main, more_info_page, new int[] {382, 60}, new int[]{418, 85});
        MyText kilogram = new MyText(more_info_page, new int[] {420, 77}, new int[]{435, 92}, "kg");
        // age
        MyText age = new MyText(more_info_page, new int[] {475, 50}, new int[] {490, 70}, "Age:");
        MyTextField ageInput = new MyTextField(main, more_info_page, new int[] {482, 60}, new int[]{518, 85});
        // gender
        MyText gender = new MyText(more_info_page, new int[] {280, 130}, new int[] {295, 150}, "Gender: ");
        MyImage femaleClicked = new MyImage(more_info_page, new int[] {440, 100}, new int[] {495, 150}, "femaleClicked", false);
        MyImage maleClicked = new MyImage(more_info_page, new int[] {370, 100}, new int[] {425, 150}, "maleClicked", false);
        MyButton male = new MyButton(more_info_page, "male", new int[]{370, 100}, new int[]{425, 150}, "male") {
            public void isClicked() {
                femaleClicked.setVisible(false);
                maleClicked.setVisible(!maleClicked.isVisible());
            }
        };
        MyButton female = new MyButton(more_info_page, "female", new int[]{440, 100}, new int[]{495, 150}, "female") {
            public void isClicked() {
                // set male to invisible
                maleClicked.setVisible(false);
                femaleClicked.setVisible(!femaleClicked.isVisible());
            }
        };
        // factors
        MyText chooseFactors = new MyText(more_info_page, new int[]{179, 180}, new int[]{194, 200}, "Choose which factors you would like to track:");
        MyImage waterClicked = new MyImage(more_info_page, new int[] {200, 190}, new int[] {400, 300}, "waterClicked", false);
        MyButton water = new MyButton(more_info_page, "water", new int[]{200, 190}, new int[]{400, 300}, "water") {
            public void isClicked() {
                waterClicked.setVisible(!waterClicked.isVisible());
            }
        };
        MyImage exerciseClicked = new MyImage(more_info_page, new int[] {410, 190}, new int[] {610, 300}, "exerciseClicked", false);
        MyButton exercise = new MyButton(more_info_page, "exercise", new int[]{410, 190}, new int[]{610, 300}, "exercise") {
            public void isClicked() {
                exerciseClicked.setVisible(!exerciseClicked.isVisible());
            }
        };
        MyImage screenTimeClicked = new MyImage(more_info_page, new int[]{200, 305}, new int[] {400, 415}, "screentimeClicked", false);
        MyButton screenTime = new MyButton(more_info_page, "screenTime", new int[]{200, 305}, new int[]{400, 415}, "screentime") {
            public void isClicked() {
                screenTimeClicked.setVisible(!screenTimeClicked.isVisible());
            }
        };
        MyImage alcoholClicked = new MyImage(more_info_page, new int[]{410, 305}, new int[] {610, 415}, "alcoholClicked", false);
        MyButton alcohol = new MyButton(more_info_page, "alcohol", new int[]{410, 305}, new int[]{610, 415}, "alcohol") {
            public void isClicked() {
                alcoholClicked.setVisible(!alcoholClicked.isVisible());
            }
        };
        MyImage stressClicked = new MyImage(more_info_page, new int[]{200, 420}, new int[]{400, 530}, "stressClicked", false);
        MyButton stress = new MyButton(more_info_page, "stress", new int[]{200, 420}, new int[]{400, 530}, "stress") {
            public void isClicked() {
                stressClicked.setVisible(!stressClicked.isVisible());
            }
        };
        MyImage caffeineClicked = new MyImage(more_info_page, new int[]{410, 420}, new int[]{610, 530}, "caffeineClicked", false);
        MyButton caffeine = new MyButton(more_info_page, "caffeine", new int[]{410, 420}, new int[]{610, 530}, "caffeine") {
            public void isClicked() {
                caffeineClicked.setVisible(!caffeineClicked.isVisible());
            }
        };

        MyButton submit = new MyButton(more_info_page, "submit", new int[]{650, 510}, new int[]{750, 550}, "submitButton"){
            public void isClicked() {
                main.setCurrentPage(account_created);
                System.out.println("Account Created!");
            }
        };

        // push buttons to front
        more_info_page.pushToFront(maleClicked);
        more_info_page.pushToFront(femaleClicked);
        more_info_page.pushToFront(waterClicked);
        more_info_page.pushToFront(exerciseClicked);
        more_info_page.pushToFront(caffeineClicked);
        more_info_page.pushToFront(screenTimeClicked);
        more_info_page.pushToFront(stressClicked);
        more_info_page.pushToFront(alcoholClicked);

        main.setCurrentPage(username_password_initial);
    }
}
