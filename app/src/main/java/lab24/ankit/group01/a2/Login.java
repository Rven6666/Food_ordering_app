package lab24.ankit.group01.a2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

import java.util.ArrayList;
import java.util.Arrays;

import lab24.ankit.group01.a2.User_types.Guest;
import lab24.ankit.group01.a2.User_types.UsersParent;

public class Login {
    
    private UsersParent user;

    public void displayLoginScreen() {
        System.out.println("Welcome to the Scroll Management System");
        System.out.println("1. Member Login\n2. Admin Login\n3. Continue as Guest\n4. Exit");
        System.out.println("Please select an option");
        int choice = Scan.scanInteger(1, 4);
        switch (choice) {
            case 1:
                attemptUserLogin("member");
                break;
            case 2:
                attemptUserLogin("admin");
                break;
            case 3:
                user = new Guest();
                break;
            case 4:
                System.exit(0);
                break;
        }
    }
    
    public void attemptUserLogin(String type) {

        boolean attemptLogin = true;

        while (attemptLogin) {

            // getting username and password
            System.out.print("Enter your username: ");
            String username = Scan.scanString(null);
            System.out.print("Enter your password: ");
            String password = Scan.scanString(null);

            // checking if login credentials are valid
            if (checkUserLogin(username, password, type)) {
                System.out.println("Login successful");
                break;
            } else {
                // seeing if they would like to attempt to try login again
                System.out.println("Invalid username or password");
                System.out.println("Would you like to try again? (y/n)");
                String choice = Scan.scanString(new ArrayList<String>(Arrays.asList("y", "n")));
                if (choice.equals("n"))
                    attemptLogin = false;
                else
                    break;
            }
        }
        
    }

    public boolean checkUserLogin(String username, String password, String type) {
        JSONParser parser = new JSONParser();
        JSONObject userFile = null;

        try {
            userFile = (JSONObject) parser.parse(new FileReader("src/main/java/lab24/ankit/group01/a2/Databases/UserList.json"));
        } catch (Exception e) {
            System.out.println("Cannot find UserList.json");
            System.exit(1);
        }

        JSONArray users = (JSONArray) userFile.get(type);

        for (Object user : users) {
            JSONObject userObject = (JSONObject) user;
            if (userObject.get("username").equals(username) && userObject.get("password").equals(password)) {
                return true;
            }
        }

        return false;

    }

    public UsersParent getUser() {
        return user;
    }

}
