package lab24.ankit.group01.a2.Miscellaneous;

import lab24.ankit.group01.a2.Logging.LogObserver;
import lab24.ankit.group01.a2.Logging.LogObserverable;
import lab24.ankit.group01.a2.Logging.SystemLog;
import lab24.ankit.group01.a2.Miscellaneous.Scan;
import lab24.ankit.group01.a2.Security.PassEncrypt;
import lab24.ankit.group01.a2.UI.AppState;
import lab24.ankit.group01.a2.User.User;
import lab24.ankit.group01.a2.User.UserManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import lab24.ankit.group01.a2.Scrolls.ScrollManager;

public class UpdateDetails implements AppState, LogObserverable {

    private User user;
    private final String filePath = "src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";
    private final LogObserver systemLog = new SystemLog();
    
    public UpdateDetails(User user) {
        this.user = user;
    }

    public String getDetailToUpdate() {
        // list of all the choices
        String[] choices = {"Username", "Email", "Password", "Address", "Phone Number", "Name", "User ID"};
        
        // getting user choice
        System.out.println("\nWhat detail would you like to update?");
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ". " + choices[i]);
        }
        System.out.print("Enter your choice: ");
        int choice = Scan.scanInteger(1, choices.length);

        // returning the detail to update
        return choices[choice - 1].toLowerCase();
    }

    public String getNewValue(String detail) {
        
        String newValue = null;

        // check case for user id because this has to be unique
        if (detail.equals("user id")) {
            System.out.println("Please enter a new " + detail + " between 0 and 100,000: ");
            int newID = Scan.scanInteger(0, 100000);
            while (true) {
                if (UserManager.checkUserIDAvailable(newID)) break;
                System.out.println("ID already taken, please choose another one.");
                newID = Scan.scanInteger(0, 100000);
            }
            newValue = Integer.toString(newID);
        }
        else {
            System.out.println("Please enter a new " + detail + ": ");
            newValue = Scan.scanString(null);
        }

        // confirming the new value
        System.out.println("You entered: " + newValue + "\nPlease confirm if this is correct? (y/n)");
        String response = Scan.scanString(new ArrayList<String>(Arrays.asList("y", "n")));
        if (response.equals("n")) {
            return getNewValue(detail);
        }
        return newValue;
    }

    public void updateDetail(String detail, String newValue) {
        JSONParser parser = new JSONParser();
        JSONObject usersObject = null;

        try {
            FileReader reader = new FileReader(filePath);
            usersObject = (JSONObject) parser.parse(reader);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // updating the detail in the database
        JSONArray users = (JSONArray) usersObject.get("users");
        for (Object user : users) {
            JSONObject jsonOb = (JSONObject) user;
            if (jsonOb.get("id").equals(this.user.getID())) {
                if (detail.equals("password"))
                    jsonOb.put(detail, PassEncrypt.hashPassword(newValue));
                else
                    jsonOb.put(detail, newValue);

                if (detail.equals("user id")) {
                    ScrollManager.updateUserScrollIDs(this.user.getID(), newValue);
                }
                
                notifyObserver("User with id " + this.user.getID() + " updated their " + detail);

                // updating the user object
                this.user = new User(jsonOb);

                break;
            }
        }

        try{
            FileWriter fileWriter = new FileWriter(this.filePath);
            fileWriter.write(usersObject.toJSONString());
            fileWriter.close();
            System.out.println("Your " + detail + " has been updated.");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public User getNewUser() {
        return user;
    }

    @Override
    public void notifyObserver(String message) {
        systemLog.updateLog("Update Details", message);
    }

}
