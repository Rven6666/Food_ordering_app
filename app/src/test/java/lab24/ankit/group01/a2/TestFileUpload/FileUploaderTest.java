package lab24.ankit.group01.a2.TestFileUpload;

import lab24.ankit.group01.a2.UI.FileUploader;
import lab24.ankit.group01.a2.User.User;
import org.json.simple.JSONObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUploaderTest {
    private final InputStream originalSystemIn = System.in;


    @After
    public void tearDown() {
        System.setIn(originalSystemIn);
    }


    @Test
    public void upload_binary_file() {
        String input = "/Lab24-Ankit-Group01-A2/app/src/test/java/lab24/ankit/group01/a2/TestFileUpload/exampleScroll.txt\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 4);
        jsonObject.put("name", "sir admin");
        jsonObject.put("phone number", "XXX-XXXXX-XXX");
        jsonObject.put("email", "admin@admin.com.au");
        jsonObject.put("username", "admin");
        jsonObject.put("password", "adminPass");
        jsonObject.put("type", "admin");
        jsonObject.put("user id", "4");
        User user = new User(jsonObject);
        FileUploader fileUploader = new FileUploader(user);
        fileUploader.upload();
    }


    @Test
    public void updateScrolls() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("name", "admin");
        jsonObject.put("phone number", "1323131");
        jsonObject.put("email", "1234321431@gmail.com");
        jsonObject.put("username", "test");
        jsonObject.put("password", "adminPass");
        jsonObject.put("type", "admin");
        jsonObject.put("user id", "1");
        User user = new User(jsonObject);
        FileUploader fileUploader = new FileUploader(user);
        fileUploader.updateScrolls();
    }
}
