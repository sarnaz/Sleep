package sleepAppGUI.pages;

import sleepAppDatabase.Database;
import sleepAppGUI.interaction.*;

import java.util.ArrayList;

public class MoreInfoPage extends UIViewPage {


    @Override
    protected int pageNumber() {
        return 3;
    }

    @Override
    protected void viewDidLoad() {

    }

    @Override
    protected void setUp(Page page) {
        // ADD THINGS TO SECOND PAGE
        new MyImage(page, new int[] {150, 15}, new int[] {650, 560}, "box_behind", true);

        // height
        new MyText(page, new int[] {275, 50}, new int[] {290, 70}, "Height:");
        MyTextField heightInput = new MyTextField(main, page, new int[] {282, 60}, new int[] {318, 85});
        new MyText(page, new int[] {320, 77}, new int[]{335, 92}, "cm");

        // weight
        new MyText(page, new int[] {375, 50}, new int[] {390, 70}, "Weight:");
        MyTextField weightInput = new MyTextField(main, page, new int[] {382, 60}, new int[]{418, 85});
        new MyText(page, new int[] {420, 77}, new int[]{435, 92}, "kg");

        // age
        new MyText(page, new int[] {475, 50}, new int[] {490, 70}, "Age:");
        MyTextField ageInput = new MyTextField(main, page, new int[] {482, 60}, new int[]{518, 85});

        // gender
        new MyText(page, new int[] {280, 130}, new int[] {295, 150}, "Gender: ");
        MyImage femaleClicked = new MyImage(page, new int[] {440, 100}, new int[] {495, 150}, "femaleClicked", false);
        MyImage maleClicked = new MyImage(page, new int[] {370, 100}, new int[] {425, 150}, "maleClicked", false);

        new MyButton(page, "male", new int[]{370, 100}, new int[]{425, 150}, "male") {
            public void isClicked() {
                femaleClicked.setVisible(false);
                maleClicked.setVisible(!maleClicked.isVisible());
            }
        };

        new MyButton(page, "female", new int[]{440, 100}, new int[]{495, 150}, "female") {
            public void isClicked() {
                // set male to invisible
                maleClicked.setVisible(false);
                femaleClicked.setVisible(!femaleClicked.isVisible());
            }
        };

        // factors
        new MyText(page, new int[]{179, 180}, new int[]{194, 200}, "Choose which factors you would like to track:");

        MyImage waterClicked = new MyImage(page, new int[] {200, 190}, new int[] {400, 300}, "waterClicked", false);
         new MyButton(page, "water", new int[]{200, 190}, new int[]{400, 300}, "water") {
            public void isClicked() {
                waterClicked.setVisible(!waterClicked.isVisible());
            }
        };

        MyImage exerciseClicked = new MyImage(page, new int[] {410, 190}, new int[] {610, 300}, "exerciseClicked", false);
        new MyButton(page, "exercise", new int[]{410, 190}, new int[]{610, 300}, "exercise") {
            public void isClicked() {
                exerciseClicked.setVisible(!exerciseClicked.isVisible());
            }
        };

        MyImage screenTimeClicked = new MyImage(page, new int[]{200, 305}, new int[] {400, 415}, "screentimeClicked", false);
        new MyButton(page, "screenTime", new int[]{200, 305}, new int[]{400, 415}, "screentime") {
            public void isClicked() {
                screenTimeClicked.setVisible(!screenTimeClicked.isVisible());
            }
        };

        MyImage alcoholClicked = new MyImage(page, new int[]{410, 305}, new int[] {610, 415}, "alcoholClicked", false);
        new MyButton(page, "alcohol", new int[]{410, 305}, new int[]{610, 415}, "alcohol") {
            public void isClicked() {
                alcoholClicked.setVisible(!alcoholClicked.isVisible());
            }
        };

        MyImage stressClicked = new MyImage(page, new int[]{200, 420}, new int[]{400, 530}, "stressClicked", false);
        new MyButton(page, "stress", new int[]{200, 420}, new int[]{400, 530}, "stress") {
            public void isClicked() {
                stressClicked.setVisible(!stressClicked.isVisible());
            }
        };

        MyImage caffeineClicked = new MyImage(page, new int[]{410, 420}, new int[]{610, 530}, "caffeineClicked", false);
        new MyButton(page, "caffeine", new int[]{410, 420}, new int[]{610, 530}, "caffeine") {
            public void isClicked() {
                caffeineClicked.setVisible(!caffeineClicked.isVisible());
            }
        };

        new MyButton(page, "submit", new int[]{650, 510}, new int[]{750, 550}, "submitButton"){
            public void isClicked() {
                Database.setUserHeight(Integer.parseInt(heightInput.getText()));
                Database.setUserWeight(Integer.parseInt(weightInput.getText()));
                Object[][] new_factors = Database.getFactorArray();
                ArrayList<String> factors_chosen = new ArrayList<>();
                if (caffeineClicked.isVisible()) {
                    factors_chosen.add("caffeine");
                }
                if (alcoholClicked.isVisible()) {
                    factors_chosen.add("alcohol");
                }
                if (exerciseClicked.isVisible()) {
                    factors_chosen.add("fitness");
                }
                if (stressClicked.isVisible()) {
                    factors_chosen.add("stress");
                }
                if (waterClicked.isVisible()) {
                    factors_chosen.add("water");
                }
                if (screenTimeClicked.isVisible()) {
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
                    MoreInfoPage.this.push(new HomePage());
                    System.out.println("Account Created!");
                }
            }
        };

        // push buttons to front
        page.pushToFront(maleClicked);
        page.pushToFront(femaleClicked);
        page.pushToFront(waterClicked);
        page.pushToFront(exerciseClicked);
        page.pushToFront(caffeineClicked);
        page.pushToFront(screenTimeClicked);
        page.pushToFront(stressClicked);
        page.pushToFront(alcoholClicked);
    }
}
