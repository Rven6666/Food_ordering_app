package lab24.ankit.group01.a2;

import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Login implements LogObserverable, AppState {
    
    private User user;
    private final LogObserver logObserver = new SystemLog();
    private final String filePath = "src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";

    public void displayLoginScreen() {
        System.out.println("Welcome to the Scroll Management System");
        System.out.println("1. Login\n2. Continue as Guest\n3. Exit");
        System.out.println("Please select an option");
        int choice = Scan.scanInteger(1, 4);
        switch (choice) {
            case 1:
                if (!attemptUserLogin())
                    displayLoginScreen();
                break;
            case 2:
                this.user = null;
                System.out.println("Welcome, guest!");
                notifyObserver("Guest entered the system");
                AppStats.incrementCount("guest logins");
                break;
            case 3:
                System.out.println("Thanks for stopping by!");
                System.exit(0);
        }
    }
    
    public boolean attemptUserLogin() {

        while (true) {

            // getting username and password
            System.out.print("Enter your username: ");
            String username = Scan.scanString(null);
            System.out.print("Enter your password: ");
            String password = Scan.scanString(null);

            // checking if login credentials are valid
            if (checkUserLogin(username, password)) {
                System.out.println("Login successful");
                notifyObserver("User " + username + " logged in");
                AppStats.incrementCount("user logins");
                return true;
            } else {
                // seeing if they would like to attempt to try login again
                System.out.println("Invalid username or password");
                System.out.println("Would you like to try again? (y/n)");
                String choice = Scan.scanString(new ArrayList<String>(Arrays.asList("y", "n")));
                if (choice.equals("n"))
                    return false;
            }
        }
    }

    public boolean checkUserLogin(String username, String password) {
        JSONParser parser = new JSONParser();
        JSONObject userFile = null;

        try {
            userFile = (JSONObject) parser.parse(new FileReader(this.filePath));
        } catch (Exception e) {
            System.out.println("Cannot find UserList.json");
            e.printStackTrace();
            System.exit(1);
        }
        try{
        JSONArray users = (JSONArray) userFile.get("users");

        for (Object user : users) {
            JSONObject userObject = (JSONObject) user;
            if (userObject.get("username").equals(username) && 
            PassEncrypt.checkPassword(password, userObject.get("password").toString())) {
                this.user = new User(userObject);
                return true;
            }
        }
        }catch(Exception e){
            System.out.println(e);
        }

        return false;

    }

    public User getUser() {
        return user;
    }

    @Override
    public void notifyObserver(String message) {
        logObserver.updateLog("Login", message);
    }

}
