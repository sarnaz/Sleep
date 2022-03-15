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
		f.delete();
		
		Database.initialiseDatabase();
		assert(Database.addUser("theUsername", "thePassword") == 1);
		assert(Database.validateUser("theUsername", "thePassword") == 1);
		assert(Database.checkForUsers());
		assert(Database.getCurrentUserId() == 1);
		assert(Database.removeUserData(1) == 1);
		assert(Database.validateUser("theUsername", "thePassword") == 1);
		assert(Database.validateUser("theUsername", "thePassword2") == -1);
		assert(Database.validateUser("theUsername2", "thePassword") == -1);
		assert(Database.validateUser("theUsername2", "thePassword2") == -1);
		assert(Database.checkForUsers());
		
		Pattern saltPattern = Pattern.compile("([a-zA-Z0-9!@£$^&*\\(\\)-+=\\{\\}:;|<,>.?~±§])+");
		Matcher saltMatch = saltPattern.matcher(Database.generateSalt());
		assert(saltMatch.find());
		
		f = new File("PI.db");
		f.delete();
	}

}
