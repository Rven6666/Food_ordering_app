package lab24.ankit.group01.a2;

import lab24.ankit.group01.a2.Scrolls.ScrollManager;
import lab24.ankit.group01.a2.Scrolls.ScrollSeeker;

public class Menu implements AppState {

    private final LogObserver logObserver = new SystemLog();
    private final User user;
    private final String name;
    
    private State nextState;
    private final ScrollManager manager = new ScrollManager();
    private final ScrollSeeker seeker = new ScrollSeeker();

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
            System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Remove Scrolls\n" +
                            "5. Receive Scrolls\n6. Search Scrolls\n7. Update Profile Details\n8. Exit");
            selection = Scan.scanInteger(1, 8);
            if (selection == 8) exit();
        } else {
            // getting choice from admin
            System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scroll\n4. Remove Scroll\n"
                            +"5. Receive Scrolls\n6. Search Scrolls\n7. Update Profile Details\n"
                            +"8. Create New Users\n9. View Users\n10. Delete Users\n"
                            +"11. View app stats\n12. Exit");
            selection = Scan.scanInteger(1, 12);
            if (selection == 12) exit();
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
            case 2: // Add scrolls (only for registered user)
                nextState = State.UPLOAD;
                break;
            case 3: //Edit Scrolls (only for registered user)
               manager.editScroll(user);
                break;
            case 4: // Remove Scroll (only for registered user)
                manager.removeScroll(user);
                break;
            case 5: //receive Scrolls (download)
                seeker.downloadScroll(user);
                break;
            case 6://Search Scrolls
                seeker.searchFilter();
                break;
            case 7:
                nextState = State.UPDATEDETAILS;
                break;
            case 8:
                UserManager userManager = new UserManager();
                userManager.createUser();
                break;
            case 9:
                UserManager userManager2 = new UserManager();
                userManager2.displayUserList();
                break;
            case 10:
                UserManager userManager3 = new UserManager();
                userManager3.removeUser();
                break;
            case 11:
                System.out.println("CALL CLASS - To be done - VIEW app stats");
                break;
        }
     }

     @Override
     public State getNextState() {
         return nextState;
     }
    
}