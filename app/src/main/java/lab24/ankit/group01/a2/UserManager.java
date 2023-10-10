package lab24.ankit.group01.a2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UserManager{
    // attributes for UserManager class
    private final String filePath = "/Users/obie/Desktop/SOFT2412/Assignments/A2/Lab24-Ankit-Group01-A2/app/src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";
    private JSONParser parser;
    private int id;

    public UserManager(){
        // init parser & id
        parser = new JSONParser();
        id = 1;
    }

    public void createUser(){

        File file = new File(filePath);

        if (!file.exists()){
            // File does not exist, not considered empty
            System.out.println("File does not exist.");
            return;
        }

        // create name (probably would need to enforce check)
        System.out.print("Please enter your full name: ");
        String full_name = Scan.scanString();

        // please enter your email (would also need to enforce check)
        System.out.print("Please enter your email: ");
        String email = Scan.scanString();

        // please enter your phone number (also need to enforce a check)
        System.out.print("Please enter your phone number: ");
        String phone_number = Scan.scanString();

        System.out.print("Please enter a username: ");
        String username = Scan.scanString();

        // hash password with hasing algorithm
        System.out.print("Please enter a password: ");
        String password = PassEncrypt.hashPassword(Scan.scanString());

        // choose user privilege/type
        System.out.print("Please choose privilege (Admin/Member/Guest): ");
        String privilege = Scan.scanString().toLowerCase();
        while(!privilege.equals("admin") && !privilege.equals("member") && !privilege.equals("guest")){
            // keep prompting user until they give right input
            privilege = Scan.scanString().toLowerCase();
        }

        // create new user json object
        JSONObject user = new JSONObject();
        JSONObject user_content = new JSONObject();
        JSONObject profile = new JSONObject();
        profile.put("full_name", full_name);
        profile.put("email", email);
        profile.put("phone_number", phone_number);
        user_content.put("profile", profile);
        user_content.put("type", privilege);
        user_content.put("password", password);
        user_content.put("username", username);
        // increment id by 1
        id++;

        if (file.length() != 0){
            // set user as the existing json content
            try {
                Object obj = parser.parse(new FileReader(filePath));
                user = (JSONObject) obj;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        try {
            user.put(id, user_content);
            // write to the UserList
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(user.toJSONString());
            fileWriter.close();
        } catch (IOException e){
            // print the stacktrace
            e.printStackTrace();
        }
    }

    public void removeUser(){
        System.out.println("Displaying User Information...");
        // Read JSON data from a file
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));

            for(Object key : jsonObject.keySet()){
                JSONObject user = (JSONObject) jsonObject.get(key);
                System.out.println("Full Name: " + user.get("profile.full_name"));
                System.out.println("Email: " + user.get("profile.email"));
            }

        } catch (Exception e){
            e.printStackTrace();
        }




    }

    public void updateUser(){
        
    }
 
    public void loginSystem() {
        // String username = scan.nextLine();
        // String password = scan.nextLine();
        // scan.close();
        // JSONParser parser = new JSONParser();
        // try {
        //     Object file = parser.parse(new FileReader(userList));
        //     JSONObject fileob = (JSONObject) file;
        // } catch (Exception e) {
        //     System.out.println("File not found login");
        //     System.exit(0);
        // }
    }

    public static void main(String[] args) {
        UserManager test = new UserManager();
//        test.createUser();
        test.removeUser();
    }

}