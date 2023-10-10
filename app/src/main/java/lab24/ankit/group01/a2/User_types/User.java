package lab24.ankit.group01.a2.User_types;

import lab24.ankit.group01.a2.PassEncrypt;
import lab24.ankit.group01.a2.ScrollManager;
import lab24.ankit.group01.a2.UserManager;

public abstract class User {

    protected int id;
    protected String name;
    protected int phoneNum;
    protected String email;
    protected String username;
    protected String encryptedPassword;
    protected Boolean viewEditAccess;
    protected ScrollManager scrollManager;
    protected UserManager userManager;

    public User(){
        this.userManager = new UserManager();
    }

    protected void setEmail(String newEmail){
        this.email = newEmail;
    }
    protected void setPhoneNumber(int newPhoneNum){
        this.phoneNum = newPhoneNum;
    }
    protected void setPassword(String rawPassword){
        this.encryptedPassword = PassEncrypt.hashPassword(rawPassword);
    }

    protected String getEmail(){
        return this.email;
    }
    protected int getPhoneNumber(){
        return this.phoneNum;
    }
    protected String getPassword(){
       return this.encryptedPassword;
    }

}