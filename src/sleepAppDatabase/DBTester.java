package sleepAppDatabase;

import sleepAppProcessing.Fitness;

import java.sql.Date;
import java.util.Arrays;

public class DBTester {

    public static void main(String[] args) {
        Database.validateUser("b", "a");
        Database.addSleepEntry(5, 5, 1, 28, 4, 2022);
        Fitness f = new Fitness(0, 27, 4, 2022);


    }
}
