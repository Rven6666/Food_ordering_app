package lab24.ankit.group01.a2;

import lab24.ankit.group01.a2.User_types.Guest;
import lab24.ankit.group01.a2.User_types.User;

public class Login {
    
    private User user;

    public void displayLoginScreen() {
        System.out.println("Welcome to the Scroll Management System");
        System.out.println("1. Member Login\n2. Admin Login\n3. Continue as Guest\n4. Exit");
        System.out.println("Please select an option");
        int choice = Scan.scanInteger(1, 4);
        switch (choice) {
            case 1:
                attemptUserLogin();
                break;
            case 2:
                attemptAdminLogin();
                break;
            case 3:
                user = new Guest();
                break;
            case 4:
                System.exit(0);
                break;
        }
    }
    
    public void attemptUserLogin() {
        System.out.println("Enter your username");
        String username = Scan.scanString();
        System.out.println("Enter your password");
        String password = Scan.scanString();
        
        
    }


    public void attemptAdminLogin() {



    }

}
