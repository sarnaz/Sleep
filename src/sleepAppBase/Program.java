package sleepAppBase;

import sleepAppGUI.visuals.*;
import sleepAppGUI.interaction.*;
import sleepAppDatabase.*;

public class Program {

	public static void main(String[] args) {
		
		System.out.println("Hello, Sleep App! 2");
		
		UserLogin user = new UserLogin(null, null);
		
		if (isFirstStart())
		{
			
			System.out.println("is first start");
			Database.initialiseDatabase();
			int success = -1;
			while (success != 1)
			{
				user = createNewUser();
				success = Database.addUser(user.username(), user.password());
				

				switch (success)
				{
					case -2:
						System.out.println("went wrong early on");
						break;
					case -1:
						System.out.println("went wrong");
						break;
					case 0:
						System.out.println("username already exists");
						break;
					case 1:
						System.out.println("successful");
						break;
				}
				
				System.out.println("success status " + Integer.toString(success));
				System.out.println("user ID: " + Integer.toString(Database.getCurrentUserId()));
				if (success != 1) System.exit(1);
			}
		}
		else
		{
			System.out.println("is not first start");
			int success = -1;
			while (success != 1)
			{
				user = createNewUser();
				
				success = Database.validateUser(user.username(), user.password());
				
				if (success != 1) System.exit(1);
			}
		}
		
		System.out.println("welcome: " + user.username());
		System.out.println("*open main dashboard for daily questions, options etc.*");
		
		System.out.println("current user id: " + Integer.toString(Database.getCurrentUserId()));
		Database.removeUser(Database.getCurrentUserId());
		
		Main main = new Main(800, 600);

        Page.setUpPages(main);
        
        System.exit(1);
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
