package lab24.ankit.group01.a2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UpdateDetails implements AppState {

    private User user;
    private final String filePath = "src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";
    
    public UpdateDetails(User user) {
        this.user = user;
    }

    public String getDetailToUpdate() {
        // list of all the choices
        String[] choices = {"Username", "Email", "Password", "Address", "Phone Number", "Name"};
        
        // getting user choice
        System.out.println("What detail would you like to update?");
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ". " + choices[i]);
        }
        System.out.print("Enter your choice: ");
        int choice = Scan.scanInteger(1, choices.length);

        // returning the detail to update
        return choices[choice - 1].toLowerCase();
    }

    public String getNewValue(String detail) {
        System.out.println("Please enter a new " + detail + ": ");
        String newValue = Scan.scanString(null);
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

}
