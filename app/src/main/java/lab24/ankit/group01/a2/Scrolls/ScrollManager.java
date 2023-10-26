package lab24.ankit.group01.a2.Scrolls;

import lab24.ankit.group01.a2.AppStats;
import lab24.ankit.group01.a2.Scan;
import lab24.ankit.group01.a2.Scrolls.ScrollSeeker;
import lab24.ankit.group01.a2.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;

public class ScrollManager{
    
    private static final String SCROLLS_PATH = "src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
    private static final String UPLOADED_SCROLLS_PATH = "src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/";
    private JSONParser parser = new JSONParser();

    public void editScroll(User user){
        /* edit the scroll based on the supplied id */
        JSONArray scrollsArray = ScrollSeeker.getScrollsArray();

        System.out.println("\nDisplaying your scrolls: ");
        int count = 0;

        for (Object obj : scrollsArray) {
            JSONObject scrollInfo = (JSONObject) obj;
            if (scrollInfo.get("uploader real id").equals(user.getID())) {
                System.out.println("Scroll ID: " + scrollInfo.get("scroll id"));
                System.out.println("Scroll Date: " + scrollInfo.get("date"));
                System.out.println("Scroll Name: " + scrollInfo.get("filename"));
                System.out.println("Scroll Version: " + scrollInfo.get("version"));
                System.out.println("Scroll Downloads: " + scrollInfo.get("downloads"));
                System.out.println();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("You have no scrolls to edit. Returning to menu.\n");
            return;
        }

        JSONObject scroll = null;

        while (true) {
            System.out.print("Please enter the id of the scroll you would like to edit: ");
            String scrollID = Scan.scanString(null);
            if (scrollID.equals("exit")) return;
            scroll = ScrollSeeker.getScroll(scrollID);
            if(scroll == null || !scroll.get("uploader real id").equals(user.getID())) {
                System.out.println("Invalid scroll ID, please enter another or type 'exit' to return to the main menu.");
                scroll = null;
                continue;
            }
            break;
        }

        // convert user string input to binary representation
        System.out.print("Please enter content to overwrite the existing contents with: ");
        String content = Scan.scanString(null);
        String binary_content = convertStringToBinary(content);

        // write the binary representation to the file
        String filename = (String) scroll.get("filename");
        String nextVersion = getNextVersion(scroll);
        filename += "-" + nextVersion;

        // write new file to the folder
        String path = UPLOADED_SCROLLS_PATH + scroll.get("scroll id") + "/" + filename;
        try {
            FileWriter fileObj = new FileWriter(path);
            fileObj.write(binary_content);
            fileObj.close();
        } catch (IOException e) {
            System.out.println("Error writing to file. Returning to menu.\n");
            return;
        }

        // update database
        scroll.put("version", nextVersion);
        scroll.put("date", LocalDate.now().toString());


        // update versions array
        JSONArray versions = (JSONArray) scroll.get("versions");
        JSONObject new_version = new JSONObject();
        new_version.put("date", scroll.get("date"));
        new_version.put("filename", filename);
        new_version.put("version", nextVersion);
        versions.add(new_version);
        scroll.put("versions", versions);

        // remove previous scroll info then add new
        for (Object obj : scrollsArray) {
            JSONObject scrollInfo = (JSONObject) obj;
            if (scrollInfo.get("scroll id").equals(scroll.get("scroll id"))) {
                scrollsArray.remove(scrollInfo);
                break;
            }
        }
        scrollsArray.add(scroll);

        File jsonFile = new File("src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json");

        JSONObject scrolls = new JSONObject();
        scrolls.put("scrolls", scrollsArray);

        // write to the json file
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(scrolls.toJSONString());
            fileWriter.close();
            AppStats.incrementCount("scrolls uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating scrolls database");
            System.exit(1);
        }

        System.out.println("Successfully wrote new contents to file!");

    }

    public String getNextVersion(JSONObject scroll) {
        /* get the next version of the scroll */
        String currentVersion = (String) scroll.get("version");
        int nextVersion = Integer.parseInt(currentVersion) + 1;
        return Integer.toString(nextVersion);
    }

    public void removeScroll(User user){
        /* remove scroll based on the supplied id */

        JSONArray scrollsArray = ScrollSeeker.getScrollsArray();

        System.out.println("Displaying your scrolls: ");
        int count = 0;

        for (Object obj : scrollsArray) {
            JSONObject scrollInfo = (JSONObject) obj;
            if (scrollInfo.get("uploader real id").equals(user.getID())) {
                System.out.println("Scroll ID: " + scrollInfo.get("scroll id"));
                System.out.println("Scroll Name: " + scrollInfo.get("filename"));
                System.out.println("Scroll Version: " + scrollInfo.get("version"));
                System.out.println("Scroll Date: " + scrollInfo.get("date"));
                System.out.println("Scroll Downloads: " + scrollInfo.get("downloads"));
                System.out.println();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("You have no scrolls to remove. Returning to menu.\n");
            return;
        }

        JSONObject scroll = null;

        while (true) {
            System.out.print("Please enter the id of the scroll you would like to remove: ");
            String scrollID = Scan.scanString(null);
            if (scrollID.equals("exit")) return;
            scroll = ScrollSeeker.getScroll(scrollID);
            if(scroll == null || !scroll.get("uploader real id").equals(user.getID())) {
                System.out.println("Invalid scroll ID, please enter another or type 'exit' to return to the main menu.");
                scroll = null;
                continue;
            }
            break;
        }

        // delete directory
        File dir = new File(UPLOADED_SCROLLS_PATH + scroll.get("scroll id"));

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        dir.delete();

        // delete from scrolls database
        scrollsArray.remove(scroll);

        // update scrolls array
        JSONObject scrolls = new JSONObject();
        scrolls.put("scrolls", scrollsArray);

        File jsonFile = new File("src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json");

        // write to the json file
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(scrolls.toJSONString());
            fileWriter.close();
            AppStats.incrementCount("scrolls uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating scrolls database");
            System.exit(1);
        }

        System.out.println("Successfully removed scroll!");
    }

    public static String convertStringToBinary(String input) {
        /* function to convert string to binary */
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")       // zero pads
            );
        }
        return result.toString();
    }

    public static void updateUserScrollIDs(String id, String newUploaderID) {
        
        JSONArray scrollsArray = ScrollSeeker.getScrollsArray();

        for (int i = 0; i < scrollsArray.size(); i++) {
            JSONObject scrollInfo = (JSONObject) scrollsArray.get(i);
            if (scrollInfo.get("uploader real id").equals(id)) {
                scrollInfo.put("uploader id", newUploaderID);
                scrollsArray.remove(i);
                scrollsArray.add(scrollInfo);
                break;
            }
        }

        JSONObject scrolls = new JSONObject();

        scrolls.put("scrolls", scrollsArray);

        File jsonFile = new File("src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json");

        // write to the json file
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(scrolls.toJSONString());
            fileWriter.close();
            AppStats.incrementCount("scrolls uploaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating scrolls database");
            System.exit(1);
        }

    }

    public static boolean updateAfterEditing(JSONArray scrolls_array, User user){
        /* update the scroll information to reflect the one
           that has been removed
        */
        JSONArray updated_scrolls_array = new JSONArray();

        for(int i = 0; i < scrolls_array.size(); i++){
            JSONObject scroll_temp = (JSONObject) scrolls_array.get(i);

            String date = (String) scroll_temp.get("date");
            String uploader_id = (String) scroll_temp.get("uploader_id");
            String filename = (String) scroll_temp.get("filename");
            String file_id = Integer.toString(i+1); // write id to the new scroll_temp
            Long version = (Long) scroll_temp.get("version");

            if (scroll_temp.get("uploader_id").equals(user.getID())){
                version += 1;
            }

            // update the scrolls json database information
            scroll_temp.put("date", date);
            scroll_temp.put("uploader_id", uploader_id);
            scroll_temp.put("filename", filename);
            scroll_temp.put("file_id", file_id);
            scroll_temp.put("version", version);

            // add to the updated scroll array
            updated_scrolls_array.add(scroll_temp);

        }

        JSONObject final_scrolls = new JSONObject();
        final_scrolls.put("scrolls", updated_scrolls_array);

        try {
            // write to the Scrolls.json file in the database
            FileWriter file = new FileWriter(SCROLLS_PATH);
            file.write(final_scrolls.toJSONString());
            file.close();
            return true;

        } catch (IOException e){
            // return false
            return false;
        }
    }

    public static boolean updateAfterRemove(JSONArray scrolls_array){
        /* update the scroll information to reflect the one
           that has been removed
        */
        JSONArray updated_scrolls_array = new JSONArray();

        for(int i = 0; i < scrolls_array.size(); i++){
            JSONObject scroll_temp = (JSONObject) scrolls_array.get(i);

            String date = (String) scroll_temp.get("date");
            String uploader_id = (String) scroll_temp.get("uploader_id");
            String filename = (String) scroll_temp.get("filename");
            String file_id = Integer.toString(i+1); // write id to the new scroll_temp
            Long version = (Long) scroll_temp.get("version");

            // update the scrolls json database information
            scroll_temp.put("date", date);
            scroll_temp.put("uploader_id", uploader_id);
            scroll_temp.put("filename", filename);
            scroll_temp.put("file_id", file_id);
            scroll_temp.put("version", version);

            // add to the updated scroll array
            updated_scrolls_array.add(scroll_temp);
        }

        JSONObject final_scrolls = new JSONObject();
        final_scrolls.put("scrolls", updated_scrolls_array);

        try {
            // write to the Scrolls.json file in the database
            FileWriter file = new FileWriter(SCROLLS_PATH);
            file.write(final_scrolls.toJSONString());
            file.close();
            return true;

        } catch (IOException e){
            // return false
            return false;
        }
    }

}