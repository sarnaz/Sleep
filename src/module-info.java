module sleepAppDesktopBase {
	
	requires java.sql;
	requires java.desktop;
	requires org.junit.jupiter.api;
    requires javax.servlet;

    exports sleepAppBase;
	exports sleepAppDatabase;
	exports sleepAppGUI.visuals;
	
}