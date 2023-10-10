package lab24.ankit.group01.a2;
public class Admin extends User{

    @Overide
    public void setID(){
        this.id = this.userManager.getUserCount() ++1;
    }

    @Overide
    public int getID(){
        return this.id;
    } 

    @Overide
    public void setName(){
        this.name = "Admin"
    }

    @Overide
    public void setViewEditAccess(){
        this.viewEditAccess = True;
    } 

}