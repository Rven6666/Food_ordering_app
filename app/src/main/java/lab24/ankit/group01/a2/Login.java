package lab24.ankit.group01.a2;

import java.io.*;
import java.util.*;
import lab24.ankit.group01.a2.User_types.Admin;
import lab24.ankit.group01.a2.User_types.Member;
import lab24.ankit.group01.a2.User_types.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Login implements LogObserverable {
    
    private User user;
    private LogObserver logObserver;

    public Login() {
        this.logObserver = new SystemLog();
    }
    private Scanner scan = new Scanner(System.in);
    private String filePath = "src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";
    private PassEncrypt encryptedPassword;


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
                if (userObject.get("type").equals("member"))
                    this.user = new Member(userObject);
                else
                    this.user = new Admin(userObject);
                return true;
            }
        }
        }catch(Exception e){
            System.out.println(e);
        }

        return false;

    }

    public void changeLoginDetails(String currentUsername, String detailType){
        String newChoice;
        String response = "";
        while (true){
            System.out.println("Please choose a "+ detailType+" between 4 and 12 characters: ");
            newChoice = scan.nextLine().strip();
            if(newChoice.length() >= 4 && newChoice.length() <= 12 ){
                System.out.println("You entered: " + newChoice + "\nPlease confirm if this is correct? (y/n)");
                response = scan.nextLine().toLowerCase();
                if(response.equals("y")) {
                    updateLoginInfo(currentUsername, newChoice, detailType);
                    break;
            } 
            else {
                    System.out.println("Invalid entry.");
                }
            }
        }

    }

    public void updateLoginInfo(String currentUser, String updatedDetails, String detailType){
        JSONParser parser = new JSONParser();
        JSONObject usersObject = null;

        try {
            
            FileReader reader = new FileReader(this.filePath);
            usersObject = (JSONObject) parser.parse(reader);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        JSONArray users = (JSONArray) usersObject.get("users");
        for (Object user : users) {
            JSONObject jsonOb = (JSONObject) user;
            if (jsonOb.get("username").equals(currentUser)){
                if(detailType.equals("username")){
                    jsonOb.put("username", updatedDetails);
                }else if(detailType.equals("password")){
                    jsonOb.put("password", encryptedPassword.hashPassword(updatedDetails));
                }
            }
        }
        try{
            FileWriter fileWriter = new FileWriter(this.filePath);
            fileWriter.write(usersObject.toJSONString());
            fileWriter.close();
            System.out.println("Password has been updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public User getUser() {
        return user;
    }

    @Override
    public void notifyObserver(String message) {
        logObserver.updateLog("Login", message);
    }

}
