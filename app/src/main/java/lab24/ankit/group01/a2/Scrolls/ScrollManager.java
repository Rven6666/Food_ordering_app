package lab24.ankit.group01.a2.Scrolls;

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

public class ScrollManager{
    private static final String SCROLLS_PATH = "src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
    private static final String UPLOADED_SCROLLS_PATH = "src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/";
    private JSONParser parser = new JSONParser();

    public void editScroll(User user){
        /* edit the scroll based on the supplied id */
        try {
            // read the scroll json file from the database
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");

            if (scrolls_array.size() == 0) {
                // return to the caller function
                System.out.println("No scroll to display and edit. Returning to menu.\n");
                return;
            }

            System.out.println("Displaying scroll information");
            for(int i = 0; i < scrolls_array.size(); i++){
                JSONObject scroll_info = (JSONObject) scrolls_array.get(i);

                String date = (String) scroll_info.get("date");
                String uploader_id = (String) scroll_info.get("uploader_id");
                String filename = (String) scroll_info.get("filename");
                String file_id = (String) scroll_info.get("file_id");
                Long version = (Long) scroll_info.get("version");

                if (uploader_id.equals(user.getID())){
                    // show scroll corresponding to the ones
                    // only uploaded by the user
                    System.out.println("date = " + date);
                    System.out.println("uploader id = " + uploader_id);
                    System.out.println("filename = " + filename);
                    System.out.println("file id = " + file_id);
                    System.out.println("version = " + version);
                }
            }

            // select the scroll to update
            System.out.print("Please select the id of the scroll to update: ");
            int idx = Scan.scanInteger(1, scrolls_array.size()) - 1;
            JSONObject scroll_info = (JSONObject) scrolls_array.get(idx);

            // keep prompting user for input until it matches his/her id
            while ((Long)scroll_info.get("id") != Long.valueOf(user.getID())){
                System.out.printf("Error, must remove scrolls correspond to your name only: %s\n",user.getName());
                System.out.print("Please select the id of the scroll to remove: ");
                idx = Scan.scanInteger(1, scrolls_array.size()) - 1;
                scroll_info = (JSONObject) scrolls_array.get(idx);
            }

            // convert user string input to binary representation
            System.out.print("Please select a content to overwrite the existing contents with: ");
            String content = Scan.scanString(null);
            String binary_content = convertStringToBinary(content);

            // write the binary representation to the file
            FileWriter fileObj = new FileWriter("src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + scroll_info.get("filename"));
            fileObj.write(binary_content);
            fileObj.close();

            // output successful edit message to user
            System.out.printf("Content successfully updated to be: %s\n", content);
            System.out.printf("Corresponding binary representation: %s\n\n", binary_content);

        } catch (Exception e){
            System.out.println("No scroll to display and edit. Returning to menu.\n");
        }
    }

    public void removeScroll(User user){
        /* remove scroll based on the supplied id */
        // early user exit if user is null
        if (user == null){
            System.out.println("We are sorry. Only registered user are eligible to remove a scroll.");
            System.out.println("Returning you to the main menu.\n");
            return;
        }

        try {
            // read the scroll json file from the database
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");

            if (scrolls_array.size() == 0) {
                // return to the caller function
                System.out.println("No scroll to display and remove. Returning to menu.\n");
                return;
            }

            System.out.println("Displaying scroll information");
            for(int i = 0; i < scrolls_array.size(); i++){
                JSONObject scroll_info = (JSONObject) scrolls_array.get(i);

                String date = (String) scroll_info.get("date");
                String uploader_id = (String) scroll_info.get("uploader_id");
                String filename = (String) scroll_info.get("filename");
                String file_id = (String) scroll_info.get("file_id");
                Long version = (Long) scroll_info.get("version");

                if (uploader_id.equals(user.getID())){
                    // show scroll corresponding to the ones
                    // only uploaded by the user
                    System.out.println("date = " + date);
                    System.out.println("uploader id = " + uploader_id);
                    System.out.println("filename = " + filename);
                    System.out.println("file id = " + file_id);
                    System.out.println("version = " + version);
                }
            }

            // select the scroll to remove
            System.out.print("Please select the id of the scroll to remove: ");
            int idx = Scan.scanInteger(1, scrolls_array.size()) - 1;
            JSONObject scroll_info = (JSONObject) scrolls_array.get(idx);

            // keep prompting user for input until it matches his/her id
            while ((Long)scroll_info.get("id") != Long.valueOf(user.getID())){
                System.out.printf("Error, must remove scrolls correspond to your name only: %s\n",user.getName());
                System.out.print("Please select the id of the scroll to remove: ");
                idx = Scan.scanInteger(1, scrolls_array.size()) - 1;
                scroll_info = (JSONObject) scrolls_array.get(idx);
            }

            // remove the digital scroll
            File file = new File(UPLOADED_SCROLLS_PATH + scroll_info.get("filename"));
            if (file.delete()){
                System.out.print(scroll_info.get("filename") + " Successfully deleted\n");
                // remove the scroll_info from the scrolls_array
                scrolls_array.remove(scroll_info);

                // update the scrolls.json file here
                if (updateScrollsJsonDatabase(scrolls_array)){
                    System.out.println("Successfully updated the Scrolls database.\n");
                } else {
                    System.out.println("Scrolls database not found. please check the directory folder/source file.\n");
                }
            }

            // display error message when failing to remove digital scroll
            else {
                System.out.println("Failed. Returning back to the main menu.\n");
            }

        } catch (Exception e){
            System.out.println("No scroll to display and remove. Returning to menu.\n");
        }
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

    public static boolean updateScrollsJsonDatabase(JSONArray scrolls_array){
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