package sleepAppDatabase;

import java.sql.Date;

public class DBTester {

    public static void main(String[] args) {
        Database.validateUser("b", "a");
        Database.getDataForDate(2022, 4, 6);


    }
}
