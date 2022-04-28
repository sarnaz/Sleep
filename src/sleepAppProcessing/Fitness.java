package sleepAppProcessing;


import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import sleepAppDatabase.Database;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

public class Fitness {

    public static final String databaseURL = "jdbc:sqlite:PI.db";
    private static final String clientId = "80404";
    private static final String clientSecret = "a0dbfe606f36c20e5253c02c9f2908f4a67d1699";
    private String accessToken = null;

    private static String code = null;

    private static volatile boolean codeSet = false;


    public Fitness(int id, int day, int month, int year) {
        try {

            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            String returnPoint = "/test";
            HttpContext context = server.createContext(returnPoint);
            context.setHandler(Fitness::handleRequest);
            server.start();

            Connection conn= DriverManager.getConnection(databaseURL);

            Statement stmt= conn.createStatement();
            String sql = "select accessToken from STRAVADATA where id="+id;
            ResultSet rs = stmt.executeQuery(sql);


            String returnURL = "http://localhost:8000/test";
            String stringUri ="https://www.strava.com/oauth/authorize?client_id=80404&"
                    + "redirect_uri="+ returnURL +"&response_type=code&scope=activity:read_all";

            URI uri = new URI(stringUri);
            if(rs.next()){
                if(rs.getString("accessToken")==null){
                    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(uri);
                    }

                    while (!codeSet) {
                        Thread.onSpinWait();
                    }
                }
            }

            conn.close();

        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }

        getToken(id, code);
        addActivityData(id, day, month, year);

    }
    //non-static to ensure id is initialised

    //method based on code from LogicBig (May 11, 2018) Java-HttpServer example  source code. https://www.logicbig.com/tutorials/core-java-tutorial/http-server/http-server-basic.html.
    private static void handleRequest(HttpExchange exchange) throws IOException {
        printCode(exchange);
        String response = "You may now close this page";
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    //method based on code from LogicBig (May 11, 2018) Java-HttpServer example  source code. https://www.logicbig.com/tutorials/core-java-tutorial/http-server/http-server-basic.html.
    private static void printCode(HttpExchange exchange) {
        URI requestURI = exchange.getRequestURI();
        String returnedString = requestURI.getQuery();

        String[] valueArray = returnedString.split("&");
        if(valueArray[1].contains("=")){
            code = valueArray[1].replace("code=", "");
        }
        codeSet = true;
        //sets the value of "code" to the actual needed code for that user.
    }

    public void getToken(int id, String code) {

        int expiresAt = -1;
        String refreshToken = null;
        //-1 is my default is something is unavailable

        try {
            Connection conn = DriverManager.getConnection(databaseURL);


            Statement stmt = conn.createStatement();
            String sql = "select expiresAt from STRAVADATA where id=" + id;
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                expiresAt = rs.getInt("expiresAt");
            }

            rs = stmt.executeQuery("select refreshToken from STRAVADATA where id=" + id);

            if (rs.next()) {
                refreshToken = rs.getString("refreshToken");
            }

            conn.close();


            if (expiresAt == -1) {
                getNewToken("new", id, code);
            } else if (System.currentTimeMillis() > expiresAt) {
                //means the token has expired
                getNewToken(refreshToken, id);
            }
            //either sends a request for the first access token, a new access token, or nothing


            conn.close();

        } catch (Exception e) {

            System.out.println(e.getLocalizedMessage());
        }

    }


    private void getNewToken(String token, int id) {
        if (!Objects.equals(token, "new")) {
            getNewToken(token, id, null);
        }
    }

    private void getNewToken(String token, int id, String code) {

        try {

            URL url = null;

            if (token.equals("new")) {
                url = new URL("https://www.strava.com/oauth/token?client_id=" + clientId + "&client_secret=" + clientSecret + "&code="
                        + code + "&grant_type=authorization_code");
            } else {
                url = new URL("https://www.strava.com/oauth/token?client_id=" + clientId + "&client_secret=" + clientSecret + "&"
                        + "refresh_token=" + token + "&grant_type=refresh_token");
            }


            URLConnection con = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection) con;
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json; utf-8");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setDoOutput(true);


            Scanner scanner = new Scanner(httpCon.getInputStream());
            String output = "";

            while (scanner.hasNext()) {
                output = output + scanner.nextLine();
            }
            // gets the access token for the API

            String[] dividedOutput = output.split(",");
            //cuts up the output into types
            String accessToken;
            //takes the access token
            String refreshToken;
            // takes the refresh token
            String expiresAt;
            //takes the time since the epoch in milliseconds which the access token expires at

            if (token.equals("new")) {

                accessToken = dividedOutput[4].substring(16, 56);
                refreshToken = dividedOutput[3].substring(17, 57);
                expiresAt = dividedOutput[1].substring(13);
            } else {
                //divides the refresh requests response up
                accessToken = dividedOutput[1].substring(16, 56);
                refreshToken = dividedOutput[4].substring(17, 57);
                expiresAt = dividedOutput[2].substring(13);
            }

            this.accessToken = accessToken;


            Connection conn = DriverManager.getConnection(databaseURL);


            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Update STRAVADATA SET accessToken=\"" + accessToken + "\", refreshToken=\"" + refreshToken + "\", expiresAt=\"" + expiresAt + "\" WHERE"
                    + " id=" + id);


            conn.close();


            //stops the method being called again and returns the user to the main page

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /*
       retrieves activity length for all activites on a given day
    */
    public void addActivityData(int id, int day, int month, int year) {

        int totalActivityTime = 0;
        long time = Date.valueOf(year+"-"+month+"-"+(day-1)).getTime()/100;

        try {

            //getSegments(request);
            //retrieves data about segments for later. Used here as it prevents repeats
            URL url = new URL("https://www.strava.com/api/v3/athlete/activities?after="+time+"&page=1&per_page=30&access_token="+accessToken);

            URLConnection con = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection)con;
            httpCon.setRequestMethod("GET");
            httpCon.setRequestProperty("Content-Type", "application/json; utf-8");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setDoOutput(true);

            Scanner scanner = new Scanner(httpCon.getInputStream());
            String output = "";

            while(scanner.hasNext()) {
                output = output + scanner.nextLine();
            }


            if(!output.equals("")) {
                String[] dividedOutput = output.split(",\"");
                //allows me to find the correct pieces of data

                int plusOrMinus;
                boolean timeFound, stop1, stop2;

                int lowerBound;
                //increases lower bound on loops to avoid it finding the same data set over and over, causing an infinite loop

                plusOrMinus = 0;

                for(int i = 5; i-plusOrMinus<dividedOutput.length; i+=40) {
                    //40 is the approximate difference between each value in the array
                    timeFound = false;
                    plusOrMinus = 0;

                    lowerBound = i;
                    stop1 = false;
                    stop2 = false;
                    // resets the damn variables

                    while(!timeFound) {
                        if(i+plusOrMinus<dividedOutput.length) {
                            if(dividedOutput[i+plusOrMinus].contains("moving_time") && dividedOutput[i+plusOrMinus-1].contains("distance") &&
                                    dividedOutput[i+plusOrMinus-2].contains("name")) {

                                dividedOutput[i+plusOrMinus]=dividedOutput[i+plusOrMinus]
                                        .replace("\"", "")
                                        .replace("moving_time", "")
                                        .replace(":", "");

                                System.out.println(dividedOutput[i+plusOrMinus]);


                                totalActivityTime+=Math.round(Double.parseDouble(dividedOutput[i+plusOrMinus])/60);

                                timeFound=true;

                                //should speed up finding next value, as approx 60 gap is between found positions
                            }
                        }
                        else {
                            stop1=true;
                            //stops the program if out of bounds
                        }
                        if(i-plusOrMinus-2>lowerBound) {
                            if(dividedOutput[i-plusOrMinus].contains("moving_time") && dividedOutput[i-plusOrMinus-1].contains("distance") &&
                                    dividedOutput[i-plusOrMinus-2].contains("name"))  {

                                dividedOutput[i-plusOrMinus]=dividedOutput[i-plusOrMinus]
                                        .replace("\"", "")
                                        .replace("moving_time", "")
                                        .replace(":", "");


                                //updateSegmentTimes(request, dividedOutput[i-plusOrMinus]);


                                totalActivityTime+=Math.round(Double.parseDouble(dividedOutput[i-plusOrMinus])/60);

                                timeFound=true;
                            }
                        }
                        else {
                            stop2=true;
                        }
                        if(stop1 && stop2) {
                            timeFound = true;
                            //stops the search if the search is outside both bounds of the array.
                        }

                        stop1=false;
                        stop2=false;
                        plusOrMinus++;

                    }
                    //repeats until the activity Id is found, going further away from the guess each time
                    //similar to jump search, with a small tweak
                }
                //continues approximating Id positions until no more are left
            }


            Database.addFitnessEntry(totalActivityTime, 0, day, month, year);


        } catch (Exception e) {

            e.printStackTrace();
        }
        //ensures that this loop isn't called repeatedly while users use the program
    }
    //gets all activity IDs available
}