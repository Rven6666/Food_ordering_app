package lab24.ankit.group01.a2.User_types;

import lab24.ankit.group01.a2.PassEncrypt;
import lab24.ankit.group01.a2.ScrollManager;
import lab24.ankit.group01.a2.UserManager;

import org.json.simple.JSONObject;

public abstract class User {

    protected int id;
    protected String name;
    protected String phoneNum;
    protected String email;
    protected String username;
    protected String encryptedPassword;
    protected ScrollManager scrollManager;
    protected UserManager userManager;

    public User(JSONObject user){
        this.id = Integer.parseInt(user.get("id").toString());
        this.name = user.get("full_name").toString();
        this.phoneNum = user.get("phone_number").toString();
        this.email = user.get("email").toString();
        this.username = user.get("username").toString();
        this.encryptedPassword = user.get("password").toString();


        this.userManager = new UserManager();
    }

    public abstract boolean isAdmin();

    protected void setEmail(String newEmail){
        this.email = newEmail;
    }
    protected void setPhoneNumber(String newPhoneNum){
        this.phoneNum = newPhoneNum;
    }
    protected void setPassword(String rawPassword){
        this.encryptedPassword = PassEncrypt.hashPassword(rawPassword);
    }

    protected String getEmail(){
        return this.email;
    }
    protected String getPhoneNumber(){
        return this.phoneNum;
    }
    protected String getPassword(){
       return this.encryptedPassword;
    }

}