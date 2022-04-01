package sleepAppGUI.interaction;

import sleepAppGUI.interaction.graphs.MyBar;
import sleepAppGUI.interaction.graphs.MyScatter;
import sleepAppGUI.visuals.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import sleepAppDatabase.Database;

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
    private final ArrayList<VObject> visualObjects = new ArrayList<>();
    private final ArrayList<MyButton> buttons = new ArrayList<>();
    private final ArrayList<MySlider> sliders = new ArrayList<>();
    private final ArrayList<MyTextField> textFields = new ArrayList<>();

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
    public void addSlider(MySlider b) { sliders.add(b); }
    public void addObject(VObject obj) { visualObjects.add(obj); }
    public void addTextField(MyTextField t) { textFields.add(t); }
    public Color getColour() { return backgroundColour; }


    public void checkButtons(int[] clickCoordinates)
    {
        for (MyButton button : buttons)
        {
            if(button.onButton(clickCoordinates))
            {
                button.isClicked();
                return;
            }
        }

        for (MySlider slider : sliders)
        {
        	if (slider.onSlider(clickCoordinates))
        	{
            	slider.updatePosition(clickCoordinates);
        		slider.setSelected(true);
        	}
        }
    }

    public void checkSliderDrag(int[] dragCoordinates)
    {
        for(MySlider slider : sliders)
        {
            if(slider.onSlider(dragCoordinates))
            {
            	slider.updatePosition(dragCoordinates);
                return;
            }
        }
    }

    public void checkSliderRelease(int[] releaseCoordinates)
    {
        for(MySlider slider : sliders)
        {
            if(slider.onSlider(releaseCoordinates))
            {
            	slider.updatePosition(releaseCoordinates);
            	slider.setSelected(false);
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
        /* OLD CODE
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
        */

        // Create the pages here nice color: 0xC7EFF9
        Page sign_in_page = new Page(1, main, new Color(0xC7EFF9));
        Page username_password_initial = new Page(2, main, new Color(0x6D3FB2));
        Page more_info_page = new Page(3, main, new Color(0xC7EFF9));
        Page graph_visual = new Page(5, main, new Color(0xC7EFF9));
        Page home_page = new Page(6, main, new Color(0x6D3FB2));
        Page profile_page = new Page(7, main, new Color(0xC7EFF9));
        Page edit_profile_page = new Page(8, main, new Color(0xC7EFF9));
        Page sleep_questions = new Page(9, main, new Color(0xC7EFF9));
        Page water_questions = new Page(10, main, new Color(0xC7EFF9));
        Page alcohol_questions = new Page(11, main, new Color(0xC7EFF9));
        Page alcohol_yes = new Page(12, main, new Color(0xC7EFF9));
        Page alcohol_no = new Page(13, main, new Color(0xC7EFF9));
        Page stress_questions = new Page(14, main, new Color(0xC7EFF9));
        Page caffeine_questions = new Page(15, main, new Color(0xC7EFF9));
        Page caffeine_yes = new Page(16, main, new Color(0xC7EFF9));
        Page caffeine_no = new Page(17, main, new Color(0xC7EFF9));
        Page screen_questions = new Page(18, main, new Color(0xC7EFF9));
        Page exercise_questions = new Page(19, main, new Color(0xC7EFF9));
        Page exercise_yes = new Page(20, main, new Color(0xC7EFF9));
        Page exercise_no = new Page(21, main, new Color(0xC7EFF9));
        Page suggestions = new Page(22, main, new Color(0xC7EFF9));
        Page goal_page = new Page(23, main, new Color(0xC7EFF9));
        Page water_goal = new Page(24, main, new Color(0xC7EFF9));
        Page sleep_goal = new Page(25, main, new Color(0xC7EFF9));
        Page exercise_goal = new Page(26, main, new Color(0xC7EFF9));
        Page alcohol_goal = new Page(27, main, new Color(0xC7EFF9));
        Page screen_goal = new Page(28, main, new Color(0xC7EFF9));
        Page stress_goal = new Page(29, main, new Color(0xC7EFF9));
        Page caffeine_goal = new Page(30, main, new Color(0xC7EFF9));

        Page water_graph = new Page(10, main, new Color(0xC7EFF9));
        Page exercise_graph = new Page(11, main, new Color(0xC7EFF9));
        Page caffeine_graph = new Page(12, main, new Color(0xC7EFF9));
        Page alcohol_graph = new Page(13, main, new Color(0xC7EFF9));
        Page screen_graph = new Page(14, main, new Color(0xC7EFF9));
        Page stress_graph = new Page(15, main, new Color(0xC7EFF9));

        setUpDailySignIn(main, sign_in_page, home_page);
        setUpSignInPage(main, username_password_initial, more_info_page);
        setUpMoreInfoPage(main, more_info_page, home_page);
        setUpHomePage(main, home_page, profile_page, sleep_questions, graph_visual, goal_page);
        setUpProfilePage(main, profile_page, home_page, edit_profile_page, sign_in_page);
        setUpEditProfilePage(main, edit_profile_page, profile_page);
        setUpSleepQuestionsPage(main, sleep_questions, water_questions);
        setUpWaterQuestionsPage(main, water_questions, alcohol_questions);
        setUpAlcoholQuestionsGeneral(main, alcohol_questions, alcohol_yes, alcohol_no);
        setUpAlcoholYes(main, alcohol_yes, alcohol_no, caffeine_questions);
        setUpAlcoholNo(main, alcohol_no, alcohol_yes, caffeine_questions);
        setUpCaffeineQuestionsGeneral(main, caffeine_questions, caffeine_yes, caffeine_no);
        setUpCaffeineYes(main, caffeine_yes, caffeine_no, stress_questions);
        setUpCaffeineNo(main, caffeine_no, caffeine_yes, stress_questions);
        setUpStressQuestions(main, stress_questions, screen_questions);
        setUpScreenTimeQuestions(main, screen_questions, exercise_questions);
        setExerciseGeneral(main, exercise_questions, exercise_yes, exercise_no);
        setUpExerciseYes(main, exercise_yes, exercise_no, home_page);
        setUpExerciseNo(main, exercise_no, exercise_yes, home_page);
        setUpGraphPage(main,graph_visual, home_page, more_info_page, water_graph, exercise_graph, caffeine_graph, alcohol_graph, screen_graph, stress_graph);
        setUpGoalPage(main, goal_page, home_page, suggestions, water_goal, sleep_goal, exercise_goal, alcohol_goal, screen_goal, stress_goal, caffeine_goal);
        setUpEditWaterGoal(main, water_goal, goal_page);
        setUpEditSleepGoal(main, sleep_goal, goal_page);
        setUpEditExerciseGoal(main, exercise_goal, goal_page);
        setUpEditAlcoholGoal(main, alcohol_goal, goal_page);
        setUpEditScreenGoal(main, screen_goal, goal_page);
        setUpEditStressGoal(main, stress_goal, goal_page);
        setUpEditCaffeineGoal(main, caffeine_goal, goal_page);
        setupSuggestions(main, suggestions,  home_page);

        setUpWaterGraph(main,water_graph,graph_visual,home_page);
        setUpExerciseGraph(main,exercise_graph,graph_visual,home_page);
        setUpCaffeineGraph(main,caffeine_graph,graph_visual,home_page);
        setUpAlcoholGraph(main,alcohol_graph,graph_visual,home_page);
        setUpScreenGraph(main,screen_graph,graph_visual,home_page);
        setUpStressGraph(main,stress_graph,graph_visual,home_page);

        if (!Database.databaseExists()) {
        	Database.initialiseDatabase();
        }

        if (Database.checkForUsers()) {
            main.setCurrentPage(sign_in_page);
        } else {
            main.setCurrentPage(username_password_initial);
        }
    }


    private static void setUpDailySignIn(Main main, Page sign_in_page, Page nextPage){
        new MyImage(sign_in_page, new int[] {0, 0}, new int[] {800, 600}, "first-page", ".jpg", true);

        // Adds the username input box on first page
        MyText.putText(sign_in_page, new int[] {400, 230}, new int[] {70, 24}, "Username:", new int[] {0, 0, 1}, "Helvetica", Font.BOLD);
        MyTextField usernameInput = new MyTextField(main, sign_in_page, new int[] {400, 245}, new int[] {750, 275});

        // Adds the password boxes
        MyText.putText(sign_in_page, new int[] {400, 310}, new int[] {70, 24}, "Password:", new int[] {0, 0, 1}, "Helvetica", Font.BOLD);
        MyPasswordField passwordInput1 = new MyPasswordField(main, sign_in_page, new int[] {400, 325}, new int[] {750, 355});

        MyImage openEye = new MyImage(sign_in_page, new int[]{450, 250}, new int[]{470, 270}, "openEye", false);
        MyImage closedEye = new MyImage(sign_in_page, new int[]{450, 250}, new int[]{470, 270}, "closedEye", true);

        sign_in_page.pushToFront(openEye);
        sign_in_page.pushToFront(closedEye);

        new MyButton(sign_in_page, "show password", new int[] {450, 250}, new int[] {470, 270}, null)
        {
            public void isClicked()
            {
                System.out.println("clicked: " + ((passwordInput1.getTextVisibility()) ? "hiding" : "showing"));
                passwordInput1.setTextVisibility(!passwordInput1.getTextVisibility());
                openEye.setVisible(passwordInput1.getTextVisibility());
                closedEye.setVisible(!passwordInput1.getTextVisibility());
            }
        };

        // Add next button
        new MyButton(sign_in_page, "next", new int[] {680, 365}, new int[] {750, 415}, "next-button") {    public void isClicked() {
                // basic input validation
                if (Database.validateUser(usernameInput.getText(), passwordInput1.getText()) == 1){
                    main.setCurrentPage(nextPage);
                    System.out.println("More Info Page");
                } else {
                    System.out.println("Validate User Failed.");
                }
            }
        };
    }


    private static void setUpMoreInfoPage(Main main, Page more_info_page, Page nextPage) {
        // ADD THINGS TO SECOND PAGE
        MyImage inputFrame = new MyImage(more_info_page, new int[] {150, 15}, new int[] {650, 560}, "box_behind", true);
        // height
        MyText height = new MyText(more_info_page, new int[] {275, 50}, new int[] {290, 70}, "Height:");
        MyTextField heightInput = new MyTextField(main, more_info_page, new int[] {282, 60}, new int[] {318, 85});
        MyText metres = new MyText(more_info_page, new int[] {320, 77}, new int[]{335, 92}, "cm");
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
                Database.setUserHeight(Integer.parseInt(heightInput.getText()));
                Database.setUserWeight(Integer.parseInt(weightInput.getText()));
                Object[][] new_factors = Database.getFactorArray();
                ArrayList<String> factors_chosen = new ArrayList<String>();
                if(caffeineClicked.isVisible()==true) {
                    factors_chosen.add("caffeine");
                }
                if(alcoholClicked.isVisible()==true) {
                    factors_chosen.add("alcohol");
                }
                if(exerciseClicked.isVisible()==true) {
                    factors_chosen.add("fitness");
                }
                if(stressClicked.isVisible()==true) {
                    factors_chosen.add("stress");
                }
                if(waterClicked.isVisible()==true) {
                    factors_chosen.add("water");
                }
                if(screenTimeClicked.isVisible()==true) {
                    factors_chosen.add("screenTime");
                }
                if(factors_chosen.size() < 3){
                    System.out.println("Not enough factors");
                }
                else {
                    for (String each_factor : factors_chosen) {
                        System.out.println(factors_chosen);
                        for (int current = 0; current < new_factors[0].length; current++) {
                            System.out.println(new_factors[0][current]);
                            if (new_factors[0][current] == each_factor) {
                                new_factors[1][current] = 1;
                                System.out.println(each_factor);
                            }
                        }
                    }
                    Database.setFactors(new_factors);
                    main.setCurrentPage(nextPage);
                    System.out.println("Account Created!");
                }
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
    }

    private static void setUpSignInPage(Main main, Page username_password_initial, Page nextPage) {
        // ADD THINGS TO FIRST PAGE
        // Adds background
        new MyImage(username_password_initial, new int[] {0, 1}, new int[] {800, 600}, "first-page", ".jpg", true);

        new MyText(username_password_initial, new int[] {400, 190}, new int[] {440, 215}, "Username:", new int[] {0, 0, 1}, "Helvetica", Font.BOLD);
        MyTextField usernameInput = new MyTextField(main, username_password_initial, new int[] {400, 205}, new int[] {750, 235});

        // Adds the password boxes
        new MyText(username_password_initial, new int[] {400, 280}, new int[] {440, 305}, "Password:", new int[] {0, 0, 1}, "Helvetica", Font.BOLD);
        MyPasswordField passwordInput1 = new MyPasswordField(main, username_password_initial, new int[] {400, 295}, new int[] {750, 325});

        new MyText(username_password_initial, new int[] {400, 370}, new int[] {440, 395}, "Re-enter Password:", new int[] {0, 0, 1}, "Helvetica", Font.BOLD);
        MyPasswordField passwordInput2 = new MyPasswordField(main, username_password_initial, new int[] {400, 385}, new int[] {750, 415});

        MyImage openEye = new MyImage(username_password_initial, new int[]{540, 262}, new int[]{560, 282}, "openEye", false);
        MyImage closedEye = new MyImage(username_password_initial, new int[]{540, 262}, new int[]{560, 282}, "closedEye", true);

        new MyButton(username_password_initial, "show password", new int[]{540, 262}, new int[]{560, 282}, null)
        {
        	public void isClicked()
        	{
        		System.out.println("clicked: " + ((passwordInput1.getTextVisibility()) ? "hiding" : "showing"));
        		passwordInput1.setTextVisibility(!passwordInput1.getTextVisibility());
        		passwordInput2.setTextVisibility(!passwordInput2.getTextVisibility());
        		openEye.setVisible(passwordInput1.getTextVisibility());
        		closedEye.setVisible(!passwordInput1.getTextVisibility());
        	}
        };

        // Add next button
        new MyButton(username_password_initial, "next", new int[] {680, 420}, new int[] {750, 470}, "next-button")
        {
            public void isClicked()
            {
            	// basic input validation
            	// don't need to check for conflicting username because this is the only user
            	if (passwordInput1.getText().equals(passwordInput2.getText()) &&
            		!usernameInput.getText().equals("") &&
            		!passwordInput1.getText().equals("")
           			)
            	{
                    Database.addUser(usernameInput.getText(), passwordInput1.getText());
	                main.setCurrentPage(nextPage);
	                System.out.println("More Info Page");
            	}
            	else
            	{
            		System.out.println("password doesn't match or username not valid");
            	}
            }
        };

    }

    public static void setUpHomePage(Main main, Page home_page, Page profilePage, Page sleep_questions, Page graph_visual, Page goalPage) {
        MyImage inputFrame = new MyImage(home_page, new int[] {150, 130}, new int[] {650, 530}, "home_page_layout", true);

        // Add Logo
        MyImage.putImage(home_page, new int[] {275, 30}, 250, "logo");

        // show male icon for men and female icon for women
        MyImage maleIcon = new MyImage(home_page, new int[] {550, 157}, new int[] {610, 217}, "male_icon", true);
        MyImage femaleIcon = new MyImage(home_page, new int[] {550, 157}, new int[] {610, 217}, "female_icon", false);

        // display username
        MyText username = new MyText(home_page, new int[] {270, 178}, new int[] {320, 198}, "Username");

        // buttons
        MyButton dailyQuestions = new MyButton(home_page, "dailyQuestions", new int[]{170, 260}, new int[]{404, 361}, "questions"){
            public void isClicked()
            {
                main.setCurrentPage(sleep_questions);
                System.out.println("Sleep questions");
            }
        };
        MyButton goals = new MyButton(home_page, "goals", new int[]{170, 375}, new int[]{405, 515}, "goals"){
            public void isClicked()
            {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton activity = new MyButton(home_page, "activity", new int[]{420, 260}, new int[]{634, 405}, "activity"){
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("Graph Page");
            }
        };
        MyButton profile = new MyButton(home_page, "profile", new int[]{420, 418}, new int[]{634, 514}, "profile") {
            public void isClicked()
            {
                main.setCurrentPage(profilePage);
                System.out.println("Profile Page");
            }
        };
    }

    public static void setUpProfilePage(Main main, Page profile_page, Page previousPage, Page editPage, Page signinPage) {
        profilePageGeneralSetUp(profile_page);

        MyText height = new MyText(profile_page, new int[] {366, 298}, new int[] {388, 316}, "180 cm");
        MyText weight = new MyText(profile_page, new int[] {366, 366}, new int[] {388, 384}, "75 kg");

        // Add edit profile button
        MyButton editProfileButton = new MyButton(profile_page, "next", new int[] {400, 425}, new int[] {505, 450}, "edit_profile") {
            public void isClicked()
            {
                main.setCurrentPage(editPage);
                System.out.println("Edit Profile Page");
            }
        };
        MyButton backButton = new MyButton(profile_page, "back", new int[] {275, 480}, new int[] {365, 510}, "back_button") {
            public void isClicked()
            {
                main.setCurrentPage(previousPage);
                System.out.println("Main Menu Page");
            }
        };
        MyButton logoutButton = new MyButton(profile_page, "logout", new int[] {405, 480}, new int[] {525, 510}, "logout_button") {
            public void isClicked()
            {
                main.setCurrentPage(signinPage);
                System.out.println("Sign In Page");
            }
        };
    }

    public static void setUpEditProfilePage(Main main, Page edit_profile_page, Page nextPage) {
        profilePageGeneralSetUp(edit_profile_page);

        MyTextField heightInput = new MyTextField(main, edit_profile_page, new int[] {370, 280}, new int[] {398, 305});
        MyText cm = new MyText(edit_profile_page, new int[] {401, 298}, new int[] {406, 316}, "cm");
        MyTextField weightInput = new MyTextField(main, edit_profile_page, new int[] {370, 345}, new int[] {398, 370});
        MyText kg = new MyText(edit_profile_page, new int[] {401, 363}, new int[] {406, 381}, "kg");

        MyButton cancelButton = new MyButton(edit_profile_page, "cancel", new int[]{295, 425}, new int[]{380, 450}, "cancel_button") {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Profile Page");
            }
        };
        MyButton saveButton = new MyButton(edit_profile_page, "save", new int[]{435, 425}, new int[]{505, 450}, "save_button") {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Profile Page");
            }
        };
        MyButton deleteData = new MyButton (edit_profile_page, "delete data", new int[]{600, 475}, new int[]{750, 550}, "deleteData"){
            public void isClicked()
            {
                System.out.println("delete data (NOT IMPLEMENTED)");
            }
        };
    }

    private static void profilePageGeneralSetUp(Page page) {
        MyImage logo = new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);

        MyImage inputFrame = new MyImage(page, new int[] {275, 170}, new int[] {525, 470}, "profile_box", true);
        MyText username = new MyText(page, new int[] {305, 230}, new int[] {340, 250}, "Username");
    }

    private static void questionsGeneralSetUp(Page page){
        //logo
        MyImage logo = new MyImage(page, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        // behind frame
        MyImage inputFrame = new MyImage(page, new int[] {120, 170}, new int[] {680, 380}, "box_behind", true);
        // hide bed
        MyImage hideBed = new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "hideSquare", true);
    }

    public static void setUpSleepQuestionsPage(Main main, Page sleep_questions, Page nextPage){
        questionsGeneralSetUp(sleep_questions);
        MyImage bed = new MyImage(sleep_questions, new int[] {185, 15}, new int[] {615, 160}, "logo", true);
        MyText sleepHours = new MyText(sleep_questions, new int[] {170, 220}, new int[] {195, 240}, "How many hours of sleep did you get last night? ");
        MyText nearestHour = new MyText(sleep_questions, new int[] {290, 250}, new int[] {305, 270}, "(to the nearest hour)");
        MyTextField numberHours = new MyTextField(main, sleep_questions, new int[] {380, 255}, new int[] {420, 280});
        // quality of sleep - can we implement a slider?
        MyText qualitySleep = new MyText(sleep_questions, new int[] {220, 310}, new int[] {245, 330}, "Rate the quality of your sleep (1-10) ");
        MyTextField rateQuality = new MyTextField(main, sleep_questions, new int[] {380, 325}, new int[] {420, 350});

        // Add next button
        MyButton nextButton = new MyButton(sleep_questions, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Water questions");
            }
        };
    }

    public static void setUpWaterQuestionsPage(Main main, Page water_questions, Page nextPage){
        questionsGeneralSetUp(water_questions);
        MyImage waterLogo = new MyImage(water_questions, new int[] {185, 15}, new int[] {330, 160}, "waterLogo", true);
        // number cups water
        MyText numberCups = new MyText(water_questions, new int[] {195, 220}, new int[] {220, 240}, "How many cups of water have you had?");
        MyTextField numberCupsInp = new MyTextField(main, water_questions, new int[] {380, 240}, new int[] {420, 265});
        // number of cups immediately before bed
        MyText cupsTwoHours = new MyText(water_questions, new int[] {180, 310}, new int[] {205, 330}, "How many cups in the two hours before sleep?");
        MyTextField numTwoHours = new MyTextField(main, water_questions, new int[] {380, 325}, new int[] {420, 350});

        // Add next button
        MyButton nextButton = new MyButton(water_questions, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Stress questions");
            }
        };
    }
    public static void setUpAlcoholQuestionsGeneral(Main main, Page page, Page alcohol_yes, Page alcohol_no){
        questionsGeneralSetUp(page);
        MyImage alcoholLogo = new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "alcoholLogo", true);
        //MyImage yesClicked = new MyImage(page, new int []{325, 230}, new int[] {375, 265}, "yesButton", false);
        //MyImage noClicked = new MyImage(page, new int[] {425, 230}, new int[] {475, 265}, "noButton", false);
        // did you drink alcohol?
        MyText alcohol = new MyText(page, new int[] {245, 220}, new int[] {270, 240}, "Have you consumed alcohol?");
        MyButton yes = new MyButton(page, "yes", new int[] {325, 230}, new int[] {375, 265}, "yesUnclicked")
        {
            public void isClicked()
            {
                main.setCurrentPage(alcohol_yes);
                System.out.println("yes");

            }
        };
        MyButton no = new MyButton(page, "no", new int[] {425, 230}, new int[] {475, 265}, "noUnclicked")
        {
            public void isClicked()
            {
                main.setCurrentPage(alcohol_no);
                System.out.println("no");
            }
        };
    }

    public static void setUpAlcoholYes(Main main, Page alcohol_yes, Page alcohol_no, Page nextPage){
        setUpAlcoholQuestionsGeneral(main, alcohol_yes, alcohol_yes, alcohol_no);
        MyImage yesClicked = new MyImage(alcohol_yes, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        alcohol_yes.pushToFront(yesClicked);
        MyText numUnits = new MyText(alcohol_yes, new int[] {245, 290}, new int[] {270, 310}, "How many units have you had?");
        MyTextField howMany = new MyTextField(main, alcohol_yes, new int[] {380, 305}, new int[] {420, 330});
        MyButton nextButton = new MyButton(alcohol_yes, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Stress");
            }
        };
    }

    public static void setUpAlcoholNo(Main main, Page alcohol_no, Page alcohol_yes, Page nextPage){
        setUpAlcoholQuestionsGeneral(main, alcohol_no, alcohol_yes, alcohol_no);
        MyImage noClicked = new MyImage(alcohol_no, new int[] {425, 230}, new int[] {475, 265}, "noButton", true);
        alcohol_no.pushToFront(noClicked);
        MyButton nextButton = new MyButton(alcohol_no, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Stress");
            }
        };
    }

    public static void setUpStressQuestions(Main main, Page stress_questions, Page nextPage){
        questionsGeneralSetUp(stress_questions);
        MyImage stressLogo = new MyImage(stress_questions, new int[] {185, 15}, new int[] {330, 160}, "stressLogo", true);
        // daily stress
        MyText dailyStress = new MyText(stress_questions, new int[] {195, 260}, new int[] {220, 280}, "Rate your average stress level today (1-5):");
        MyTextField averageStress = new MyTextField(main, stress_questions, new int[] {380, 280}, new int[] {420, 305});
        // Add next button
        MyButton nextButton = new MyButton(stress_questions, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Back to home");
            }
        };
    }

    public static void setUpCaffeineQuestionsGeneral(Main main, Page page, Page caffeine_yes, Page caffeine_no){
        questionsGeneralSetUp(page);
        MyImage caffeineLogo = new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "coffeeLogo", true);
        // did you drink caffeine?
        MyText caffeine = new MyText(page, new int[] {245, 220}, new int[] {270, 240}, "Have you consumed caffeine?");
        MyButton yes = new MyButton(page, "yes", new int[] {325, 230}, new int[] {375, 265}, "yesUnclicked")
        {
            public void isClicked()
            {
                main.setCurrentPage(caffeine_yes);
                System.out.println("yes");

            }
        };
        MyButton no = new MyButton(page, "no", new int[] {425, 230}, new int[] {475, 265}, "noUnclicked")
        {
            public void isClicked()
            {
                main.setCurrentPage(caffeine_no);
                System.out.println("no");
            }
        };
    }

    public static void setUpCaffeineYes(Main main, Page caffeine_yes, Page caffeine_no, Page nextPage){
        setUpCaffeineQuestionsGeneral(main, caffeine_yes, caffeine_yes, caffeine_no);
        MyImage yesClicked = new MyImage(caffeine_yes, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        caffeine_yes.pushToFront(yesClicked);
        //MyText metres = new MyText(more_info_page, new int[] {320, 77}, new int[]{335, 92}, "m")
        MyText numEach = new MyText(caffeine_yes, new int[] {180, 290}, new int[] {205, 310}, "Please enter how many of each you have had:");
        // coffee questions
        MyText coffeeNum = new MyText(caffeine_yes, new int[] {200, 323}, new int[] {215, 343}, "Coffee:");
        MyTextField coffeeInput = new MyTextField(main, caffeine_yes, new int[] {282, 305}, new int[]{318, 330});
        // tea questions
        MyText teaNum = new MyText(caffeine_yes, new int[] {340, 323}, new int[] {355, 343}, "Tea:");
        MyTextField teaInput = new MyTextField(main, caffeine_yes, new int[] {392, 305}, new int[]{428, 330});
        // energy drinks
        MyText energyNum = new MyText(caffeine_yes, new int[] {448, 323}, new int[] {473, 343}, "Energy drinks:");
        MyTextField energyInput = new MyTextField(main, caffeine_yes, new int[] {590, 305}, new int[]{626, 330});
        // next button
        MyButton nextButton = new MyButton(caffeine_yes, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Stress");
            }
        };
    }

    public static void setUpCaffeineNo(Main main, Page caffeine_no, Page caffeine_yes, Page nextPage){
        setUpCaffeineQuestionsGeneral(main, caffeine_no, caffeine_yes, caffeine_no);
        MyImage noClicked = new MyImage(caffeine_no, new int[] {425, 230}, new int[] {475, 265}, "noButton", true);
        caffeine_no.pushToFront(noClicked);
        MyButton nextButton = new MyButton(caffeine_no, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Home");
            }
        };
    }

    private static void setUpScreenTimeQuestions(Main main, Page screen_questions, Page nextPage){
        questionsGeneralSetUp(screen_questions);
        MyImage screenLogo = new MyImage(screen_questions, new int[] {230, 15}, new int[] {330, 160}, "phoneLogo", true);
        // amount of screen time
        MyText screenTime = new MyText(screen_questions, new int[] {190, 260}, new int[] {215, 280}, "How much time have you spent on a screen?");
        MyText nearestHour = new MyText(screen_questions, new int[] {290, 290}, new int[] {305, 310}, "(to the nearest hour)");
        MyTextField screenHours = new MyTextField(main, screen_questions, new int[] {380, 300}, new int[] {420, 325});
        MyButton nextButton = new MyButton(screen_questions, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Home");
            }
        };
    }

    public static void setExerciseGeneral(Main main, Page page, Page exercise_yes, Page exercise_no){
        questionsGeneralSetUp(page);
        MyImage exLogo = new MyImage(page, new int[] {185, 15}, new int[] {330, 160}, "exerciseLogo", true);
        // Have you exercised?
        MyText caffeine = new MyText(page, new int[] {300, 220}, new int[] {325, 240}, "Have you exercised?");
        MyButton yes = new MyButton(page, "yes", new int[] {325, 230}, new int[] {375, 265}, "yesUnclicked")
        {
            public void isClicked()
            {
                main.setCurrentPage(exercise_yes);
                System.out.println("yes");

            }
        };
        MyButton no = new MyButton(page, "no", new int[] {425, 230}, new int[] {475, 265}, "noUnclicked")
        {
            public void isClicked()
            {
                main.setCurrentPage(exercise_no);
                System.out.println("no");
            }
        };
    }

    public static void setUpExerciseYes(Main main, Page exercise_yes, Page exercise_no, Page nextPage){
        setExerciseGeneral(main, exercise_yes, exercise_yes, exercise_no);
        MyImage yesClicked = new MyImage(exercise_yes, new int []{325, 230}, new int[] {375, 265}, "yesButton", true);
        exercise_yes.pushToFront(yesClicked);
        MyText amountEx = new MyText(exercise_yes, new int[] {200, 290}, new int[] {225, 310}, "Please enter how much you have done:");
        MyText nearestHour = new MyText(exercise_yes, new int[]{290, 320}, new int[]{305, 340}, "(to the nearest hour)");
        MyTextField exerciseHours = new MyTextField(main, exercise_yes, new int[]{380, 330}, new int[]{420, 355});
        // next button
        MyButton nextButton = new MyButton(exercise_yes, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Home");
            }
        };
    }

    public static void setUpExerciseNo(Main main, Page exercise_no, Page exercise_yes, Page nextPage){
        setExerciseGeneral(main, exercise_no, exercise_yes, exercise_no);
        MyImage noClicked = new MyImage(exercise_no, new int[] {425, 230}, new int[] {475, 265}, "noButton", true);
        exercise_no.pushToFront(noClicked);
        MyButton nextButton = new MyButton(exercise_no, "next", new int[] {360, 400}, new int[] {440, 445}, "next")
        {
            public void isClicked()
            {
                main.setCurrentPage(nextPage);
                System.out.println("Home");
            }
        };
    }

    private static void setUpGraphPage(Main main, Page graph_visual, Page home_page, Page more_info_page, Page water_graph, Page exercise_graph, Page caffeine_graph, Page alcohol_graph, Page screen_graph, Page stress_graph) {
        MyImage water_frame = new MyImage(graph_visual, new int[] {10, 10}, new int[] {260, 265}, "box_behind", true);
        MyImage water = new MyImage(graph_visual, new int[] {60, 220}, new int[] {100, 260}, "waternotext", true);
        MyButton waterbutton = new MyButton(graph_visual, "water", new int[] {110, 220}, new int[] {220, 255}, "moreinfo")
        {
            public void isClicked()
            {
                main.setCurrentPage(water_graph);
                System.out.println("water graph");
            }
        };
        MyImage exercise_frame = new MyImage(graph_visual, new int[] {270, 10}, new int[] {520, 265}, "box_behind", true);
        MyImage exercise = new MyImage(graph_visual, new int[] {320, 220}, new int[] {360, 260}, "exercisenotext", true);
        MyButton exercisebutton = new MyButton(graph_visual, "exercise", new int[] {370, 220}, new int[] {480, 255}, "moreinfo")
        {
            public void isClicked()
            {
                main.setCurrentPage(exercise_graph);
                System.out.println("exercise graph");
            }
        };
        MyImage screentime_frame = new MyImage(graph_visual, new int[] {530, 10}, new int[] {780, 265}, "box_behind", true);
        MyImage screentime = new MyImage(graph_visual, new int[] {580, 220}, new int[] {620, 260}, "screentimenotext", true);
        MyButton screenbutton = new MyButton(graph_visual, "screen", new int[] {630, 220}, new int[] {740, 255}, "moreinfo")
        {
            public void isClicked()
            {
                main.setCurrentPage(screen_graph);
                System.out.println("screen graph");
            }
        };
        MyImage alcohol_frame = new MyImage(graph_visual, new int[] {10, 270}, new int[] {260, 530}, "box_behind", true);
        MyImage alcohol = new MyImage(graph_visual, new int[] {60, 485}, new int[] {100, 525}, "alcoholnotext", true);
        MyButton alcoholbutton = new MyButton(graph_visual, "alcohol", new int[] {110, 485}, new int[] {220, 520}, "moreinfo")
        {
            public void isClicked()
            {
                main.setCurrentPage(alcohol_graph);
                System.out.println("alcohol graph");
            }
        };
        MyImage caffeine_frame = new MyImage(graph_visual, new int[] {270, 270}, new int[] {520, 530}, "box_behind", true);
        MyImage caffeine = new MyImage(graph_visual, new int[] {320, 485}, new int[] {360, 525}, "caffeinenotext", true);
        MyButton caffeinebutton = new MyButton(graph_visual, "caffeine", new int[] {370, 485}, new int[] {480, 520}, "moreinfo")
        {
            public void isClicked()
            {
                main.setCurrentPage(caffeine_graph);
                System.out.println("caffeine graph");
            }
        };
        MyImage stress_frame = new MyImage(graph_visual, new int[] {530, 270}, new int[] {780, 530}, "box_behind", true);
        MyImage stress = new MyImage(graph_visual, new int[] {580, 485}, new int[] {620, 525}, "stressnotext", true);
        MyButton stessbutton = new MyButton(graph_visual, "stress", new int[] {630, 485}, new int[] {740, 520}, "moreinfo")
        {
            public void isClicked()
            {
                main.setCurrentPage(stress_graph);
                System.out.println("stress graph");
            }
        };
        MyButton homebutton = new MyButton(graph_visual, "home", new int[] {245, 240}, new int[] {290, 280}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton settingsbutton = new MyButton(graph_visual, "setting", new int[] {505, 240}, new int[] {550, 285}, "setting")
        {
            public void isClicked()
            {
                main.setCurrentPage(more_info_page);
                System.out.println("moreinfo");
            }
        };
    }

    private static void setUpWaterGraph(Main main,Page water_graph, Page graph_visual, Page home_page){
        MyImage water_frame = new MyImage(water_graph, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(water_graph, new int[] {300, 10}, new int[] {500, 100}, "water", true);
        MyButton homebutton = new MyButton(water_graph, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(water_graph, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("graph");
            }
        };
    }

    private static void setUpExerciseGraph(Main main,Page exercise_graph, Page graph_visual, Page home_page){
        MyImage water_frame = new MyImage(exercise_graph, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(exercise_graph, new int[] {300, 10}, new int[] {500, 100}, "exercise", true);
        MyButton homebutton = new MyButton(exercise_graph, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(exercise_graph, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("graph");
            }
        };
    }

    private static void setUpCaffeineGraph(Main main,Page caffeine_graph, Page graph_visual, Page home_page){
        MyImage water_frame = new MyImage(caffeine_graph, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(caffeine_graph, new int[] {300, 10}, new int[] {500, 100}, "caffeine", true);
        MyButton homebutton = new MyButton(caffeine_graph, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(caffeine_graph, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("graph");
            }
        };
    }

    private static void setUpAlcoholGraph(Main main,Page alcohol_graph, Page graph_visual, Page home_page){
        MyImage water_frame = new MyImage(alcohol_graph, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(alcohol_graph, new int[] {300, 10}, new int[] {500, 100}, "alcohol", true);
        MyButton homebutton = new MyButton(alcohol_graph, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(alcohol_graph, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("graph");
            }
        };
    }

    private static void setUpScreenGraph(Main main,Page screen_graph, Page graph_visual, Page home_page){
        MyImage water_frame = new MyImage(screen_graph, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(screen_graph, new int[] {300, 10}, new int[] {500, 100}, "screentime", true);
        MyButton homebutton = new MyButton(screen_graph, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(screen_graph, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("graph");
            }
        };
    }

    private static void setUpStressGraph(Main main,Page stress_graph, Page graph_visual, Page home_page){
        MyImage water_frame = new MyImage(stress_graph, new int[] {20, 100}, new int[] {760, 500}, "box_behind", true);
        MyImage water = new MyImage(stress_graph, new int[] {300, 10}, new int[] {500, 100}, "stress", true);
        MyButton homebutton = new MyButton(stress_graph, "home", new int[] {565, 30}, new int[] {635, 100}, "home")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
                System.out.println("home");
            }
        };
        MyButton backbutton = new MyButton(stress_graph, "back", new int[] {150, 30}, new int[] {220, 100}, "back")
        {
            public void isClicked()
            {
                main.setCurrentPage(graph_visual);
                System.out.println("graph");
            }
        };
    }

    public static void setUpGoalPage(Main main, Page goal_page, Page previousPage, Page suggestionsPage, Page waterPage, Page sleepPage, Page exercisePage, Page alcoholPage, Page screenPage, Page stressPage, Page caffeinePage) {
        MyImage layout = new MyImage(goal_page, new int[] {0, 20}, new int[] {800, 476}, "goal_layout", true);

        // example of how to display goal
        MyText goal_example = new MyText(goal_page, new int[] {110, 200}, new int[] {320, 230}, "5 / 10");

        // buttons to edit goals
        MyButton EditWaterGoal = new MyButton(goal_page, "editWater", new int[]{70, 248}, new int[]{222, 271}, "edit_goal_1"){
            public void isClicked()
            {
                main.setCurrentPage(waterPage);
                System.out.println("Edit Water Goal Page");
            }
        };
        MyButton EditSleepGoal = new MyButton(goal_page, "editSleep", new int[]{247, 248}, new int[]{399, 271}, "edit_goal_1"){
            public void isClicked()
            {
                main.setCurrentPage(sleepPage);
                System.out.println("Edit Sleep Goal Page");
            }
        };
        MyButton EditExerciseGoal = new MyButton(goal_page, "editExercise", new int[]{70, 427}, new int[]{222, 450}, "edit_goal_1"){
            public void isClicked()
            {
                main.setCurrentPage(exercisePage);
                System.out.println("Edit Exercise Goal Page");
            }
        };
        MyButton EditAlcoholGoal = new MyButton(goal_page, "editAlcohol", new int[]{247, 427}, new int[]{399, 450}, "edit_goal_1"){
            public void isClicked()
            {
                main.setCurrentPage(alcoholPage);
                System.out.println("Edit Alcohol Goal Page");
            }
        };
        MyButton EditScreenTimeGoal = new MyButton(goal_page, "editScreenTime", new int[]{709, 113}, new int[]{731, 196}, "edit_goal_2"){
            public void isClicked()
            {
                main.setCurrentPage(screenPage);
                System.out.println("Edit Screen Time Goal Page");
            }
        };
        MyButton EditStressGoal = new MyButton(goal_page, "editStress", new int[]{709, 220}, new int[]{731, 321}, "edit_goal_2"){
            public void isClicked()
            {
                main.setCurrentPage(stressPage);
                System.out.println("Edit Stress Goal Page");
            }
        };
        MyButton EditCaffeineGoal = new MyButton(goal_page, "editCaffeine", new int[]{709, 345}, new int[]{731, 450}, "edit_goal_2"){
            public void isClicked()
            {
                main.setCurrentPage(caffeinePage);
                System.out.println("Edit Caffeine Goal Page");
            }
        };
        MyButton backButton = new MyButton(goal_page, "back", new int[] {100, 490}, new int[] {190, 520}, "back_button") {
            public void isClicked()
            {
                main.setCurrentPage(previousPage);
                System.out.println("Home Page");
            }
        };
        MyButton suggestionsButton = new MyButton(goal_page, "suggestions", new int[] {520, 490}, new int[] {662, 520}, "suggestions_button") {
            public void isClicked()
            {
                main.setCurrentPage(suggestionsPage);
                System.out.println("Suggestions Page");
            }
        };
    }

    public static void setUpEditWaterGoal(Main main, Page water_goal, Page goalPage) {
        MyImage layout = new MyImage(water_goal, new int[]{0, 10}, new int[]{800, 519}, "water_goal", true);
        MyTextField input = new MyTextField(main, water_goal, new int[]{375, 170}, new int[]{425, 210});

        MyButton backButton = new MyButton(water_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(water_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    public static void setUpEditSleepGoal(Main main, Page sleep_goal, Page goalPage) {
        MyImage layout = new MyImage(sleep_goal, new int[]{0, 10}, new int[]{800, 519}, "sleep_goal", true);
        MyTextField input = new MyTextField(main, sleep_goal, new int[]{375, 170}, new int[]{425, 210});

        MyButton backButton = new MyButton(sleep_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(sleep_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    public static void setUpEditExerciseGoal(Main main, Page exercise_goal, Page goalPage) {
        MyImage layout = new MyImage(exercise_goal, new int[]{0, 10}, new int[]{800, 519}, "exercise_goal", true);
        MyTextField input = new MyTextField(main, exercise_goal, new int[]{375, 170}, new int[]{425, 210});

        MyButton backButton = new MyButton(exercise_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(exercise_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    public static void setUpEditAlcoholGoal(Main main, Page alcohol_goal, Page goalPage) {
        MyImage layout = new MyImage(alcohol_goal, new int[]{0, 10}, new int[]{800, 519}, "alcohol_goal", true);
        MyTextField input = new MyTextField(main, alcohol_goal, new int[]{375, 170}, new int[]{425, 210});

        MyButton backButton = new MyButton(alcohol_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(alcohol_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    public static void setUpEditScreenGoal(Main main, Page screen_goal, Page goalPage) {
        MyImage layout = new MyImage(screen_goal, new int[]{0, 10}, new int[]{800, 519}, "screen_goal", true);
        MyTextField input = new MyTextField(main, screen_goal, new int[]{375, 170}, new int[]{425, 210});

        MyButton backButton = new MyButton(screen_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(screen_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    public static void setUpEditStressGoal(Main main, Page stress_goal, Page goalPage) {
        MyImage layout = new MyImage(stress_goal, new int[]{0, 10}, new int[]{800, 519}, "stress_goal", true);
        MyTextField input = new MyTextField(main, stress_goal, new int[]{475, 170}, new int[]{525, 210});

        MyButton backButton = new MyButton(stress_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(stress_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    public static void setUpEditCaffeineGoal(Main main, Page caffeine_goal, Page goalPage) {
        MyImage layout = new MyImage(caffeine_goal, new int[]{0, 10}, new int[]{800, 519}, "caffeine_goal", true);
        MyTextField coffeeInput = new MyTextField(main, caffeine_goal, new int[]{265, 170}, new int[]{315, 210});
        MyTextField teaInput = new MyTextField(main, caffeine_goal, new int[]{385, 170}, new int[]{435, 210});
        MyTextField energyDrinkInput = new MyTextField(main, caffeine_goal, new int[]{645, 170}, new int[]{695, 210});

        MyButton backButton = new MyButton(caffeine_goal, "back", new int[]{240, 525}, new int[]{330, 555}, "back_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Goal Page");
            }
        };
        MyButton saveButton = new MyButton(caffeine_goal, "save", new int[]{470, 525}, new int[]{540, 555}, "save_button") {
            public void isClicked() {
                main.setCurrentPage(goalPage);
                System.out.println("Saved");
            }
        };
    }

    private static void setupSuggestions(Main main, Page suggestions, Page home_page) {
        MyText title = new MyText(suggestions, new int[] {270, 110}, new int[] {180, 160}, "Suggestions");
        //MyText WaterSuggestion = new MyText(suggestions, new int[] {350, 200}, new int[] {365, 220}, "You should drink 6 cups of water per day on average.");
        //I need a suggestion for each person from the factor they researched on and I need data from the back end to suggest how to improve(eg. if 4 cups of water then drink 1 more
        MyButton backButton = new MyButton(suggestions, "back", new int[] {360, 400}, new int[] {440, 435}, "back_button")
        {
            public void isClicked()
            {
                main.setCurrentPage(home_page);
            }
        };
    }
}
