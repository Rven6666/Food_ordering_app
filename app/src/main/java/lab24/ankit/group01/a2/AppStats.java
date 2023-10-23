package lab24.ankit.group01.a2;

import java.io.FileWriter;
import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AppStats {
    
    public static void incrementCount(String key) {
        JSONParser parser = new JSONParser();
        JSONObject stats = null;

        try {
            stats = (JSONObject) parser.parse(new FileReader("src/main/java/lab24/ankit/group01/a2/Databases/AppStats.json"));
        } catch (Exception e) {
            System.out.println("Cannot find AppStats.json in databases folder");
            System.exit(1);
        }

        // getting the current count
        Long count = (Long) stats.get(key);
        count++;

        // updating the count
        stats.put(key, count);

        // writing to the json file
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/lab24/ankit/group01/a2/Databases/AppStats.json");
            fileWriter.write(stats.toJSONString());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Cannot write to AppStats.json");
            System.exit(1);
        }
    }

    public AppStats() {
        JSONParser parser = new JSONParser();
        JSONObject stats = null;

        try {
            stats = (JSONObject) parser.parse(new FileReader("src/main/java/lab24/ankit/group01/a2/Databases/AppStats.json"));
        } catch (Exception e) {
            System.out.println("Cannot find AppStats.json in databases folder");
            System.exit(1);
        }

        // printing the information to the user
        System.out.println("Total number of guest logins: " + stats.get("guest logins"));
        System.out.println("Total number of user logins: " + stats.get("user logins"));
        System.out.println("Total number of scroll views " + stats.get("scroll views"));
        System.out.println("Total number of scrolls uploaded: " + stats.get("scrolls uploaded"));
        System.out.println("Total number of scrolls downloaded: " + stats.get("scrolls downloaded"));
        System.out.println();
        
    }

}
