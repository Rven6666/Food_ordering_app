package lab24.ankit.group01.a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void test1() {
        String input= "1\nmember\nmemberPass\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.displayLoginScreen();
        assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
        assertTrue(byteArrayOutputStream.toString().contains("Login successful"));
    }

    @Test
    public void test2() {
        String input= "1\nmember\nnotMemberPass\ny\n1\nmember\nnotMemberPass\nn";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.displayLoginScreen();
        assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
        assertTrue(byteArrayOutputStream.toString().contains("Invalid username or password"));
        assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
        assertTrue(byteArrayOutputStream.toString().contains("Invalid username or password"));
        assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
    }

    @Test
    public void test3() {
        String input= "2\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.displayLoginScreen();
        assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
        assertTrue(byteArrayOutputStream.toString().contains("Welcome, guest!"));
    }

    @Test
    public void test4() {
        String input= "3\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.displayLoginScreen();
        assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
        assertTrue(byteArrayOutputStream.toString().contains("Thanks for stopping by!"));
    }
}