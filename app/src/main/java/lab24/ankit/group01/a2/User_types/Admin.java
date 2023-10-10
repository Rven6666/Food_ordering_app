package lab24.ankit.group01.a2.User_types;
public class Admin extends UsersParent{

    @Override
    public void setID(){
        this.id = userManager.getUserCount() + 1;
    }

    @Override
    public int getID(){
        return this.id;
    } 

    @Override
    public void setName(){
        this.name = "Admin";
    }

    @Override
    public void setViewEditAccess(){
        this.viewEditAccess = true;
    } 

}