package lab24.ankit.group01.a2;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONChecker {

    public static boolean checkUserLogin(String username, String password) {
        
        JSONParser parser = new JSONParser();
        JSONObject userFile = null;

        try {
            userFile = (JSONObject) parser.parse(new FileReader("userList.json"));
        } catch (Exception e) {
            System.out.println("Cannot find userList.json");
            System.exit(1);
        }

        JSONArray users = (JSONArray) userFile.get("users");

        for (Object user : users) {
            JSONObject userObject = (JSONObject) user;
            if (userObject.get("username").equals(username) && userObject.get("password").equals(password)) {
                return true;
            }
        }

    }
    
}
