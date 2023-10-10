package lab24.ankit.group01.a2;
public abstract class UsersParent{

    protected int id;
    protected String name;
    protected int phoneNum;
    protected String email;
    protected String username;
    protected String encryptedPassword;
    protected Boolean viewEditAccess;
    protected ScrollManager scrollManager;
    private UserManager userManager;
    private PassEncript encriptPassword;

    public UsersParent(){
        this.userManager = new UserManager;
        this.encriptPassword = new PassEncript;
    }

    protected abstract void setID(){
    }

    protected abstract int getID(){
    }

    protected abstract void setName(){
    }

    protected abstract void setViewEditAccess(){
    }

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
        this.encryptedPassword = this.encriptPassword.hashedPassword(rawPassword);
    }

    protected String getPassword(){
       return this.encryptedPassword;
    }

}