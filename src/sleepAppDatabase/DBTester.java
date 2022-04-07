package sleepAppDatabase;

import java.sql.Date;

public class DBTester {

    public static void main(String[] args) {

        Database.initialiseDatabase();
        Database.addUser("a","a");


    }
}
