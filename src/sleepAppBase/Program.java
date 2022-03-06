package sleepAppBase;

import sleepAppGUI.interaction.Page;
import sleepAppGUI.visuals.*;
import sleepAppDatabase.*;

public class Program {

	public static void main(String[] args) {
		
		System.out.println("Hello, Sleep App! 2");
		
		UserLogin user = new UserLogin(null, null);
		Database.initialiseDatabase();
		
		if (isFirstStart())
		{
			int success = -1;
			while (success != 1)
			{
				user = createNewUser();
				success = Database.addUser(user.username(), user.password());
			}
		}
		else
		{
			int success = -1;
			while (success != 1)
			{
				user = createNewUser();
				
				switch (Database.validateUser(user.username(), user.password()))
				{
					case -2:
						System.out.println("went wrong early on");
						break;
					case -1:
						System.out.println("went wrong");
					case 0:
						System.out.println("username already exists");
					case 1:
						success = 1;
						break;
				}
			}
		}
		
		System.out.println("welcome: " + user.username());
		System.out.println("*open main dashboard for daily questions, options etc.*");
		
		Main main = new Main(800, 600);

        Page.setUpPages(main);
	}
	
	private static boolean isFirstStart()
	{
		System.out.println("*querys database to check if it's the first time opening*");
		return true;
	}
	
	private static UserLogin createNewUser()
	{
		return new UserLogin("theUsername", "thePassword");
	}

}
