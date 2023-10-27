//package lab24.ankit.group01.a2.TestFileUpload;
//
//import lab24.ankit.group01.a2.UI.FileUploader;
//import lab24.ankit.group01.a2.User.User;
//import org.json.simple.JSONObject;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//public class UploadnonbinaryTest {
//
//    private final InputStream originalSystemIn = System.in;
//    @Before
//    public void setUp() {
//    }
//
//    @After
//    public void tearDown() {
//        System.setIn(originalSystemIn);
//    }
//
//    @Test
//    public void upload_nonbinary_file() {
//        String input = "/C:/Users/ethan/Desktop/cantbeupload.txt\n";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id", 4);
//        jsonObject.put("name", "sir admin");
//        jsonObject.put("phone number", "XXX-XXXXX-XXX");
//        jsonObject.put("email", "admin@admin.com.au");
//        jsonObject.put("username", "admin");
//        jsonObject.put("password", "adminPass");
//        jsonObject.put("type", "admin");
//        jsonObject.put("user id", "4");
//        User user = new User(jsonObject);
//        FileUploader fileUploader = new FileUploader(user);
//        fileUploader.upload();
//    }
//}
