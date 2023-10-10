package lab24.ankit.group01.a2.User_types;
import lab24.ankit.group01.a2.User_types.UsersParent;

public class Member extends UsersParent {

    @Override
    public void setID(){
        this.id = this.userManager.getUserCount() + 1;
    }

    @Override
    public int getID(){
        return this.id;
    } 

    @Override
    public void setName(){
        this.name = "User";
    }

    @Override
    public void setViewEditAccess(){
        this.viewEditAccess = true;
    } 
}