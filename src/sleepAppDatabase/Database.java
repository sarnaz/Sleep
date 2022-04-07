package sleepAppDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
import java.io.*;

public class Database {

    private static final int secondsInDay = (int) ((int) 8.64 * Math.pow(10, 4));
    //assumes this is the user's first login unless proven otherwise
    public static final String databaseURL = "jdbc:sqlite:PI.db";
    //the database url. final as it should never be changed
    private static int id;
    private static Object[][] factors = {{"caffeine", "alcohol", "fitness", "stress", "water", "screenTime"},{false, false, false, false, false, false}};

    //returns the currently set user id
    public static int getCurrentUserId()
    {
        return id;
    }
    //returns the factor array in it's current state
    public static Object[][] getFactorArray(){
        return factors;
    }


    //returns true if the daily questions haven't yet been asked
    //does not increment the time. That is done once the questions have been answered
    //returns false if the daily questions have already been asked that day
    public static boolean askDailyQuestionsCheck(){
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT lastQuestionTime FROM USER";
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()) {
                    if((System.currentTimeMillis()/1000) - rs.getInt("lastQuestionTime") > secondsInDay){
                        return true;
                    }
                } else {
                    System.out.println("no previous time found");
                }
            }
            assert conn != null;
            conn.close();
        } catch (SQLException e) {
            System.out.println("exception was caught initialising database");
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    //gets a list of the factors in use, with boolean variables attached to each indicating whether they are being used
    public static Object[][] getFactors(){

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM FACTORS where id="+id;
                ResultSet rs = stmt.executeQuery(sql);

                if(rs.next()){
                    for(int i = 0; i<factors[0].length;i++){
                        if(rs.getInt(String.valueOf(factors[0][i]))==1){
                            factors[1][i]=true;
                            //sets the factors currently in use to true
                        }
                    }
                    return factors;
                }
                else{
                    System.out.println("no factors chosen");
                }
                conn.close();
            }

        } catch (SQLException e) {
            System.out.println("exception was caught getting factors");
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }



    //updates the factors using the factors and corresponding boolean values in the 2d array above
    public static boolean setFactors(Object[][] factors){
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                StringBuilder sql = new StringBuilder("UPDATE FACTORS SET ");
                for(int i = 0; i<factors[0].length;i++){
                    sql.append(factors[0][i]).append("=").append(factors[1][i]);
                    if(i!=factors[0].length-1){
                        sql.append(", ");
                    }
                }
                sql.append(" where id=");
                sql.append(id);
                stmt.executeUpdate(sql.toString());
                conn.close();
            } else {
                return false;
            }

            Database.factors = factors;


        } catch (SQLException e) {
            System.out.println("exception was caught getting factors");
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        return true;
    }
    public static int getStressData(int year, int month, int day){

        Date addDate = Date.valueOf(year + "-" + month + "-" + day);

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();

            String sql = "SELECT stressLevel FROM STRESS WHERE id="+id+" and addDate="+addDate.getTime();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("stressLevel");
            }

            conn.close();

        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return -1;
    }

    public static Object[][] getDataForDate(int year, int month, int day){

        Object[][] values = new Object[][]{{"units", "caffeine", "cupsOfWater", "sleepTime", "timeToSleep", "sleepQuality", "stressLevel", "screenTime", "exerciseTime"}
                ,{null, null, null, null, null, null, null, null, null}};

        try{
            /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date d = sdf.parse(day+"-"+month+"-"+year);
            long time = d.getTime();

             */


            Date addDate = Date.valueOf(year + "-" + month + "-" + day);

            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();

            String sql = "SELECT units, caffeine, cupsOfWater FROM FLUID WHERE addDate="+ addDate.getTime() + " and id="+id;
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println(sql);
            if(rs.next()){
                values[1][0] = rs.getInt((String)values[0][0]);
                values[1][1] = rs.getInt((String)values[0][1]);
                values[1][2] = rs.getInt((String)values[0][2]);
            }

            sql = "SELECT sleepTime, timeToSleep, sleepQuality FROM SLEEP where addDate="+addDate.getTime() +" and id="+id;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                values[1][3] = rs.getInt((String)values[0][3]);
                values[1][4] = rs.getInt((String)values[0][4]);
                values[1][5] = rs.getInt((String)values[0][5]);
            }
            sql = "SELECT stressLevel FROM STRESS where addDate="+addDate.getTime() +" and id="+id;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                System.out.println("B");
                values[1][6] = rs.getInt((String)values[0][6]);
            }
            sql = "SELECT screenTime FROM SCREENTIME where addDate="+addDate.getTime() +" and id="+id;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                System.out.println("C");
                values[1][7] = rs.getInt((String)values[0][7]);
            }
            sql = "SELECT exerciseTime FROM FITNESS where addDate="+addDate.getTime() +" and id="+id;
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                System.out.println("D");
                values[1][8] = rs.getInt((String)values[0][8]);
            }

            conn.close();

        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        return values;
    }

    //sets the last day that questions were answered to the end of the current day
    public static void setQuestionsAnswered(){
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM USER";
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.getInt("lastQuestionDay") - (System.currentTimeMillis() / 1000) > secondsInDay) {

                    int newTime = rs.getInt("lastQuestionDay");

                    while (newTime < (System.currentTimeMillis() / 1000)) {
                        newTime += secondsInDay;
                        //adds millis in the day until newTime is accurate to the current day
                    }

                    String updateDate = "UPDATE USER SET lastQuestionTime=" + newTime + " WHERE id="+id;
                    stmt.executeUpdate(updateDate);
                    //should update the date in the database so

                }

                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("exception caught in setQuestionsAnswered");
            System.out.println(e.getLocalizedMessage());
        }
    }

    public static boolean addScreenTimeEntry(double screentime, int day, int month, int year){
        Date addDate = Date.valueOf(year + "-" + month + "-" + day);
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                String sql = "INSERT INTO SCREENTIME (id, screenTime, addDate) VALUES("+id+","
                        +screentime+","+addDate.getTime()+")";

                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                conn.close();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println("exception caught when adding screen time entry ");
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }


    //sets a given user variable in a given column of the User database
    public static boolean addSleepEntry(int sleepTime, int timeToSleep, int sleepQuality, int day, int month, int year){
        Date addDate = Date.valueOf(year + "-" + month + "-" + day);
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                String sql = "INSERT INTO SLEEP (id, sleepTime, timeToSleep, sleepQuality, addDate) VALUES("+id+","
                        +sleepTime+","+timeToSleep + ", " + sleepQuality+ ", "+addDate.getTime()+")";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                checkStreak(sleepTime);
                //calls the checkStreak function to validate those details

                conn.close();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println("exception caught when adding sleep entry");
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }


    //function to check if the streak needs to be incremented or reset. called from addSleepEntry
    private static void checkStreak(int sleepTime){
        double goalTime = 8;
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();

            String sql = "SELECT sleepTarget FROM GOALS WHERE id="+id;

            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                goalTime = rs.getDouble("sleepTarget");
            }
            else{
                throw new Exception("no sleep goal found");
            }

            if(sleepTime<goalTime) {
                sql = "UPDATE GOALS SET sleepStreak=0 where id=" + id;
                stmt.executeUpdate(sql);
            }
            else{
                int currentStreak = 0;
                sql = "SELECT sleepStreak FROM GOALS WHERE id="+id;
                rs = stmt.executeQuery(sql);
                if(rs.next()){
                    currentStreak = rs.getInt("sleepStreak");
                }
                else{
                    throw new Exception("no sleep streak found");
                }
                sql = "UPDATE GOALS SET sleepStreak="+currentStreak+1+" WHERE id="+id;
                stmt.executeUpdate(sql);
                //updates the sleep streak by adding 1
            }
            conn.close();

        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage()+ " in endStreak");
        }
    }

    public static boolean addStressEntry(int stressLevel, int day, int month, int year){
        Date addDate = Date.valueOf(year + "-" + month + "-" + day);
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                String sql = "INSERT INTO STRESS (id, stressLevel, addDate) VALUES("+id+","+ stressLevel+","+addDate.getTime()+")";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);

                conn.close();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println("exception caught when adding stress entry ");
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    //sets a given user variable in a given column of the User database
    private static void setUserIntVariable(String column, int value) {
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                String setValueString = "UPDATE USER SET " + column + "=? WHERE id="+id;
                PreparedStatement preparedSetValueStatement = conn.prepareStatement(setValueString);
                preparedSetValueStatement.setInt(1, value);
                preparedSetValueStatement.execute();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("exception caught when setting " + column);
            System.out.println(e.getLocalizedMessage());
        }
    }

    //gets an int from the table user given a column
    private static int getUserIntVariable(String column) {
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {

                int value = Integer.MIN_VALUE;
                String getValueString = "SELECT " + column + " FROM USER WHERE id="+id;
                PreparedStatement preparedGetValueStatement = conn.prepareStatement(getValueString);
                if (preparedGetValueStatement.execute()) {
                    ResultSet result = preparedGetValueStatement.getResultSet();
                    value = result.getInt(1);
                }
                conn.close();

                return value;
            }
        } catch (SQLException e) {
            System.out.println("exception caught when getting height");
            System.out.println(e.getLocalizedMessage());
        }

        return Integer.MIN_VALUE;
    }

    //sets the user's height to newAge
    public static void setUserHeight(int newAge) {
        setUserIntVariable("height", newAge);
    }

    //returns the current user's height
    public static int getUserHeight() {
        return getUserIntVariable("height");
    }

    //sets the current user's weight
    public static void setUserWeight(int newWeight) {
        setUserIntVariable("weight", newWeight);
    }

    public static int getUserWeight() {
        return getUserIntVariable("weight");
    }

    public static void setUserDateOfBirth(int year, int month, int day) {

        try {

            // parse the string into java.sql date
            Date newDate = Date.valueOf(year + "-" + month + "-" + day);

            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {

                int value = Integer.MIN_VALUE;
                String setDOBString = "UPDATE USER SET dateOfBirth=? ";
                PreparedStatement preparedSetDOBStatement = conn.prepareStatement(setDOBString);
                preparedSetDOBStatement.setDate(1, newDate);
                preparedSetDOBStatement.execute();
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage() + " exception caught when setting date of birth");
        }
    }

    // returns a date as a string in escaped "year-month-day" format
    public static String getUserDateOfBirth() {

        try {

            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {

                String value = null;
                String setDOBString = "SELECT dateOfBirth FROM USER";
                PreparedStatement preparedSetDOBStatement = conn.prepareStatement(setDOBString);
                if (preparedSetDOBStatement.execute()) {
                    ResultSet result = preparedSetDOBStatement.getResultSet();
                    value = result.getDate(1).toString();
                }

                conn.close();

                return value;
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage() + "exception caught when getting date of birth");
        }

        return null;
    }


    //function to initially create the database. This should only be called once, as the database file is now set up.
    public static void initialiseDatabase(){

        //add db
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            if (conn != null) {
                Statement stmt = conn.createStatement();
                String[] sql = getSetupQuery();
                for (String s : sql) {

                    stmt.execute(s);
                }
                //loops through each table creation statement to set up the database
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("exception was caught initialising database");
            System.out.println(e.getLocalizedMessage()+ " in initialiseDatabase");
        }
    }
    
    public static boolean databaseExists() {
    	return (new File("PI.db")).exists();
    }

    private static String[] getSetupQuery(){
        return new String[] {"CREATE TABLE USER (\n" +
                "  id INTEGER(4) NOT NULL primary key,\n" +
                "  firstLogin INTEGER(1) NOT NULL DEFAULT 1,\n" +
                "  name varchar(30) not null UNIQUE,\n" +
                "  password varchar(32) not null,\n" +
                "  salt varchar(8) not null,\n" +
                "  weight INT(3) not NULL DEFAULT 72,\n" +
                "  height INT(3) not null DEFAULT 174,\n" +
                "  dateOfBirth DATE not null DEFAULT \"2002-1-1\",\n" +
                "  lastQuestionTime INT(13) not null DEFAULT 1648080000\n" +
                ");",

                "CREATE TABLE FLUID (\n" +
                        "  id INTEGER(4) NOT NULL,\n" +
                        "  units INTEGER(3)  DEFAULT NULL,\n" +
                        "  caffeine INTEGER(4) DEFAULT NULL,\n" +
                        "  cupsOfWater INTEGER(3)  DEFAULT NULL,\n" +
                        "  addDate DATE NOT NULL\n" +
                        ");\n",

                "CREATE TABLE SLEEP (\n" +
                        "  id int(4) NOT NULL,\n" +
                        "  sleepTime INTEGER(2)  DEFAULT NULL,\n" +
                        "  timeToSleep INTEGER(3)  DEFAULT NULL,\n" +
                        "  sleepQuality INTEGER(2)  DEFAULT NULL,\n" +
                        "  addDate DATE NOT NULL\n" +
                        ");\n",


                "CREATE TABLE STRESS (\n" +
                        "  id INTEGER(4)  NOT NULL,\n" +
                        "  stressLevel int(2) DEFAULT NULL,\n" +
                        "  addDate DATE NOT NULL\n" +
                        ");"
                ,
                "CREATE TABLE SCREENTIME (\n" +
                        " id INTEGER(4) NOT NULL,\n" +
                        " screenTime REAL DEFAULT 6.4,\n"+
                        " addDate DATE NOT NULL\n" +
                        ");"
                ,
                "CREATE TABLE STRAVADATA (\n" +
                        " id INTEGER(4) NOT NULL, \n" +
                        " expiresAt INTEGER(12) NOT NULL DEFAULT -1, \n" +
                        " accessToken TEXT(40) DEFAULT NULL, \n" +
                        " refreshToken TEXT(40) DEFAULT NULL \n" +
                        ");"
                ,
                "CREATE TABLE FITNESS (\n" +
                        " id INTEGER(4) NOT NULL, \n" +
                        " exerciseTime INTEGER(4) NOT NULL DEFAULT -1, \n" +
                        " latestEndTime INTEGER(4) NOT NULL DEFAULT 0100, \n" +
                        " addDate DATE NOT NULL \n" +
                        ");"
                ,
                "CREATE TABLE GOALS (\n" +
                        "id INTEGER(4) NOT NULL,\n" +
                        "streakLength INTEGER(4) NOT NULL DEFAULT 0, \n" +
                        "sleepAverage REAL DEFAULT -1, \n" +
                        "sleepTarget REAL DEFAULT 8, \n" +
                        "cupsOfWater int(3) DEFAULT 0, " +
                        "sleepDuration int(2) DEFAULT 8, " +
                        "exerciseDuration int(2) DEFAULT 1, " +
                        "units int(2) DEFAULT 0," +
                        "screenTime int(2) DEFAULT 0, " +
                        "stress int(2) DEFAULT 0," +
                        "coffee int(2) DEFAULT 0," +
                        "tea int(2) DEFAULT 0, " +
                        "energyDrinks int(2) DEFAULT 0 " +
                        ");",

                "CREATE TABLE FACTORS (\n" +
                        "  id INTEGER(4)  NOT NULL,\n" +
                        "  caffeine int(1) NOT NULL DEFAULT 0,\n" +
                        "  alcohol int(1) NOT NULL DEFAULT 0,\n" +
                        "  fitness int(1) NOT NULL DEFAULT 0,\n" +
                        "  stress int(1) NOT NULL DEFAULT 0,\n" +
                        "  screenTime int(1) NOT NULL DEFAULT 0,\n" +
                        "  water int(1) NOT NULL DEFAULT 0\n" +
                        ");\n"};
    }

    public static Object[][] getGoalData(){
        Object[][] goals = {{"cupsOfWater", "sleepDuration", "exerciseDuration", "units", "screenTime", "stress", "coffee", "tea", "energyDrinks"},
                                {0, 0, 0, 0, 0, 0, 0, 0}};

        try {
            Connection conn = DriverManager.getConnection(databaseURL);

            Statement stmt = conn.createStatement();
            String sql = "SELECT cupsOfWater, sleepDuration, exerciseDuration, units, screenTime, stress, coffee, tea, energyDrinks FROM GOALS WHERE id=" + id;


            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                for (int i = 0; i < goals[0].length; i++) {
                    goals[1][i] = rs.getInt(rs.getInt((int) goals[0][i]));
                }
            }
            conn.close();
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }

        return goals;
    }

    //checks if users already exist. If none exist, returns false, otherwise returns true.
    //Can be used to check if it is a first login attempt on the device.
    public static boolean checkForUsers(){

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            String statement = "SELECT Count(*) as rowCount FROM USER";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(statement);

            // get value from rs now before it gets collected
            int rowCount = rs.getInt("rowCount");

            conn.close();

            // returns true if rows exist in the user table
            return rowCount > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
            System.out.println("something went wrong in checkForUsers");
        }
        return false;
    }

    //gets the salt from the database, puts the password+salt through a hashing algorithm then checks the database for matches
    public static int validateUser(String name, String password) {

        try{
            Connection conn = DriverManager.getConnection(databaseURL);

            String getSalt = "SELECT salt FROM USER where name=?";
            PreparedStatement preparedGetSaltStatement = conn.prepareStatement(getSalt);
            preparedGetSaltStatement.setString(1, name);

            //System.out.println("statement: " + preparedGetSaltStatement.toString());

            preparedGetSaltStatement.execute();
            ResultSet rs = preparedGetSaltStatement.getResultSet();

            String salt;

            //checks if a salt exists for that username
            //System.out.println("is first: " + Boolean.toString(rs.next()));
            if (rs.next()) {
                salt = rs.getString("salt");

            }
            else{
                conn.close();
                return -1;
            }

            password = hashPassword(password+salt);

            String getMatches = "SELECT id FROM USER WHERE name=? and password=?";
            PreparedStatement preparedGetMatchesStatement = conn.prepareStatement(getMatches);
            preparedGetMatchesStatement.setString(1, name);
            preparedGetMatchesStatement.setString(2, password);
            preparedGetMatchesStatement.execute();
            rs = preparedGetMatchesStatement.getResultSet();

            if(rs.next()){

                id = rs.getInt("id");
                conn.close();
                return 1;
            }
            else{
                conn.close();
                return -1;
            }

        }
        catch(Exception e){
            System.out.println("went wrong validating");
            System.out.println(e.getMessage()+ " in validateUser");
        }
        return -1;
    }
    //returns 1 if everything functioned correctly and the user was validated

    //adds a user taking in a name and password, and internally adding to
    public static int addUser(String name, String password){

        int nextId = -2;
        //if -2 is returned, something went wrong very early on

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();

            String checkNames = "SELECT * FROM USER WHERE name=?";
            PreparedStatement preparedUserCheckStatement = conn.prepareStatement(checkNames);
            preparedUserCheckStatement.setString(1, name);
            preparedUserCheckStatement.execute();
            ResultSet rs = preparedUserCheckStatement.getResultSet();

            // returns 0 if the username is already taken
            if (rs.next()) {
                System.out.println("username already taken");
                conn.close();
                return 0;
            }

            String salt = generateSalt();
            //creates a salt
            password = hashPassword(password+salt);
            //hashes the password with the salt through the SHA-256 hashing algorithm

            String getNextId = "SELECT MAX(id) FROM USER";
            nextId = 0;
            //0 automatically as if no value found, the first id is 0
            rs = stmt.executeQuery(getNextId);
            if(rs.next()){
                nextId = rs.getInt(1)+1;
                //gets the largest id existing and adds 1.
            }

            String addStatement = "INSERT INTO USER (id, name, password, salt, firstLogin) VALUES("+nextId+",?,?,?,0)";
            PreparedStatement preparedAddStatement = conn.prepareStatement(addStatement);

            // doesn't commit changes to database until signalled
            conn.setAutoCommit(false);

            // prepare the statement
            preparedAddStatement.setString(1, name);
            preparedAddStatement.setString(2,password);
            preparedAddStatement.setString(3,salt);

            // execute the prepared add statement
            preparedAddStatement.execute();

            // commit changes to database
            conn.commit();

            // reset connection
            conn.setAutoCommit(true);

            //should add a new user with appropriate id, name, password, salt, and set their first login to false.

            id = nextId;
            //sets the current user's id as this new user's id. Allows for future database calls to be easier

            stmt.executeUpdate("INSERT INTO FACTORS (id) VALUES("+id+")");
            stmt.executeUpdate("INSERT INTO GOALS (id) values("+id+")");
            //adds the id into factors for use later

            conn.close();

            return 1;
        }
        catch(Exception e){
            System.out.println("something went wrong when adding a new user");
            System.out.println(e.getMessage()+ " in addUser");
        }

        return 0;
        // exception was caught, so return an error
    }

    public static int removeUserData(int id){

        // removes all user information and data linked to the user
        // EXCEPT the user class

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            deleteFromTableById(conn, id, "FLUID");
            deleteFromTableById(conn, id, "SLEEP");
            deleteFromTableById(conn, id, "STRESS");

            conn.close();
            return 1;
        }
        catch(Exception e) {
            System.out.println("something went wrong removing user");
            System.out.println(e.getMessage()+ " in removeUserData");
        }

        return 0;

    }

    private static void deleteFromTableById(Connection conn, int id, String tableName) throws SQLException
    {
        // tableName is hard-coded, therefore preparing statement is not necessary for security
        String removeUser = "DELETE FROM " + tableName + " WHERE id=?";
        PreparedStatement preparedUserRemoveStatement = conn.prepareStatement(removeUser);
        preparedUserRemoveStatement.setString(1, Integer.toString(id));
        preparedUserRemoveStatement.execute();
    }


    private static final String[] saltCharacters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x",
            "y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4",
            "5","6","7","8","9","0","!","@","£","$","^","&","*","(",")","-","+","=","{","}",":",";","|","<",",",">",".",
            "?","~","±","§"};
    //character set doesn't include %,",',_,\ to prevent accidental sql issues (still use prepared statements regardless)

    public static String generateSalt(){
        Random random = new Random();

        StringBuilder salt = new StringBuilder();

        for(int i = 0; i<8;i++){
            salt.append(saltCharacters[random.nextInt(saltCharacters.length)]);
        }

        return salt.toString();
    }

    //hashes the password and salt input through the SHA-256 algorithm for storage in a non-readable format
    private static String hashPassword(String passwordAndSalt){

        byte[] passwordToBytes = passwordAndSalt.getBytes();
        BigInteger passwordToInteger = BigInteger.ONE;
        String hash = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            passwordToBytes = md.digest(md.digest(md.digest(passwordToBytes)));
            //applies the md.digest function 3 times to ensure higher security

            passwordToInteger = new BigInteger(1, passwordToBytes);
            hash = passwordToInteger.toString(16);


        }
        catch(Exception e) {
            System.out.println(e+ " in hashPassword");
        }

        return hash;
    }

    //counts the number of rows in a result set. Useful for loops.
    private static int countRows(ResultSet rs) {

        int rows = 0;

        try {
            rs.last();
            rows = rs.getRow();
            rs.beforeFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }


}
