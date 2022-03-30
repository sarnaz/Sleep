package DatabaseTest;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

import sleepAppDatabase.Database;

class DatabaseTest {
	
	@Test
	void test() {
		
		// https://www.w3schools.com/java/java_files_delete.asp
		// I could look up how to do this myself

		// delete the database file to get into an expected state - no database
		File f = new File("PI.db");
		System.out.println(f.exists());
		System.out.println(f.delete());
		
		// create the database and run setup query
		Database.initialiseDatabase();
		
		System.out.println("database intitialised");
		// add a user and validate
		assert(Database.addUser("theUsername", "thePassword") == 1);
		assert(Database.validateUser("theUsername", "thePassword") == 1);
		
		System.out.println("passed add user");
		
		// test checkForUsers()
		assert(Database.checkForUsers());
		assert(Database.getCurrentUserId() == 1);
		
		// removes all user data 
		assert(Database.removeUserData(1) == 1);
		assert(Database.validateUser("theUsername", "thePassword") == 1);
		assert(Database.validateUser("theUsername", "thePassword2") == -1);
		assert(Database.validateUser("theUsername2", "thePassword") == -1);
		assert(Database.validateUser("theUsername2", "thePassword2") == -1);
		assert(Database.checkForUsers());

		// set user information
		Database.setUserHeight(20);
		Database.setUserWeight(63);
		Database.setUserDateOfBirth(4000,5,6);
		
		// retrieve user information
		assert(Database.getUserHeight() == 20);
		assert(Database.getUserWeight() == 63);
		System.out.println(Database.getUserDateOfBirth());
		assert(Database.getUserDateOfBirth().equals("4000-05-06"));
		
		// check that the salt uses expected characters
		Pattern saltPattern = Pattern.compile("^([a-zA-Z0-9!@£$^&*\\(\\)-+=\\{\\}:;|<,>.?~±§])+$");
		Matcher saltMatch = saltPattern.matcher(Database.generateSalt());
		assert(saltMatch.find());
		
		// return to the expected state
		f = new File("PI.db");
		f.delete();
	}

}
