module sleepAppDesktopBase {
	
	requires java.sql;
	requires java.desktop;
	requires org.junit.jupiter.api;
    requires javax.servlet;
    requires jdk.httpserver;

    exports sleepAppBase;
	exports sleepAppDatabase;
	exports sleepAppGUI.visuals;
	
}