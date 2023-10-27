package lab24.ankit.group01.a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lab24.ankit.group01.a2.UI.Login;
import org.junit.Test;
import org.json.simple.JSONObject; // Make sure to use the correct import for JSONObject
import lab24.ankit.group01.a2.User; // Adjust the package and class name for the User class



import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

// }@Test
// public void testGetUserByID() {
//     UserManager userManager = new UserManager();
//     User user = new User(new JSONObject("{\"id\":\"1\",\"name\":\"John Doe\",\"phone number\":\"1234567890\",\"email\":\"johndoe@example.com\",\"username\":\"johndoe\",\"password\":\"password\",\"type\":\"user\",\"user id\":\"123\"}"));
//     userManager.addUser(user);
//     assertEquals(user, userManager.getUserByID("1"));
// }


    @Test
    public void testUserConstructor() {
        // Create a sample JSON object to mimic user data
        JSONObject user = new JSONObject();
        user.put("id", "1");
        user.put("name", "John Doe");
        user.put("phone number", "1234567890");
        user.put("email", "johndoe@example.com");
        user.put("username", "johndoe");
        user.put("password", "hashed_password");
        user.put("type", "admin");
        user.put("user id", "1001");

        // // Create a User object using the constructor
        User user = new User(userJson);

        // Assert that the User object is created correctly
        assertEquals("1", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("1234567890", user.getPhoneNum());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("johndoe", user.getUsername());
        assertEquals("hashed_password", user.getEncryptedPassword());
        assertEquals("admin", user.getType());
        assertEquals("1001", user.getUserId());
    }

}