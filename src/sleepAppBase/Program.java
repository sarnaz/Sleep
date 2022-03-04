package sleepAppBase;
import sleepAppGUI.visuals.*;
import sleepAppDatabase.*;

public class Program {

	public static void main(String[] args) {
		
		System.out.println("Hello, Sleep App! 2");
		
		startDatabase();
		
		UserDetails user;
		
		if (isFirstStart())
		{
			displayFirstStartWindow();
			user = createNewUser();
			saveNewUser(user);
		}
		else
		{
			UserLogin loginInformation = new UserLogin(null, null);
			boolean validLogin = false;
			while (!validLogin)
			{
				displayLoginWindow();
				loginInformation = getLoginInformation();
				validLogin = isValidLogin(loginInformation);
			}
			user = getUserDetails(loginInformation);
			
		}
		
		System.out.println("welcome: " + user.name() + ", you are " + user.age() + " years old!");
		System.out.println("*open main dashboard for daily questions, options etc.*");
		
	}
	
	private static boolean isFirstStart()
	{
		System.out.println("*querys database to check if it's the first time opening*");
		return true;
	}
	
	private static UserDetails createNewUser()
	{
		return new UserDetails("theUsername", 20);
	}
	
	private static void saveNewUser(UserDetails newUser)
	{
		System.out.println("*saves the user login details to the database*");
		return;
	}
	
	private static void startDatabase()
	{
		System.out.println("*starts the database for initialisation if required*");
		return;
	}
	
	private static UserDetails getUserDetails(UserLogin userLogin)
	{
		System.out.println("*fetches the user details from the database given the login information*");
		return new UserDetails(userLogin.username(), 20);
	}
	
	private static UserLogin getLoginInformation()
	{
		return new UserLogin("theUsername", "thePassword");
	}
	
	private static boolean isValidLogin(UserLogin userLogin)
	{
		System.out.println("query database to check if login information is valid");
		return true;
	}
	
	private static void displayFirstStartWindow()
	{
		System.out.println("*open windows here and collect user data and login information*");
		return;
	}
	
	private static void displayLoginWindow()
	{
		System.out.println("*open windows here and collect login information*");
		return;
	}

}
