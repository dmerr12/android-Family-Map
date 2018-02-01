package example.com.familymap;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.google.gson.*;

import model.Event;
import model.Person;
import request.LoginRequest;
import request.RegisterRequest;
import result.AllEventResponse;
import result.AllPersonResponse;
import result.LoginResponse;
import result.RegisterResponse;

public class Client {


    public static void main(String[] args) {

        String serverHost = args[0];
        System.out.println(serverHost);
        String serverPort = args[1];
        LoginRequest t = new LoginRequest();
        t.setUserName("susan");
        t.setPassword("mysecret");
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("susy");
        registerRequest.setPassword("pass");
        registerRequest.setFirstName("sus");
        registerRequest.setLastName("y");
        registerRequest.setEmail("banana");
        registerRequest.setGender("f");
        //register(serverHost,serverPort,registerRequest);
        String auth = "3ASQGJRIAH";
        ArrayList<Event> events = getEvents(serverHost,serverPort,auth);
        ArrayList<Person> persons = getPersons(serverHost, serverPort,auth);
        System.out.println(events.get(0).getDescendant());
        System.out.println(persons.get(0).getDescendant());
    }


    public static ArrayList<Person> getPersons(String serverHost, String serverPort, String authToken) {
        AllPersonResponse r= new AllPersonResponse();
        // This method shows how to send a GET request to a server

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("GET");
            // Indicate that this request will not contain an HTTP request body
            http.setDoOutput(false);

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", authToken);

            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                Gson gson = new Gson();
                r=gson.fromJson(respData,AllPersonResponse.class);
                System.out.println(respData);
                return r.getData();
                // Display the JSON data returned from the server

            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return r.getData();
    }

    public static ArrayList<Event> getEvents(String serverHost, String serverPort, String authToken) {
        AllEventResponse r= new AllEventResponse();
        // This method shows how to send a GET request to a server

        try {
            // Create a URL indicating where the server is running, and which
            // web API operation we want to call
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/event");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP GET request
            http.setRequestMethod("GET");
            // Indicate that this request will not contain an HTTP request body
            http.setDoOutput(false);

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", authToken);

            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                Gson gson = new Gson();
                r=gson.fromJson(respData,AllEventResponse.class);
                //System.out.println(respData);
                return r.getData();
                // Display the JSON data returned from the server

            }
            else {

                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
        return r.getData();
    }



      public static String logIn(String serverHost, String serverPort, LoginRequest r) {
          String s="";
        // This method shows how to send a POST request to a server

        try {

            System.out.println("I get here!");
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();


            http.setRequestMethod("POST");

            http.setDoOutput(true);	// There is a request body


            http.connect();
            Gson gson = new Gson();
            String reqData = gson.toJson(r);


            OutputStream reqBody = http.getOutputStream();
            // Write the JSON data to the request body
            writeString(reqData, reqBody);
            // Close the request body output stream, indicating that the
            // request is complete
            reqBody.close();


            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                LoginResponse f = gson.fromJson(respData,LoginResponse.class);
                System.out.println(respData);
                if(f.getMessage()==null){
                    s=f.getAuthToken();
                }



            }
            else {

                System.out.println("ERROR: " + http.getResponseMessage());

            }
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        return s;
    }

    public static String register(String serverHost, String serverPort, RegisterRequest r) {
        String s = "";


        try {

            System.out.println("I get here!");
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

            // Start constructing our HTTP request
            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            // Specify that we are sending an HTTP POST request
            http.setRequestMethod("POST");
            // Indicate that this request will contain an HTTP request body
            http.setDoOutput(true);	// There is a request body


            http.connect();
            Gson gson = new Gson();
            String reqData = gson.toJson(r);


            OutputStream reqBody = http.getOutputStream();

            writeString(reqData, reqBody);

            reqBody.close();


            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();

                String respData = readString(respBody);
                System.out.println(respData);
                RegisterResponse f = gson.fromJson(respData,RegisterResponse.class);
                if(f.getMessage()==null){
                    s=f.getAuthToken();
                }


            }
            else {

                System.out.println("ERROR: " + http.getResponseMessage());

            }
        }
        catch (IOException e) {

            e.printStackTrace();
        }
        return s;
    }


    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }


    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}