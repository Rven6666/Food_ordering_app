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

    // @Test
    // public void test2() {
    //     String input= "1\nmember\nmemberNotPass\n";
    //     ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
    //     System.setIn(byteArrayInputStream);
    //     ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    //     PrintStream printStream=new PrintStream(byteArrayOutputStream);
    //     System.setOut(printStream);
    //     Login login = new Login();
    //     login.displayLoginScreen();
    //     assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
    //     assertTrue(byteArrayOutputStream.toString().contains("Invalid username or password\nWould you like to try again? (y/n)"));
    // }

    // @Test
    // public void test3() {
    //     String input= "2\n";
    //     ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
    //     System.setIn(byteArrayInputStream);
    //     ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    //     PrintStream printStream=new PrintStream(byteArrayOutputStream);
    //     System.setOut(printStream);
    //     Login login = new Login();
    //     login.displayLoginScreen();
    //     assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
    //     assertTrue(byteArrayOutputStream.toString().contains("Welcome, guest!"));
    // }

    // @Test
    // public void test4() {
    //     String input= "3\n";
    //     ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
    //     System.setIn(byteArrayInputStream);
    //     ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
    //     PrintStream printStream=new PrintStream(byteArrayOutputStream);
    //     System.setOut(printStream);
    //     Login login = new Login();
    //     login.displayLoginScreen();
    //     assertTrue(byteArrayOutputStream.toString().contains("1. Login\n2. Continue as Guest\n3. Exit"));
    //     assertTrue(byteArrayOutputStream.toString().contains("Thanks for stopping by!"));
    // }

    @Test //valid username change test
    public void changeLoginUsernameTest(){
        
        String input= "Mary\ny";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.changeLoginDetails("John","username");
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a username between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: Mary\nPlease confirm if this is correct? (y/n)"));
    }

    @Test //change of mine password check
    public void changeMindUsernameChangeTest(){
        String input= "Bobby\nn\nGeorge\ny";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.changeLoginDetails("John","username");
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a username between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: Bobby\nPlease confirm if this is correct? (y/n)"));
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a username between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: George\nPlease confirm if this is correct? (y/n)"));
    }

        @Test //change of mine password check
    public void changeUsernameTooShortTest(){
        String input= "Bob\nBobby\ny";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.changeLoginDetails("John","username");
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a username between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("Invalid Input"));
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a username between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: Bobby\nPlease confirm if this is correct? (y/n)"));
       
    }


        @Test //Valid password change test
    public void changeLoginPasswordTest(){
        String input= "Password1\ny";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.changeLoginDetails("John","password");
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a password between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: Password1\nPlease confirm if this is correct? (y/n)"));
    }

    @Test //change of mine password check
    public void changeMindPasswordChangeTest(){
        String input= "Password1\nn\nnewPassword\ny";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        Login login = new Login();
        login.changeLoginDetails("John","password");
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a password between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: Password1\nPlease confirm if this is correct? (y/n)"));
        assertTrue(byteArrayOutputStream.toString().contains("Please choose a password between 4 and 12 characters: "));
        assertTrue(byteArrayOutputStream.toString().contains("You entered: Password1\nPlease confirm if this is correct? (y/n)"));
    }

        // @BeforeEach
    // void setUp() {
    //     login = new Login();
    //     testFilePath = "app/src/test/java/lab24/ankit/group01/a2/TestJsonFiles/test.json";
    // }

    // @Test // Testing Updating username
    // public void testUpdateUsername() throws Exception {
    //     createTestJSONFile(testFilePath);
    //     String username ="Oana3745";
    //     login.updateLoginInfo(username, "newUsername", "username");

    //     JSONParser parser = new JSONParser();
    //     JSONObject usersObject = (JSONObject) parser.parse(new File(testFilePath));
    //     JSONArray users = (JSONArray) usersObject.get("users");
    //     String updatedUsername = null;

    //     for (Object user : users) {
    //         JSONObject jsonOb = (JSONObject) user;
    //         if (jsonOb.get("username").equals(username)) {
    //             updatedUsername = "newUsername";
    //             break;
    //         }
    //     }

    //     assertEquals("newUsername", updatedUsername); // Assert that the username was updated
    // }

    // private void createTestJSONFile(String filePath) {
    //     try (FileWriter file = new FileWriter(filePath)) {
    //         String json = "{\"users\":[{\"password\":\"$2a$10$irewhlFWePgEckuL847vGuuK7FwvIHFXbHzqemnddmOLp2svkCJRa\"," +
    //                 "\"full_name\":\"Obie Ananda\",\"phone_number\":\"xxx-xxxx-xxxx\",\"id\":\"1\",\"type\":\"admin\"," +
    //                 "\"email\":\"haha.youWish@gmail.com\",\"username\":\"Oana3745\"}]}";
    //         file.write(json);
    //         file.flush();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}