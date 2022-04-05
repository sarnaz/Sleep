package sleepAppProcessing;



import java.sql.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Fitness extends HttpServlet{

    private int id;
    public static final String databaseURL = "jdbc:sqlite:PI.db";
    private static final String clientId = "80404";
    private static final String clientSecret = "a0dbfe606f36c20e5253c02c9f2908f4a67d1699";
    private String accessToken = null;

    public Fitness(int id){
        this.id = id;
    }
    //non-static to ensure id is initialised

    //takes in a httpservlet request and uses it to get the token
    public void getToken(HttpServletRequest request) {

        int expiresAt = -1;
        String refreshToken = null;
        String code = (String)request.getParameter("code");
        //-1 is my default is something is unavailable

        try {
            Connection conn= DriverManager.getConnection(databaseURL);


            Statement stmt= conn.createStatement();
            String sql = "select expiresAt from STRAVADATA where id=\""+id+"\"";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                expiresAt = rs.getInt("expiresAt");
            }

            rs = stmt.executeQuery("select refreshToken from STRAVADATA where id=\""+id+"\"");

            if(rs.next()) {
                refreshToken = rs.getString("refreshToken");
            }


            if(expiresAt==-1) {
                getNewToken(request, "new", code);
            }
            if(System.currentTimeMillis()>expiresAt) {
                //means the token has expired
                getNewToken(request, refreshToken, null);
            }
            //either sends a request for the first access token, a new access token, or nothing

            rs = stmt.executeQuery("select accessToken from STRAVADATA where id=\""+(id)+"\"");

            if(rs.next()) {
                this.accessToken = rs.getString("accessToken");
                //sets the access token for use in later cURL requests
            }


            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    /*
        called by getToken method. Performs actual retrieval of the token
    */
    private void getNewToken(HttpServletRequest request, String token, String code) {

        HttpSession session = request.getSession();

        try {

            URL url = null;

            if(token.equals("new")) {
                url = new URL("https://www.strava.com/oauth/token?client_id="+clientId+"&client_secret="+clientSecret+"&code="
                        +((String)request.getParameter("code"))+"&grant_type=authorization_code");
            }
            if(!token.equals("new")) {
                url = new URL("https://www.strava.com/oauth/token?client_id="+clientId+"&client_secret="+clientSecret+"&"
                        + "refresh_token="+token+"&grant_type=refresh_token");
            }


            URLConnection con = url.openConnection();
            HttpURLConnection httpCon = (HttpURLConnection)con;
            httpCon.setRequestMethod("POST");
            httpCon.setRequestProperty("Content-Type", "application/json; utf-8");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setDoOutput(true);


            Scanner scanner = new Scanner(httpCon.getInputStream());
            String output = "";

            while(scanner.hasNext()) {
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

            if(token.equals("new")) {

                accessToken = dividedOutput[4].substring(16,56);
                refreshToken  = dividedOutput[3].substring(17,57);
                expiresAt = dividedOutput[1].substring(13);
            }
            else {
                //divides the refresh requests response up
                accessToken = dividedOutput[1].substring(16,56);
                refreshToken  = dividedOutput[4].substring(17,57);
                expiresAt = dividedOutput[2].substring(13);
            }
            this.accessToken = accessToken;


            Connection conn=DriverManager.getConnection(databaseURL);


            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.executeUpdate("Update STRAVADATA set accessToken="+accessToken+", refreshToken="+refreshToken+"\", expiresAt=\""+expiresAt+"\" where"
                    + " id=\""+id+"\"");


            conn.close();

            //stops the method being called again and returns the user to the main page

        } catch(Exception e) {
            System.out.println(e);
        }
    }


    /*
        retrieves specific activity data
     */
    public void getActivityData(HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            getSegments(request);
            //retrieves data about segments for later. Used here as it prevents repeats

            int startTime = 0;

            Connection conn= DriverManager.getConnection(databaseURL);

            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select startDate from teams where teamId="+session.getAttribute("teamID"));

            if(rs.next()) {
                startTime = rs.getInt("startDate");
                //used in getting data later
            }

            conn.close();

            URL url = new URL("https://www.strava.com/api/v3/athlete/activities?after="+startTime+"&page=1&per_page=30&access_token="+session.getAttribute("accessToken"));

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

            if(output != null) {
                String[] dividedOutput = output.split(",");
                //allows me to find the correct pieces of data

                int plusOrMinus;
                boolean idFound, stop1, stop2;

                int lowerBound = 0;
                //increases lower bound on loops to avoid it finding the same data set over and over, causing an infinite loop

                for(int i = 10; i<dividedOutput.length;i+=50) {

                    idFound = false;
                    plusOrMinus = 0;

                    lowerBound = i;
                    stop1 = false;
                    stop2 = false;
                    // resets the damn variables

                    while(!idFound) {


                        if(i+plusOrMinus<dividedOutput.length) {
                            if(dividedOutput[i+plusOrMinus].contains("id") && dividedOutput[i+plusOrMinus+1].contains("external_id") &&
                                    !dividedOutput[i+plusOrMinus-1].contains("athlete") && dividedOutput[i+plusOrMinus-2].contains("Ride")) {

                                dividedOutput[i+plusOrMinus]=dividedOutput[i+plusOrMinus]
                                        .replace("\"", "")
                                        .replace("id", "")
                                        .replace(":", "");


                                updateSegmentTimes(request, dividedOutput[i+plusOrMinus]);

                                idFound=true;

                                //should speed up finding next value, as approx 60 gap is between found positions
                            }
                        }
                        else {
                            stop1=true;
                            //stops the program if out of bounds
                        }
                        if(i-plusOrMinus-2>lowerBound) {
                            if(i-plusOrMinus==231) {
                                System.out.println(dividedOutput[i-plusOrMinus]);
                            }
                            if(dividedOutput[i-plusOrMinus].contains("id") && dividedOutput[i-plusOrMinus+1].contains("external_id") &&
                                    !dividedOutput[i-plusOrMinus-1].contains("athlete") && dividedOutput[i-plusOrMinus-2].contains("Ride")) {

                                dividedOutput[i-plusOrMinus]=dividedOutput[i-plusOrMinus]
                                        .replace("\"", "")
                                        .replace("id", "")
                                        .replace(":", "");



                                updateSegmentTimes(request, dividedOutput[i-plusOrMinus]);

                                idFound=true;
                            }
                        }
                        else {
                            stop2=true;
                        }
                        if(stop1 && stop2) {
                            idFound = true;
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


        } catch (Exception e) {

            e.printStackTrace();
        }
        session.setAttribute("checkedStravaData", true);
        //ensures that this loop isn't called repeatedly while users use the program

    }
    //gets all activity IDs available

    public void getSegments(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String[] segmentNames;

        try {

            Connection conn=DriverManager.getConnection(databaseURL);

            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select segments.name from segments join SegmentToSegmentGroup on segments.id=SegmentToSegmentGroup.SegmentID "
                    + "JOIN SegmentGroups on SegmentToSegmentGroup.SegmentGroupID=SegmentGroups.id WHERE SegmentGroups.teamId="+session.getAttribute("teamID"));

            int rowCount = countRows(rs);
            segmentNames = new String[rowCount];

            if(rs.next()) {
                for(int i = 0; i<rowCount;i++) {
                    segmentNames[i] = rs.getString("name");
                    rs.next();
                }
            }

            session.setAttribute("segmentNames", segmentNames);

            rs = stmt.executeQuery("SELECT segments.name, CyclistsToSegments.segmentId, CyclistsToSegments.time FROM Segments, CyclistsToSegments"
                    + " WHERE CyclistsToSegments.cyclistID="+session.getAttribute("userID")+" AND CyclistsToSegments.segmentID=segments.id");
            //gets segment name and time for each segment the cyclist has COMPLETED no result returned for incomplete segments

            rowCount = countRows(rs);

            String[] completedSegmentNames = new String[rowCount];
            int[] completedSegmentTimes = new int[rowCount];
            int[] completedSegmentIds = new int[rowCount];

            if(rs.next()) {
                for(int i = 0; i<rowCount;i++) {
                    completedSegmentNames[i] = rs.getString("name");
                    completedSegmentTimes[i] = rs.getInt("time");
                    completedSegmentIds[i] = rs.getInt("segmentId");

                    rs.next();
                }
            }

            session.setAttribute("completedSegmentIds", completedSegmentIds);
            session.setAttribute("completedSegmentNames", completedSegmentNames);
            session.setAttribute("completedSegmentTimes", completedSegmentTimes);


            conn.close();
            // puts all segment names into an array




        }catch(Exception e) {
            System.out.println("exception "+e+" in StravaData.getAllSegments");
        }
    }

    public void updateSegmentTimes(HttpServletRequest request, String activityId) {


        //ensures the process isn't done for an empty dataSet
        URL url;
        URLConnection con;
        HttpURLConnection httpCon;
        HttpSession session = request.getSession();
        String output = "";

        try {

            url = new URL("https://www.strava.com/api/v3/activities/"+activityId+"?include_all_efforts=\"true\"&access_token="
                    +session.getAttribute("accessToken"));

            con = url.openConnection();
            httpCon = (HttpURLConnection)con;
            httpCon.setRequestMethod("GET");
            httpCon.setRequestProperty("Content-Type", "application/json; utf-8");
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setDoOutput(true);

            Scanner scanner = new Scanner(httpCon.getInputStream());


            while(scanner.hasNext()) {
                output = output + scanner.nextLine();
            }
            //gives a string version of the output approx 23 pages long, but varying depending on segment numebrs

            String[] dividedOutput = output.split(",");
            boolean idFound = false;
            int plusOrMinus = 0;

            boolean stop1 = false;
            boolean stop2 = false;

            int lowerBound = 0;

            ArrayList<String> acquiredSegments = new ArrayList<>();
            ArrayList<String> segmentTimes = new ArrayList<>();

            for(int i = 61; i<dividedOutput.length;i+=16) {
                //61 bypasses the useless initial data, which is always >61 from the beginning

                idFound = false;
                plusOrMinus = 0;
                stop1 = false;
                stop2 = false;
                // resets the damn variables

                lowerBound = i;
                //increasing the lower bound prevents repetitions of the same values, which could cause infinite loops

                while(!idFound) {



                    if(i+plusOrMinus<dividedOutput.length) {
                        if(dividedOutput[i+plusOrMinus].contains("name") && dividedOutput[i+plusOrMinus+1].contains("activity\":")
                                && dividedOutput[i+plusOrMinus-1].contains("resource_state\":2")) {

                            idFound = true;
                            dividedOutput[i+plusOrMinus]=dividedOutput[i+plusOrMinus]
                                    .replace("\"", "")
                                    .replace("name", "")
                                    .replace(":", "");
                            acquiredSegments.add(dividedOutput[i+plusOrMinus]);
                            segmentTimes.add(dividedOutput[i+plusOrMinus+5]
                                    //+5 brings the output to the "moving time" value, which is always 5 positions ahead of the ID
                                    .replace("\"", "")
                                    .replace("elapsed_time", "")
                                    .replace(":", ""));

                            i=i+plusOrMinus;

                        }
                    }
                    else {
                        stop1=true;
                        //stops the program if out of bounds
                    }
                    if(i-plusOrMinus-2>lowerBound-2) {
                        if(dividedOutput[i-plusOrMinus].contains("name") && dividedOutput[i-plusOrMinus+1].contains("activity\":")
                                && dividedOutput[i-plusOrMinus-1].contains("resource_state:\"2")) {

                            idFound = true;
                            dividedOutput[i-plusOrMinus]=dividedOutput[i-plusOrMinus]
                                    .replace("\"", "")
                                    .replace("name", "")
                                    .replace(":", "");
                            acquiredSegments.add(dividedOutput[i-plusOrMinus]);
                            segmentTimes.add(dividedOutput[i-plusOrMinus+5]
                                    .replace("\"", "")
                                    .replace("elapsed_time", "")
                                    .replace(":", ""));


                            i=i-plusOrMinus;
                        }
                    }
                    else {
                        stop2=true;
                    }
                    if(stop1 && stop2) {
                        idFound = true;
                        //stops the search if the search is outside both bounds of the array.
                    }

                    stop1=false;
                    stop2=false;
                    plusOrMinus++;

                }
                // repeated version of the algorithm in getActivityData(),  reused for this


            }


            checkAcquiredSegments(request,acquiredSegments, segmentTimes);

        }catch(Exception e) {

            System.out.println("error "+e+" in StravaData.updateSegmentTimes");
        }

    }


    public void checkAcquiredSegments(HttpServletRequest request, ArrayList<String> acquiredSegments, ArrayList<String> times) {

        HttpSession session = request.getSession();

        String[] segmentNames = (String[]) session.getAttribute("segmentNames");
        String[] completedSegmentNames = (String[]) session.getAttribute("completedSegmentNames");
        int[] completedSegmentTimes = (int[]) session.getAttribute("completedSegmentTimes");
        int[] completedSegmentIds = (int[])session.getAttribute("completedSegmentIds");

        int segmentID = 0;

        boolean segmentAlreadyCompleted = false;


        try {

            Connection conn=DriverManager.getConnection(databaseURL);

            Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;

            for(int i = 0; i<acquiredSegments.size();i++) {
                for(int j = 0; j<segmentNames.length;j++) {

                    if(segmentNames[j].contains(acquiredSegments.get(i))) {
                        //finds matching segments from the results



                        for(int x = 0; x<completedSegmentNames.length;x++) {

                            if(segmentNames[j].equals(completedSegmentNames[x]) && completedSegmentTimes[x]<=Integer.valueOf(times.get(i))) {

                                stmt.executeUpdate("Update CyclistsToSegments set time ="+times.get(j)+" where cyclistID="+session.getAttribute("userID")
                                        +" and segmentId="+completedSegmentIds[x]);

                                segmentAlreadyCompleted = true;
                            }
                        }
                        //checks if the cyclist has already completed the segment, then compares the times, uploading the new time if faster

                        if(!segmentAlreadyCompleted) {

                            rs = stmt.executeQuery("select id from segments where name=\""+segmentNames[j]+"\"");

                            if(rs.next()) {
                                segmentID = rs.getInt("id");
                            }

                            stmt.executeUpdate("Insert into CyclistsToSegments (teamId, cyclistID, segmentID, time) "
                                    + "VALUES("+session.getAttribute("teamID")+","+session.getAttribute("userID")+","+segmentID+","+times.get(i)+")");
                        }
                        //inserts a completed segment if none exists

                        segmentAlreadyCompleted = false;
                        break;

                    }
                }
            }
        }catch(Exception e) {
            System.out.println("Exception "+e+" in StravaData.checkAcquiredSegments");
        }


    }

    private static int countRows(ResultSet rs) {

        int rows = 0;

        try {
            rs.last();
            rows = rs.getRow();
            rs.beforeFirst();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }


}
