package lab24.ankit.group01.a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


public class MenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // @Test //Tests output for Admin menu
    // public void testAdminMenu() {
    //     String input= "1\n";
    //     ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
    //     System.setIn(byteArrayInputStream);
    //     ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    //     PrintStream printStream=new PrintStream(byteArrayOutputStream);
    //     System.setOut(printStream);
    //     Menu menu = new Menu();
    //     User admin = new Admin();
    //     int selection = menu.showMenu(admin);
    //     menu.menuSelection(selection, admin);
    //     assertTrue(byteArrayOutputStream.toString().contains("1. View Scrolls\n2. Add Scrolls\n"
    //                             +"3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. " 
    //                             +"Create new Users\n7. View users\n8. Delete users\n9. View App stats\n10. Change username\n"
    //                             +"11. Change password\n12. Exit"));
    // }

    // @Test //Tests output for Member menu
    // public void testMemberMenu() {
    //     String input= "1\n";
    //     ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
    //     System.setIn(byteArrayInputStream);
    //     ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    //     PrintStream printStream=new PrintStream(byteArrayOutputStream);
    //     System.setOut(printStream);
    //     Menu menu = new Menu();
    //     User member = new Member();
    //     int selection = menu.showMenu(member);
    //     menu.menuSelection(selection, member);
    //     assertTrue(byteArrayOutputStream.toString().contains("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Change username\n"
    //                             +"7. Change password\n8. Exit"));
    // }
}