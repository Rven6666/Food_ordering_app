package lab24.ankit.group01.a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;



public class MenuTest {

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