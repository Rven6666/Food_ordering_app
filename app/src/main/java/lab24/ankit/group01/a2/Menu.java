package lab24.ankit.group01.a2;

import lab24.ankit.group01.a2.User_types.User;


public class Menu implements LogObserverable {

    private LogObserver logObserver;
    private FileUploader uploader = new FileUploader();

    public Menu() {
        this.logObserver = new SystemLog();
    }

    public void menuIntro(User user){
        String name = "Guest";
        if (user != null){
            name = user.getName();
        }
        System.out.println("Hello "+ name +". I am VSAS, the Virtual Scroll Access System.\n"
                            +"The portal to the realm of digital wisdom!\n"
                            +"I await your selection:");
    }

    public int showMenu(User user){
        //Menu display
        while (true){
            if(user == null){
                System.out.println("1. View Scrolls\n2. Exit");
                int selection = Scan.scanInteger(1, 2);
                if (selection == 2){
                    System.out.println("Good luck anonymous member of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }else if(!user.isAdmin()){
                System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Change username\n"
                                +"7. Change password\n8. Exit");
                int selection = Scan.scanInteger(1, 8);
                if (selection == 8){
                    System.out.print("Good luck member of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }else if (user.isAdmin()){
                                System.out.println("1. View Scrolls\n2. Add Scrolls\n"
                                +"3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. " 
                                +"Create new Users\n7. View users\n8. Delete users\n9. View App stats\n10. Change username\n"
                                +"11. Change password\n12. Exit");
                int selection = Scan.scanInteger(1, 12);
                if (selection == 12){
                    System.out.print("Good luck Admin of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }
        }
    }

    public void menuSelection(int selection, User user){


        if(user != null && !user.isAdmin()){
            if(selection == 6){
                selection = 10;
            }else if (selection == 7){
                selection = 11;
            }
        }

        switch (selection){
            case 1: //preview/view scrolls
                System.out.println("CALL CLASS - To be done - Show/preview scrolls");
                break;
            case 2: // Add scrolls
                if (user != null) {
                    uploader.upload();
                } else {
                    System.out.println("You must be logged in to upload files.");
                }
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
            case 7:
                System.out.println("CALL CLASS - To be done - VIEW Users");
                break;
            case 8:
                System.out.println("CALL CLASS - To be done - DELETE users");
                break;
            case 9:
                System.out.println("CALL CLASS - To be done - VIEW app stats");
                break;
            case 10:
                login.changeLoginDetails(user.getUsername(),"username");
                break;
            case 11:
                login.changeLoginDetails(user.getUsername(),"password");
                break;
        }

     }


     @Override
     public void notifyObserver(String message) {
         this.logObserver.updateLog("Menu", message);
     }
    
}