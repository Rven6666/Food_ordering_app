/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package lab24.ankit.group01.a2;

import lab24.ankit.group01.a2.User_types.User;

public class App {

    public User user;
    public Menu menu;
    public int selection;

    public App () {
        menu = new Menu();
        run();
        menuIntro();
        menu.menuSelection(menuDisplay());
    }

    public static void main(String[] args) {
        // running the App
        new App();
    }

    public void run() {
        // displaying login screen
        Login login = new Login();
        login.displayLoginScreen();
        user = login.getUser();

    }

    public void menuIntro(){
        // Menu intro
        if(user != null){
            user.showUser();
            menu.menuIntro(user.getName());
            return;
        }else{
            System.out.println("\nUser: Guest");
            menu.menuIntro("Guest");
            return;
        }
    }

    public int menuDisplay(){
        if(user != null){
            if(user.isAdmin() == true){
                return menu.showMenu("Admin");
            }else{
                return menu.showMenu("User");
            }
        }else{
            return menu.showMenu("Guest");
        }
     }


}