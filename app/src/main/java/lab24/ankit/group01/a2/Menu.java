package lab24.ankit.group01.a2;
import lab24.ankit.group01.a2.Scan;

import java.util.Scanner;
import java.util.List;

public class Menu{

    public Scanner scan;
    public Scan inputCheck;

    public Menu(){
        this.scan = new Scanner(System.in);
    }

    public void menuIntro(String name){
        System.out.println("Hello "+ name +" I am then (VSAS) Virtual Scroll Access System.");
        System.out.println("The portal to the realm of digital wisdom!");
        System.out.println("I await your selection:");
    }

    public int showMenu(String userType){
        //Menu display
        while (true){
            if(userType.equals("Guest")){
                System.out.println("1. View Scrolls\n2. Exit");
                int selection = inputCheck.scanInteger(1, 2);
                if (selection == 2){
                    System.out.println("Good luck anonymous member of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }else if(userType.equals("Member")){
                System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Exit");
                int selection = inputCheck.scanInteger(1, 6);
                if (selection == 6){
                    System.out.print("Good luck member of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }else if (userType.equals("Admin")){
                                System.out.println("1. View Scrolls\n2. Add Scrolls\n"
                                +"3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. " 
                                +"Create new Users\n7. View users\n8. Delete users\n9. View App stats\n10. Exit");
                int selection = inputCheck.scanInteger(1, 10);
                if (selection == 10){
                    System.out.print("Good luck Admin of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }
        }
    }

    public void menuSelection(int selection){
        switch (selection){
            case 1: //preview/view scrolls
                System.out.println("CALL CLASS - To be done - Show/preview scrolls");
                break;
            case 2: // Add scrolls
                 System.out.println("CALL CLASS - To be done - ADD scrolls");
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
        }
     }

    
}