package lab24.ankit.group01.a2.User_types;
public class Guest extends UsersParent{

    @Override
    public void setID(){
        //set with database number.
        //increment
    }

    @Override
    public int getID(){
        return this.id;
    } 

    @Override
    public void setName(){
        this.name = "Guest";
    }

    @Override
    public void setViewEditAccess(){
        this.viewEditAccess = false;
    } 
}