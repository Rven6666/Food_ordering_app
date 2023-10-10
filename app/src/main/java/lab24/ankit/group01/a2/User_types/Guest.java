package lab24.ankit.group01.a2;
public class Guest extends User{

    @Overide
    public void setID(){
        //set with database number.
        //increment
    }

    @Overide
    public int getID(){
        return this.id;
    } 

    @Overide
    public void setName(){
        this.name = "Guest"
    }

    @Overide
    public void setViewEditAccess(){
        this.viewEditAccess = False;
    } 
}