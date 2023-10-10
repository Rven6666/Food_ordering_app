package lab24.ankit.group01.a2.User_types;

import lab24.ankit.group01.a2.PassEncrypt;
import lab24.ankit.group01.a2.ScrollManager;
import lab24.ankit.group01.a2.UserManager;

public abstract class UsersParent{

    protected int id;
    protected String name;
    protected int phoneNum;
    protected String email;
    protected String username;
    protected String encryptedPassword;
    protected Boolean viewEditAccess;
    protected ScrollManager scrollManager;
    protected UserManager userManager;
    protected PassEncrypt encriptPassword;

    public UsersParent(){
        this.userManager = new UserManager();
        this.encriptPassword = new PassEncrypt();
    }

    protected abstract void setID();

    protected abstract int getID();

    protected abstract void setName();

    protected abstract void setViewEditAccess();

    protected void setPhoneNumber(int newPhoneNum){
        this.phoneNum = newPhoneNum;
    }

    protected int getPhoneNumber(){
        return this.phoneNum;
    }

    protected void setEmail(String newEmail){
        this.email = newEmail;
    }

    protected String getEmail(){
        return this.email;
    }

    protected void setPassword(String rawPassword){
        this.encryptedPassword = PassEncrypt.hashPassword(rawPassword);
    }

    protected String getPassword(){
       return this.encryptedPassword;
    }

}