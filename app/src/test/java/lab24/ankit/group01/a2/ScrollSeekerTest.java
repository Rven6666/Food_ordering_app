package lab24.ankit.group01.a2;

import junit.framework.TestCase;
import lab24.ankit.group01.a2.Miscellaneous.Scan;
import lab24.ankit.group01.a2.Scrolls.ScrollSeeker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import lab24.ankit.group01.a2.User.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

// testing framework used in SOFT3202 (next year)
import java.io.*;

public class ScrollSeekerTest {
    private JSONParser parser;

    private ScrollSeeker scrollSeeker = new ScrollSeeker();
    private User user = new User(retrieveUserJsonObjectInfo());
    private static MockedStatic<ScrollSeeker> mockedStaticScrollSeeker;

    @Before
    public void setup() {
        parser = mock(JSONParser.class);
    }

    // courtesy = https://stackoverflow.com/questions/63263662/mockito-3-4-0-static-mocking-exception
    // you are a life saver bro!!! holy sh*#!!!!
    @BeforeAll
    public static void initStaticMock(){
        mockedStaticScrollSeeker = mockStatic(ScrollSeeker.class);
    }

    @AfterAll
    public static void close(){
        mockedStaticScrollSeeker.close();
    }

    /**
     * display all scrolls empty test, so we mock the ScrollSeeker class
     * using the mockito framework but in doing so we mock static. pass in
     * user as null and return empty array when getting scrolls array
     */
    @Test
    public void displayAllScrollsEmptyArray(){
        // empty JSONArray to simulate an empty scrollsArray
        JSONArray emptyArray = new JSONArray();

        // mock static method reference of ScrollSeeker
        when(ScrollSeeker.getScrollsArray()).thenReturn(emptyArray);

        // capture the printed output from the ByteArrayOutputStream
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the displayAllScrolls method, specifying user as null
        scrollSeeker.displayAllScrolls(null);

        // assertEquals expected output and output stream
        assertEquals("No Scrolls to view. Returning to menu.\n".strip(), output.toString().strip());
    }

    /**
     * call displayAllScrolls method for multiple Scrolls.json populated with multiple scrolls. we
     * should expect some output to be printed
     */
    @Test
    public void displayAllScrollsForMultipleScrolls(){
        // Create a JSONArray with multiple scroll jsonObjects
        JSONArray multipleScrollsArray = new JSONArray();
        JSONObject scroll1 = new JSONObject();
        scroll1.put("scroll id", "1");
        scroll1.put("date", "2023-10-27");
        scroll1.put("uploader id", "user1");
        scroll1.put("filename", "scroll1.txt");
        scroll1.put("version", "1");
        JSONObject scroll2 = new JSONObject();
        scroll2.put("scroll id", "2");
        scroll2.put("date", "2023-10-28");
        scroll2.put("uploader id", "user2");
        scroll2.put("filename", "scroll2.txt");
        scroll2.put("version", "2");
        multipleScrollsArray.add(scroll1);
        multipleScrollsArray.add(scroll2);

        // Mock the getScrollsArray method to return array with multiple scroll objects
        when(ScrollSeeker.getScrollsArray()).thenReturn(multipleScrollsArray);

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the displayAllScrolls method, specifying user as null
        scrollSeeker.displayAllScrolls(null);

        // Assert that the expected output is printed (this is a pain in to write)
        String expectedOutput = "All Scrolls:\n" +
                "Scroll ID = 1\n" +
                "Date = 2023-10-27\n" +
                "Uploader ID = user1\n" +
                "Filename = scroll1.txt\n" +
                "Version = 1\n" +
                "\n" +
                "Scroll ID = 2\n" +
                "Date = 2023-10-28\n" +
                "Uploader ID = user2\n" +
                "Filename = scroll2.txt\n" +
                "Version = 2\n" +
                "\n";
        assertEquals(expectedOutput.strip(), output.toString().strip());
    }

    @Test
    public void displayAllScrollsForMultipleScrollsObie(){
        // Create a JSONArray with multiple scroll objects
        JSONArray multipleScrollsArray = new JSONArray();
        JSONObject scroll1 = new JSONObject();
        scroll1.put("scroll id", "1");
        scroll1.put("date", "2023-10-27");
        scroll1.put("uploader id", "user1");
        scroll1.put("filename", "scroll1.txt");
        scroll1.put("version", "1");
        JSONObject scroll2 = new JSONObject();
        scroll2.put("scroll id", "2");
        scroll2.put("date", "2023-10-28");
        scroll2.put("uploader id", "user2");
        scroll2.put("filename", "scroll2.txt");
        scroll2.put("version", "2");
        multipleScrollsArray.add(scroll1);
        multipleScrollsArray.add(scroll2);

        mockStatic(ScrollSeeker.class);
        when(ScrollSeeker.getScrollsArray()).thenReturn(multipleScrollsArray);

        // Capture the printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Call the method
        scrollSeeker.displayAllScrolls(user);

        // Assert that the expectedOutput is equal to the actual
        String expectedOutput = "All Scrolls:\n" +
                "Scroll ID = 1\n" +
                "Date = 2023-10-27\n" +
                "Uploader ID = user1\n" +
                "Filename = scroll1.txt\n" +
                "Version = 1\n" +
                "\n" +
                "Scroll ID = 2\n" +
                "Date = 2023-10-28\n" +
                "Uploader ID = user2\n" +
                "Filename = scroll2.txt\n" +
                "Version = 2\n" +
                "\n";

        assertEquals(expectedOutput.strip(), output.toString().strip());
    }

    /* Skipping the other two tests viewScroll(User user) and getScrollsArray() because it is
    *  just too involved. */

    /**
     * Test the display of scroll version info with valid data.
     */
    @Test
    public void testDisplayScrollVersionInfoValidData(){
        // Capture the standard output to verify printed information
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Create a sample scrollInfo JSON object
        JSONObject scrollInfo = new JSONObject();
        scrollInfo.put("version", "1.0");
        scrollInfo.put("date", "2023-10-28");
        scrollInfo.put("filename", "sample.txt");

        // Call the displayScrollVersionInfo method
        scrollSeeker.displayScrollVersionInfo(scrollInfo);

        // Expected output
        String expectedOutput = "Version = 1.0\nDate = 2023-10-28\nFilename = sample.txt\n\n";

        // Assert that the method has printed the expected information
        assertEquals(expectedOutput, outContent.toString());

        // Clean up by resetting the standard output
        System.setOut(System.out);
    }

    /**
     * Test the display of scroll version info with null data.
     */
    @Test
    public void testDisplayScrollVersionInfoNullData() {

        // Expected output (empty since the method does not print anything when data is null)
        assertThrows(NullPointerException.class, () -> {
            scrollSeeker.displayScrollVersionInfo(null);
        });

    }

    /* scroll history skipped because it is too hard */
    @Test
    public void testGetScroll_WhenScrollIDNotExists() throws IOException, ParseException {
        String scrollId = "3";

        JSONObject versionInfo = new JSONObject();
        versionInfo.put("date", "2023-10-27");
        versionInfo.put("filename", "to_save.txt");
        versionInfo.put("version", "0");

        JSONArray versions = new JSONArray();
        versions.add(versionInfo);

        JSONObject scrollInfo = new JSONObject();
        scrollInfo.put("scroll id", scrollId);
        scrollInfo.put("uploader id", "500");
        scrollInfo.put("date", "2023-10-27");
        scrollInfo.put("filename", "to_save.txt");
        scrollInfo.put("downloads", "1");

        // Sort the 'versions' array to ensure consistent ordering
        versions.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));

        scrollInfo.put("versions", versions);
        scrollInfo.put("uploader real id", "3");
        scrollInfo.put("version", "0");

        JSONArray scrollsArray = new JSONArray();
        scrollsArray.add(scrollInfo);

        // Sort the 'scrolls' array to ensure consistent ordering
        scrollsArray.sort((o1, o2) -> o1.toString().compareTo(o2.toString()));

        JSONObject scrolls = new JSONObject();
        scrolls.put("scrolls", scrollsArray);

        when(parser.parse(Mockito.any(FileReader.class))).thenReturn(scrolls);

        JSONObject result = scrollSeeker.getScroll(scrollId);

        assertEquals(null, result);
    }

    @Test
    public void testPrintScrollWhenVersionIsNull() {
        // Create a sample scroll_info JSONObject where version is present
        JSONObject scroll_info = new JSONObject();
        scroll_info.put("scroll id", "1");
        scroll_info.put("filename", "sample.txt");
        scroll_info.put("version", "1.0");

        // Call the printScroll method with a null version
        scrollSeeker.printScroll(scroll_info, null);

        // Add your assertions here. For example, you can capture the printed output and assert it.
        // You may need to redirect System.out to a ByteArrayOutputStream to capture output.
    }


    /**
     * returns an instance of Obie JsonObject info
     * @return, JSONObject
     */
    private JSONObject retrieveUserJsonObjectInfo(){
        JSONObject info = new JSONObject();
        info.put("id", "1");
        info.put("name", "Obie");
        info.put("phone number", "081250805360");
        info.put("email", "Obie.kal22@gmail.com");
        info.put("username", "Oana3745");
        info.put("password", "$2a$10$irewhlFWePgEckuL847vGuuK7FwvIHFXbHzqemnddmOLp2svkCJRa");
        info.put("type", "admin");
        info.put("user id", "1");
        return info;
    }

}
