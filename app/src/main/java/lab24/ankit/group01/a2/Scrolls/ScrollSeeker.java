package lab24.ankit.group01.a2.Scrolls;
import lab24.ankit.group01.a2.Scan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class ScrollSeeker{
    private static final String SCROLLS_PATH = "/Users/obie/Desktop/SOFT2412/Assignments/A2/Lab24-Ankit-Group01-A2/app/src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
    private JSONParser parser = new JSONParser();

    public void viewScroll(){

        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");
            System.out.println("Displaying scroll information");
            for(int i = 0; i < scrolls_array.size(); i++){
                JSONObject scroll_info = (JSONObject) scrolls_array.get(i);
                Long id = (Long) scroll_info.get("id");
                String uploader = (String) scroll_info.get("uploader");
                String filename = (String) scroll_info.get("filename");
                String date = (String) scroll_info.get("date");

                // Print scroll information in the desired format
                System.out.println("id = " + id);
                System.out.println("uploader = " + uploader);
                System.out.println("upload_date = " + date);
                System.out.println("filename = " + filename + "\n");
            }

        } catch (Exception e){
            System.err.println(e);
        }
    }

    public void previewScroll(){

        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");

            System.out.print("Please select a scroll id to preview: ");
            int id = Scan.scanInteger(1, scrolls_array.size());
            JSONObject scroll_info = (JSONObject) scrolls_array.get(id-1);
            // open the file
            String filename = (String) scroll_info.get("filename");

            File fileObj = new File("/Users/obie/Desktop/SOFT2412/Assignments/A2/Lab24-Ankit-Group01-A2/app/src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + filename);
            Scanner scanner = new Scanner(fileObj);
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                // Print a shortened version of the first line, e.g., the first 50 characters
                int maxLength = Math.min(firstLine.length(), 50);
                System.out.println(firstLine.substring(0, maxLength));
            }

        } catch (Exception e){
            System.err.println(e);
        }

        // prints newline before menu selection
        System.out.println();
    }
}