package sleepAppDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Random;

public class Database {

    private static boolean firstLogin = true;
    //assumes this is the user's first login unless proven otherwise
    public static final String databaseURL = "jdbc:sqlite:PI.db";
    //the database url. final as it should never be changed
    private static int id;

    public static int getCurrentUserId()
    {
    	return id;
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
        } catch (SQLException e) {
        	System.out.println("exception was caught initialising database");
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

            String salt = "";

            //checks if a salt exists for that username
            //System.out.println("is first: " + Boolean.toString(rs.next()));
            if(rs.next()){
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
            
            String checkNames = "SELECT * FROM USER WHERE name=?";
            PreparedStatement preparedUserCheckStatement = conn.prepareStatement(checkNames);
            preparedUserCheckStatement.setString(1, name);
            preparedUserCheckStatement.execute();
            ResultSet rs = preparedUserCheckStatement.getResultSet();

            // returns 0 if the username is already taken
            if (rs.next()) {
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
            //sets the current users id as this new users id. Allows for future database calls to be easier

            conn.close();
            return 1;
        }
        catch(Exception e){
        	System.out.println("something went wrong when adding a new user");
        	System.out.println(e.getMessage());
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
        	System.out.println(e.getMessage());
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
