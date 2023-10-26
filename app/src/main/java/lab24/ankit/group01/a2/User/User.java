package lab24.ankit.group01.a2.User;

import org.json.simple.JSONObject;


public class User {

    private final String id;
    private final String user_id;
    private final String name;
    private final String phoneNum;
    private final String email;
    private final String username;
    private final String encryptedPassword;
    private final String type;

    public User(JSONObject user){
        this.id = user.get("id").toString();
        this.name = user.get("name").toString();
        this.phoneNum = user.get("phone number").toString();
        this.email = user.get("email").toString();
        this.username = user.get("username").toString();
        this.encryptedPassword = user.get("password").toString();
        this.type = user.get("type").toString();
        this.user_id = user.get("user id").toString();
    }

    public String getUserID() {
        return this.user_id;
    }

    public boolean isAdmin() {
        return this.type.equals("admin");
    }

    public String getUsername(){
        return this.username;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhoneNumber(){
        return this.phoneNum;
    }
    public String getPassword(){
       return this.encryptedPassword;
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.id;
    }

    public void showUser(){
        System.out.println("\nUser: " + this.username+"\nID: " + this.id);
    }

}