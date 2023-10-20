package lab24.ankit.group01.a2;

public class Menu implements AppState {

    private final LogObserver logObserver = new SystemLog();
    private final User user;
    private final String name;
    
    private State nextState;

    public Menu(User user) {
        this.user = user;
        if (user != null)
            this.name = user.getUsername();
        else
            this.name = "Guest";
    }

    public void menuIntro(){
        System.out.println("Hello "+ name +". I am VSAS, the Virtual Scroll Access System.\n"
                            +"The portal to the realm of digital wisdom!\n"
                            +"I await your selection:");
    }

    public void exit() {
        System.out.println("Good luck anonymous member of the Whiskers!");
        System.exit(0);
    }

    public int showMenu(){
        //Menu display
        int selection;
        if(user == null){
            // getting choice from guest
            System.out.println("1. View Scrolls\n2. Exit");
            selection = Scan.scanInteger(1, 2);
            if (selection == 2) exit();
        } else if(!user.isAdmin()){
            // getting choice from user
            System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n" +
                            "5. Search Scrolls\n6. Update Profile Details\n7. Exit");
            selection = Scan.scanInteger(1, 8);
            if (selection == 7) exit();
        } else {
            // getting choice from admin
            System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n"
                            +"4. Recieve Scrolls\n5. Search Scrolls\n6. Update Profile Details\n"
                            +"7. Create New Users\n8. View Users\n9. Delete Users\n"
                            +"10. View app stats\n11. Exit");
            selection = Scan.scanInteger(1, 11);
            if (selection == 11) exit();
        }
        return selection;
    }

    public void menuSelection(int selection){

        // default is return to menu
        nextState = State.MENU;

        switch (selection){
            case 1: //preview/view scrolls
                nextState = State.VIEWSCROLL;
                break;
            case 2: // Add scrolls
                nextState = State.UPLOAD;
                break;
            case 3: //Edit Scrolls
                System.out.println("CALL CLASS - To be done - EDIT scrolls");
                break;
            case 4: //recieve Scrolls (download)
                System.out.println("CALL CLASS - To be done - DOWNLOAD scrolls");
                break;
            case 5://Search Scrolls
                System.out.println("CALL CLASS - To be done - SEARCH scrolls");
                break;
            case 6:
                nextState = State.UPDATEDETAILS;
                break;
            case 7:
                UserManager userManager = new UserManager();
                userManager.createUser();
                break;
            case 8:
                UserManager userManager2 = new UserManager();
                userManager2.displayUserList();
                break;
            case 9:
                UserManager userManager3 = new UserManager();
                userManager3.removeUser();
                break;
            case 10:
                System.out.println("CALL CLASS - To be done - VIEW app stats");
                break;
        }
     }

     @Override
     public State getNextState() {
         return nextState;
     }
    
}