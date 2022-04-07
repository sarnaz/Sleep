package sleepAppDatabase;

import java.sql.Date;

public class DBTester {

    public static void main(String[] args) {
        Database.validateUser("c", "a");
        Database.setFactors(new Object[][]{{"caffeine", "alcohol", "fitness", "stress", "water", "screenTime"},{true, true, false, true, false, false}});
        Database.setUserDateOfBirth(2022, 4, 6);
        Database.getDataForDate(2022, 4, 6);

    }
}
