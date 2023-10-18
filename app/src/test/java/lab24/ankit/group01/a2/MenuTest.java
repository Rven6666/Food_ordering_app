package lab24.ankit.group01.a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import lab24.ankit.group01.a2.User_types.Admin;
import lab24.ankit.group01.a2.User_types.Member;
import lab24.ankit.group01.a2.User_types.User;
import org.json.simple.JSONObject;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;



public class MenuTest {

    private User admin;
    private User member;

    public MenuTest() {
        JSONObject userJSON = new JSONObject();
        userJSON.put("id", 1);
        userJSON.put("full_name", "John Doe");
        userJSON.put("phone_number", "1234567890");
        userJSON.put("email", "test@test.com.au");
        userJSON.put("password", "password");
        this.admin = new Admin(userJSON);
        this.member = new Member(userJSON);
    }

    @Test
    public void test1() {
        String input= "1\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Menu menu = new Menu();
        int selection = menu.showMenu(member);
        assertTrue(byteArrayOutputStream.toString().contains("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Change username\n"
        +"7. Change password\n8. Exit"));
    }

    @Test
    public void test2() {
        String input= "1\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Menu menu = new Menu();
        int selection = menu.showMenu(member);
        assertTrue(byteArrayOutputStream.toString().contains("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Change username\n"
        +"7. Change password\n8. Exit"));
    }

    @Test
    public void test3() {
        String input= "1\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Menu menu = new Menu();
        int selection = menu.showMenu(member);
        assertTrue(byteArrayOutputStream.toString().contains("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Change username\n"
        +"7. Change password\n8. Exit"));
    }
}