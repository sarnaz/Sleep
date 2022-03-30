package sleepAppBase;

import sleepAppGUI.visuals.*;
import sleepAppGUI.interaction.*;
import sleepAppDatabase.*;

public class Program {

	public static void main(String[] args) {

		Main.main(null);
	}
	
	private static boolean isFirstStart()
	{
		return !Database.checkForUsers();
	}
	
	private static UserLogin createNewUser()
	{
		return new UserLogin("theUsername", "thePassword");
	}

}
