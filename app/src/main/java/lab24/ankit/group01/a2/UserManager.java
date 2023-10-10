package lab24.ankit.group01.a2;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserManager{

    private String userData;
    private HashMap<Integer, Object> userList;
    private Scanner scan;

    public UserManager(){

        this.scan = new Scanner(System.in);
    }

    public void createUser(){

    }

    public void removeUser(){
        
    }

    public void updateUser(){
        
    }

    public int getUserCount(){
        return this.userList.size();
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

}