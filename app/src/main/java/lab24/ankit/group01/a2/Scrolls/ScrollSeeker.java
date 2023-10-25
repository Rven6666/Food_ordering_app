package lab24.ankit.group01.a2.Scrolls;

import lab24.ankit.group01.a2.AppState;
import lab24.ankit.group01.a2.AppStats;
import lab24.ankit.group01.a2.Scan;
import lab24.ankit.group01.a2.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ScrollSeeker implements AppState {
    private static final String SCROLLS_PATH = "src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
    private static final String USERS_PATH = "src/main/java/lab24/ankit/group01/a2/Databases/UserList.json";
    private JSONParser parser = new JSONParser();

    public void viewScroll(){

        JSONArray scrolls_array = null;

        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            scrolls_array = (JSONArray) scrolls.get("scrolls");

            if (scrolls_array.isEmpty()){
                // throw new exception & return to menu
                throw new Exception();
            }
        } catch (Exception e){
            // file is empty, return to menu
            System.out.println("No Scrolls to view. Returning to menu.");
            return;
        }

        for(int i = 0; i < scrolls_array.size(); i++){
            JSONObject scroll_info = (JSONObject) scrolls_array.get(i);

            String date = (String) scroll_info.get("date");
            String uploader_id = (String) scroll_info.get("uploader_id");
            String filename = (String) scroll_info.get("filename");
            String file_id = (String) scroll_info.get("file_id");
            Long version = (Long) scroll_info.get("version");

            System.out.println("date = " + date);
            System.out.println("uploader id = " + uploader_id);
            System.out.println("filename = " + filename);
            System.out.println("file id = " + file_id);
            System.out.println("version = " + version);
            System.out.println();
        }

        System.out.println("1. Preview a scroll\n2. Download a scroll\n3. See scroll history\n.4. Back to main menu\n");
        int choice = Scan.scanInteger(1, 4);
        switch (choice){
            case 1:
                previewScroll();
                break;
            case 2:
                downloadScroll(null);
                break;
            case 3:
                scrollHistory();
                break;
            case 4:
                break;
        }

    }

    public void scrollHistory() {

    }

    public boolean checkScrollIdExists(String id) {
        
        JSONArray scrolls_array = null;
        
        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            scrolls_array = (JSONArray) scrolls.get("scrolls");

            if (scrolls_array.isEmpty()){
                // throw an exception & return to menu
                throw new Exception();
            }

        } catch (Exception e){
            // file is empty, return to menu
            System.out.println("No Scrolls to preview. Returning to menu.\n");
            return false;
        }

        for (int i = 0; i < scrolls_array.size(); i++) {
            JSONObject scroll_info = (JSONObject) scrolls_array.get(i);
            String scroll_id = (String) scroll_info.get("scroll id");
            if (scroll_id.equals(id)) {
                return true;
            }
        }
        System.out.println("Scroll ID does not exist. Please try again.");
        return false;

    }

    public void previewScroll(){

        String scroll_id = null;

        while (true) {
            System.out.println("Please enter the id of the scroll you want to preview: ");
            scroll_id = Scan.scanString(null);
            if (scroll_id.equals("exit")) return;
            if (checkScrollIdExists(scroll_id)) break;
            System.out.println("Scroll with that id does not exist, please try again, or type 'exit' to return to the menu.");
        }

        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");

            if (scrolls_array.isEmpty()){
                // throw new exception & return to menu
                throw new Exception();
            }

            System.out.print("Please select a scroll id to preview: ");
            int id = Scan.scanInteger(1, scrolls_array.size());
            JSONObject scroll_info = (JSONObject) scrolls_array.get(id-1);
            // open the file
            String filename = (String) scroll_info.get("filename");
            String version = "-" + scroll_info.get("version");

            File fileObj = new File("src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + filename + version);
            Scanner scanner = new Scanner(fileObj);
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                // Print a shortened version of the first line, e.g., the first 50 characters
                int maxLength = Math.min(firstLine.length(), 50);
                System.out.println(firstLine.substring(0, maxLength));
            }

        } catch (Exception e){
            // file is empty, returning to menu
            System.out.println("No Scrolls to preview. Returning to menu.\n");
            return;
        }

        // prints newline before menu selection
        AppStats.incrementCount("scroll views");
        System.out.println();
    }

    public void downloadScroll(User user){
        /* Guest User are not allowed to download scrolls to their system */
        if (user == null){
            System.out.println("We are sorry. Guest User are not allowed to download scrolls to their system.");
            System.out.println("Returning you to the main menu.\n");
            return;
        }

        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");

            if (scrolls_array.isEmpty()){
                // throw new exception & return to menu
                throw new Exception();
            }

            System.out.println("Displaying scroll information");
            for(int i = 0; i < scrolls_array.size(); i++){
                JSONObject scroll_info = (JSONObject) scrolls_array.get(i);
                String date = (String) scroll_info.get("date");
                String uploader_id = (String) scroll_info.get("uploader_id");
                String filename = (String) scroll_info.get("filename");
                String file_id = (String) scroll_info.get("file_id");
                Long version = (Long) scroll_info.get("version");

                // Print scroll information in the desired format
                System.out.println("date = " + date);
                System.out.println("uploader id = " + uploader_id);
                System.out.println("filename = " + filename);
                System.out.println("file id = " + file_id);
                System.out.println("version = " + version);
                System.out.println();
            }

            System.out.print("Select a scroll to download, specify an id: ");
            int id = Scan.scanInteger(1, scrolls_array.size());
            JSONObject scroll_info = (JSONObject) scrolls_array.get(id-1);
            String filename = (String) scroll_info.get("filename");
            String version = "-" + scroll_info.get("version");
            String source = "src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + filename + version;

            System.out.print("Specify path to the directory to save the scroll: ");
            String dir_path = Scan.scanString(null);

            File sourceFile = new File(source);
            File targetDirectory = new File(dir_path);

            if (sourceFile.exists() && sourceFile.isFile() && targetDirectory.isDirectory()) {
                File targetFile = new File(targetDirectory, filename);

                try {
                    Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Successfully downloaded the scroll. Returning back to the menu.\n");
                    AppStats.incrementCount("scrolls downloaded");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to download the scroll.\n");
                }
            } else {
                System.out.println("Source file does not exist or target directory is invalid.\n");
            }

        } catch (Exception e){
            // file is empty, return to menu
            System.out.println("No Scrolls to download (Either Failed/Empty Scrolls database). Returning to menu.\n");
            return;
        }

    }

    public void searchFilter(){
        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            JSONArray scrolls_array = (JSONArray) scrolls.get("scrolls");
            JSONObject userList = (JSONObject) parser.parse(new FileReader(USERS_PATH));
            JSONArray users = (JSONArray) userList.get("users");

            if (scrolls_array.isEmpty()) {
                // throw new exception & return to menu
                throw new Exception();
            }

            System.out.println("\nSearch Filters Menu:");
            System.out.println("1. Search by Uploader ID");
            System.out.println("2. Search by Scroll ID");
            System.out.println("3. Search by Name");
            System.out.println("4. Search by Upload Date");
            System.out.println("5. Search by version");
            System.out.println("6. Back to Main Menu\n");

            int choice = Scan.scanInteger(1, 6);
            switch (choice) {
                case 1:
                    System.out.print("Enter Uploader ID: ");
                    String uploader_id = Integer.toString(Scan.scanInteger(1, users.size()));
                    System.out.println();
                    searchAndDisplayFilteredScrolls(scrolls_array, "uploader_id", uploader_id);
                    break;
                case 2:
                    System.out.print("Enter Scroll ID: ");
                    String scrollId = Integer.toString(Scan.scanInteger(1, scrolls_array.size()));
                    System.out.println();
                    searchAndDisplayFilteredScrolls(scrolls_array, "file_id", scrollId);
                    break;
                case 3:
                    System.out.print("Enter Scroll Name: ");
                    String name = Scan.scanString(null);
                    System.out.println();
                    searchAndDisplayFilteredScrolls(scrolls_array, "filename", name);
                    break;
                case 4:
                    System.out.print("Enter Upload Date (yyyy-MM-dd): ");
                    String uploadDate = Scan.scanString(null);
                    System.out.println();
                    searchAndDisplayFilteredScrolls(scrolls_array, "date", uploadDate);
                    break;
                case 5:
                    System.out.print("Search by version: ");
                    Long version = Long.valueOf(Scan.scanInteger(0, Integer.MAX_VALUE));
                    System.out.println();
                    searchAndDisplayFilteredScrolls(scrolls_array, "version", version);
                case 6:
                    System.out.println("Returning to the main menu.\n");
                    break;
            }
        }
        catch(Exception e){
            // file is empty, cancel search and return to menu
            System.out.println("No Scrolls to search. Returning to menu.\n");
            return;
        }
    }

    private void searchAndDisplayFilteredScrolls(List<JSONObject> scrolls, String filterKey, Object filterValue) {
        List<JSONObject> filteredScrolls = scrolls.stream()
                .filter(scroll -> filterValue.equals(scroll.get(filterKey)))
                .collect(Collectors.toList());

        if (filteredScrolls.isEmpty()) {
            System.out.println("No matching scrolls found.");
        } else {
            System.out.println("Matching Scrolls:");
            for (JSONObject scroll : filteredScrolls) {
                System.out.println("date = " + scroll.get("date"));
                System.out.println("uploader id = " + scroll.get("uploader_id"));
                System.out.println("filename = " + scroll.get("filename"));
                System.out.println("file id = " + scroll.get("file_id"));
                System.out.println("version = " + scroll.get("version") + "\n");
            }
        }
        System.out.println("Returning to the search filters menu.\n");
        searchFilter(); // call the method again
    }

}