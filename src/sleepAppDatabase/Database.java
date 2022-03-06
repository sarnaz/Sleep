package com.company;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Random;

public class Database {

    private static boolean firstLogin = true;
    //assumes this is the user's first login unless proven otherwise
    public static final String databaseURL = "jdbc:sqlite:PI.db";
    //the database url. final as it should never be changed
    private static int id = -100;
    //-100 until logged in. Anything that requires an id should check its value. if it is -100, it should not perform that task


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
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }


    }

    private static String[] getSetupQuery(){
        return new String[] {"CREATE TABLE USER (\n" +
                "  id INTEGER(4) NOT NULL primary key,\n" +
                "  firstLogin INTEGER(1) NOT NULL DEFAULT 1,\n" +
                "  name varchar(30) not null UNIQUE,\n" +
                "  password varchar(32) not null,\n" +
                "  salt varchar(8) not null,\n" +
                "  weight INT(3) not NULL DEFAULT 72,\n" +
                "  height INT(3) not null DEFAULT 174\n" +
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
                ");\n"};
    }

    //checks if users already exist. If none exist, returns false, otherwise returns true.
    //Can be used to check if it is a first login attempt on the device.
    public static boolean checkForUsers(){

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            String statement = "SELECT * FROM USER";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(statement);

            return rs.next();

        } catch (SQLException e) {
            System.out.println("a"+e.getLocalizedMessage());
        }
        return false;
    }

    //gets the salt from the database, puts the password+salt through a hashing algorithm then checks the database for matches
    public static int validateUser(String name, String password) {

        try{
            Connection conn = DriverManager.getConnection(databaseURL);
            String getSalt = "SELECT salt FROM USER where name="+name;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getSalt);

            String salt = "";

            //checks if a salt exists for that username
            if(rs.next()){
                salt = rs.getString("salt");
            }
            else{
                return -1;
            }

            password = hashPassword(password+salt);

            String getMatches = "SELECT id FROM USER WHERE name="+name + " and password="+password;
            rs = stmt.executeQuery(getMatches);

            if(rs.next()){

                id = rs.getInt("id");
                return 1;
            }
            else{
                return -1;
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        return -1;
    }
    //returns 1 if everything functioned correctly and the user was validated

    public static int addUser(String name, String password){

        int nextId = -2;
        //if -2 is returned, something went wrong very early on

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();
            String checkNames = "SELECT * FROM USER WHERE name=\"" + name+"\"";
            //maybe make this a prepared statement
            ResultSet rs = stmt.executeQuery(checkNames);


            if (rs.next()) {
                return 0;
            }
            //returns 0 if the username is already taken

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
            conn.setAutoCommit(false);
            preparedAddStatement.setString(1, name);
            preparedAddStatement.setString(2,password);
            preparedAddStatement.setString(3,salt);
            System.out.println(preparedAddStatement.toString()+"\n");
            preparedAddStatement.executeUpdate();
            conn.commit();
            //should add a new user with appropriate id, name, password, salt, and set their first login to false.

            id = nextId;
            //sets the current users id as this new users id. Allows for future database calls to be easier

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        return 1;
        //return of 1 means a success
    }

    //remove later
    public static void check(){
        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();
            String checkNames = "SELECT * FROM USER";
            //maybe make this a prepared statement
            ResultSet rs = stmt.executeQuery(checkNames);


            if (rs.next()) {
                System.out.println(rs.getString("name") + " " + rs.getString("password") + rs.getString("weight"));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //removes a user's data. returns 0 if the user doesn't exist or something is incorrect
    public static int clearData(){

        try {
            Connection conn = DriverManager.getConnection(databaseURL);
            Statement stmt = conn.createStatement();
            //maybe make this a prepared statement

            if(id>-1) {
                String clearString = "DELETE FROM FLUID NATURAL JOIN SLEEP NATURAL JOIN STRESS WHERE id=" + id;

                stmt.executeUpdate(clearString);
            }
            else{
                return id;
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }


    private static String[] saltCharacters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x",
            "y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4",
            "5","6","7","8","9","0","!","@","£","$","^","&","*","(",")","-","+","=","{","}",":",";","|","<",",",">",".",
            "?","~","±","§"};
    //character set doesn't include %,",',_,\ to prevent accidental sql issues (still use prepared statements regardless)

    public static String generateSalt(){
        Random random = new Random();

        String salt = "";

        for(int i = 0; i<8;i++){
            salt=salt+saltCharacters[random.nextInt(saltCharacters.length)];
        }

        return salt;
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
            hash = String.valueOf(passwordToInteger.toString(16));


        }
        catch(Exception e) {
            System.out.println("error: "+ e);
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
