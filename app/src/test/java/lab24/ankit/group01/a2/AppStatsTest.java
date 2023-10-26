package lab24.ankit.group01.a2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lab24.ankit.group01.a2.UI.AppStats;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AppStatsTest {

    @Test
    public void test1() {
        String input= "y\n";
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PrintStream printStream=new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);
        new AppStats();
        assertTrue(byteArrayOutputStream.toString().contains("Total number of guest logins:"));
        assertTrue(byteArrayOutputStream.toString().contains("Total number of user logins:"));
        assertTrue(byteArrayOutputStream.toString().contains("Total number of scroll views"));
        assertTrue(byteArrayOutputStream.toString().contains("Total number of scrolls uploaded"));
        assertTrue(byteArrayOutputStream.toString().contains("Total number of scrolls downloaded"));
        assertTrue(byteArrayOutputStream.toString().contains("Would you like to see the number of downloads for each scroll? (y/n)"));
    }
}