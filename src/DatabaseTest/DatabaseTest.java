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
		// delete the database file to get into an expected state - no database
		
		File f = new File("PI.db");
		boolean databaseExists = f.exists();
		System.out.println("Database deleted: " + Boolean.toString(f.delete()));
		
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
		
		assert(Database.getCurrentUserId() == 1); // check equals 1
		
		Object[][] factors = Database.getFactors(); // check first row is string, second row is boolean

		// factors should have no value assigned yet
		assert(factors == null);
		
		assert(Database.askDailyQuestionsCheck() == true); // should return true (defaults previous check time to 24th March 2022 00:00:00)

		Object[][] testFactors = {{"caffeine", "alcohol", "fitness", "stress", "water", "screenTime"},{false, true, true, false, false, false}};
		assert(Database.setFactors(testFactors)); // expect true
		factors = Database.getFactors(); // check same as input for previous function

		for (int i = 0; i < testFactors.length; i++) {
			assert(factors[0][i] instanceof String);
			assert(factors[0][i] == testFactors[0][i]);
			assert(factors[1][i] instanceof Boolean);
			assert(factors[1][i] == testFactors[1][i]);
		}
		
		// set user information
		Database.setUserHeight(20);
		Database.setUserWeight(63);
		Database.setUserDateOfBirth(4000,5,6);
		
		assert(Database.getUserHeight() == 20);
		
		assert(Database.getUserWeight() == 63);
		
		// retrieve user information
		assert(Database.getUserHeight() == 20);
		assert(Database.getUserWeight() == 63);
		//System.out.println(Database.getUserDateOfBirth());
		assert(Database.getUserDateOfBirth().equals("4000-05-06"));
		
		// check that the salt uses expected characters
		Pattern saltPattern = Pattern.compile("^([a-zA-Z0-9!@£$^&*\\(\\)-+=\\{\\}:;|<,>.?~±§])+$");
		Matcher saltMatch = saltPattern.matcher(Database.generateSalt());
		assert(saltMatch.find());
		
		assert(Database.removeUserData(1) == 1); // expect return 1
		
		
		// return to the expected state
		f = new File("PI.db");
		System.out.println("Database deleted: " + Boolean.toString(f.delete()));
	}

}
