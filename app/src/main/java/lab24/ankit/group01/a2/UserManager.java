package lab24.ankit.group01.a2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class UserManager{
    // attributes for UserManager class
    private final static String filePath = "src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";
    private final static JSONParser parser = new JSONParser();

    public static void createUser(){

        File file = new File(filePath);

        if (!file.exists()){
            // File does not exist, not considered empty
            System.out.println("File does not exist.");
            return;
        }

        // create name (probably would need to enforce check)
        System.out.print("Please enter your full name: ");
        String full_name = Scan.scanString(null);

        // please enter your email (would also need to enforce check)
        System.out.print("Please enter your email: ");
        String email = Scan.scanString(null);

        // please enter your phone number (also need to enforce a check)
        System.out.print("Please enter your phone number: ");
        String phone_number = Scan.scanString(null);

        System.out.print("Please enter a username: ");
        String username = Scan.scanString(null);

        // hash password with hasing algorithm
        System.out.print("Please enter a password: ");
        String password = PassEncrypt.hashPassword(Scan.scanString(null));

        // choose user privilege/type
        System.out.print("Please choose privilege (admin/member): ");
        String privilege = Scan.scanString(new ArrayList<String>(Arrays.asList("admin", "member")));

        // create new user json object
        JSONObject user = new JSONObject();
        user.put("id", getNextID());
        user.put("name", full_name);
        user.put("email", email);
        user.put("phone number", phone_number);
        user.put("type", privilege);
        user.put("password", password);
        user.put("username", username);

        JSONObject obj = null;

        try {
            // getting current jsonobject in file
            obj = (JSONObject) parser.parse(new FileReader(filePath));
            ((JSONArray)obj.get("users")).add(user);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        // write to the UserList
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(obj.toJSONString());
            fileWriter.close();
        } catch (IOException e){
            // print the stacktrace
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Successfully created new user!");

    }

    /**
     * remove a user from UserList
     */
    public static void removeUser(){

        System.out.println("Displaying User Information...\n");
        displayUserList();
        System.out.print("Please choose an ID to remove: ");
        JSONObject removed_user = (JSONObject) getUserList().get(Scan.scanInteger(1, getUserList().size())-1);

        try {
            // getting current jsonobject in file
            JSONObject obj = (JSONObject) parser.parse(new FileReader(filePath));
            ((JSONArray)obj.get("users")).remove(removed_user);

            // write to the UserList
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(obj.toJSONString());
            fileWriter.close();

            // output successful user removal message
            System.out.println("\nUser removed successfully:");
            displayUserInfo(removed_user);

        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * update a user from UserList
     */
    public void updateUser(){
        System.out.println("Displaying User Information...\n");
        displayUserList();
        System.out.print("Please choose an ID to modify: ");
        int idx = Scan.scanInteger(1, getUserList().size())-1;
        JSONObject user = (JSONObject) getUserList().get(idx);

        // output successful retrieval message
        System.out.println("\nRetrieved user information successfully:");
        displayUserInfo(user);

        // select field to modify
        Set<String> setKeys = user.keySet();
        String field = "";

        // keep prompting user until he/she give valid option
        while(!new ArrayList<>(setKeys).contains(field)){
            System.out.println("\nSelect a field to modify...");
            for(String key : setKeys){
                // print the keys
                System.out.println(key);
            }
            System.out.print("Field: ");
            field = Scan.scanString(new ArrayList<>(setKeys));
        }

        // update the existing field with new value
        System.out.printf("Please enter a new value for %s: ",field);
        String updated_value = Scan.scanString(null);
        if (field.equals("password")){
            // encrypt new password
            updated_value = PassEncrypt.hashPassword(updated_value);
        }
        user.put(field, updated_value);

        // write to file
        try {
            // getting current jsonobject in file
            JSONObject obj = (JSONObject) parser.parse(new FileReader(filePath));
            ((JSONArray)obj.get("users")).set(idx, user);

            // write to the UserList
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(obj.toJSONString());
            fileWriter.close();

            // output successful user edit message
            System.out.println("\nUser information edited successfully:");
            displayUserInfo(user);

        }
        catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static String getNextID() {
        JSONObject obj = null;

        try {
            // getting current jsonobject in file
            obj = (JSONObject) parser.parse(new FileReader(filePath));
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        int max = 0;
        for (Object user : (JSONArray) obj.get("users")){
            JSONObject userObject = (JSONObject) user;
            int id = Integer.parseInt(userObject.get("id").toString());
            if (id > max)
                max = id;
        }
        return Integer.toString(max + 1);
    }

    /**
     * display users from UserList
     */
    public static void displayUserList(){
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            JSONArray users = (JSONArray) jsonObject.get("users");
            for(int i = 0; i < users.size(); i++){
                JSONObject user = (JSONObject) users.get(i);
                displayUserInfo(user);
                System.out.println();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * display associated user info
     * @param user, passed in as param
     */
    public static void displayUserInfo(JSONObject user){
        Set<String> setKeys = user.keySet();

        for(String key : setKeys){
            // display user info
            if (key.equals("password")) continue;
            System.out.printf("%s: %s\n", key, user.get(key));
        }
    }

    /**
     * get user list from the UserList database
     * @return, a JSONArray containing user information
     */
    public static JSONArray getUserList(){
        JSONArray users = null;
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
            users = (JSONArray) jsonObject.get("users");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public HashMap<Integer, Object> getUsers() {
        return null;
    }

}