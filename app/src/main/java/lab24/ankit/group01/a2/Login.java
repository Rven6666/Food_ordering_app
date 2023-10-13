package lab24.ankit.group01.a2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;

import lab24.ankit.group01.a2.User_types.User;
import lab24.ankit.group01.a2.User_types.Admin;
import lab24.ankit.group01.a2.User_types.Member;

public class Login {
    
    private User user;

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
            userFile = (JSONObject) parser.parse(new FileReader("src/main/java/lab24/ankit/group01/a2/Databases/UserList.json"));
        } catch (Exception e) {
            System.out.println("Cannot find UserList.json");
            System.exit(1);
        }

        JSONArray users = (JSONArray) userFile.get("users");

        for (Object user : users) {
            JSONObject userObject = (JSONObject) user;
            if (userObject.get("username").equals(username) && 
            PassEncrypt.checkPassword(password, userObject.get("password").toString())) {
                if (userObject.get("type").equals("member"))
                    this.user = new Member(userObject);
                else
                    this.user = new Admin(userObject);
                return true;
            }
        }

        return false;

    }

    public User getUser() {
        return user;
    }

}
