module sleepAppDesktopBase {
	
	requires java.sql;
	requires java.desktop;
	requires org.junit.jupiter.api;
	requires rt;

	exports sleepAppBase;
	exports sleepAppDatabase;
	exports sleepAppGUI.visuals;
	
}