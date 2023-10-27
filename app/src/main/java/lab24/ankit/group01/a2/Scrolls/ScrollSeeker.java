package lab24.ankit.group01.a2.Scrolls;

import lab24.ankit.group01.a2.UI.AppState;
import lab24.ankit.group01.a2.UI.AppStats;
import lab24.ankit.group01.a2.Miscellaneous.Scan;
import lab24.ankit.group01.a2.User.User;
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
    private static final JSONParser parser = new JSONParser();

    public void displayAllScrolls(User user) {
        JSONArray scrollsArray = getScrollsArray();

        System.out.println();

        if (scrollsArray.size() == 0) {
            // file is empty, return to menu
            System.out.println("No Scrolls to view. Returning to menu.");
            return;
        }

        System.out.println("All Scrolls:");
        for(Object obj : scrollsArray){
            JSONObject scrollInfo = (JSONObject) obj;

            System.out.println("Scroll ID = " + (String) scrollInfo.get("scroll id"));
            System.out.println("Date = " + (String) scrollInfo.get("date"));
            System.out.println("Uploader ID = " + (String) scrollInfo.get("uploader id"));
            System.out.println("Filename = " + (String) scrollInfo.get("filename"));
            System.out.println("Version = " + (String) scrollInfo.get("version"));
            System.out.println();

        }
    }

    public void viewScroll(User user){

        displayAllScrolls(user);

        // guests can only preview a scroll
        if(user == null){
            // guest user
            System.out.println("1. Preview a scroll\n2. Back to main menu");
            int choice = Scan.scanInteger(1, 2);
            switch (choice){
                case 1:
                    previewScroll();
                    break;
                case 2:
                    break;
            }
            return;
        }

        System.out.println("1. Preview a scroll\n2. Download a scroll\n3. See scroll history\n4. Back to main menu");
        int choice = Scan.scanInteger(1, 4);
        switch (choice){
            case 1:
                previewScroll();
                break;
            case 2:
                downloadScroll();
                break;
            case 3:
                scrollHistory();
                break;
            case 4:
                break;
        }

    }

    public static JSONArray getScrollsArray() {

        JSONArray scrolls_array = null;

        try {
            JSONObject scrolls = (JSONObject) parser.parse(new FileReader(SCROLLS_PATH));
            scrolls_array = (JSONArray) scrolls.get("scrolls");

        } catch (Exception e){
            // file is empty, return to menu
            System.out.println("Error opening Scrolls database. Returning to menu.\n");
            System.exit(1);
            return null;
        }

        return scrolls_array;

    }

    public void displayScrollVersionInfo(JSONObject scrollInfo) {

        System.out.println("Version = " + scrollInfo.get("version"));
        System.out.println("Date = " + scrollInfo.get("date"));
        System.out.println("Filename = " + scrollInfo.get("filename"));
        System.out.println();

    }

    

    public void scrollHistory() {

        JSONObject scroll = null;

        while (true) {
            System.out.println("Please enter the id of the scroll you want to view history for: ");
            String scroll_id = Scan.scanString(null);
            if (scroll_id.equals("exit")) return;
            scroll = getScroll(scroll_id);
            if (scroll != null) break;
            System.out.println("Scroll with that id does not exist, please try again, or type 'exit' to return to the menu.");
        }

        System.out.println("Displaying scroll history for scroll id: " + scroll.get("scroll id") + "\n");
        
        JSONArray historyArray = (JSONArray) scroll.get("versions");

        int max_version = 0;

        for (Object obj : historyArray) {
            JSONObject version_info = (JSONObject) obj;
            if (Integer.parseInt((String)version_info.get("version")) > max_version) 
                max_version = Integer.parseInt((String)version_info.get("version"));
            displayScrollVersionInfo(version_info);
        }

        System.out.println("1. Preview a version\n2. Download a version\n3. Back to main menu\n");
        int choice = Scan.scanInteger(1, 3);
        switch (choice) {
            case 1:
                System.out.print("Please select a version to preview: ");
                int version = Scan.scanInteger(0, max_version);
                printScroll(scroll, Integer.toString(version));
                break;
            case 2:
                System.out.print("Please select a version to download: ");
                int version1 = Scan.scanInteger(0, max_version);
                downloadScroll(scroll, Integer.toString(version1));
            case 3:
                break;       
        }

    }

    public static JSONObject getScroll(String id) {

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
            return null;
        }

        for (int i = 0; i < scrolls_array.size(); i++) {
            JSONObject scroll_info = (JSONObject) scrolls_array.get(i);
            String scroll_id = (String) scroll_info.get("scroll id");
            if (scroll_id.equals(id)) {
                return scroll_info;
            }
        }
        System.out.println("Scroll ID does not exist. Please try again.");
        return null;

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

    public void printScroll(JSONObject scroll_info, String version) {

        String scroll_id = (String) scroll_info.get("scroll id");    
        String filename = (String) scroll_info.get("filename");
        if(version==null)
            version = (String) scroll_info.get("version");
        else {
            JSONArray versions = (JSONArray) scroll_info.get("versions");
            for (Object obj : versions) {
                JSONObject version_info = (JSONObject) obj;
                if (version_info.get("version").equals(version)) {
                    filename = (String) version_info.get("filename");
                }
            }
        }

        File fileObj = new File("src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + scroll_id + "/" + filename + "-" + version);
        try {
            Scanner scanner = new Scanner(fileObj);
            if (scanner.hasNextLine()) {
                String firstLine = scanner.nextLine();
                // Print a shortened version of the first line, e.g., the first 50 characters
                int maxLength = Math.min(firstLine.length(), 50);
                System.out.println(firstLine.substring(0, maxLength));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void previewScroll(){

        JSONObject scroll = null;

        while (true) {
            System.out.print("\nPlease enter the id of the scroll you want to preview: ");
            String scroll_id = Scan.scanString(null);
            if (scroll_id.equals("exit")) return;
            scroll = getScroll(scroll_id);
            if (scroll != null) break;
            System.out.println("Scroll with that id does not exist, please try again, or type 'exit' to return to the menu.");
        }

        printScroll(scroll, null);

        // prints newline before menu selection
        AppStats.incrementCount("scroll views");
    }

    public void downloadScroll(JSONObject scroll_info, String version) {
        String scroll_id = (String) scroll_info.get("scroll id");    
        String filename = (String) scroll_info.get("filename");
        if(version==null)
            version = (String) scroll_info.get("version");
        else {
            JSONArray versions = (JSONArray) scroll_info.get("versions");
            for (Object obj : versions) {
                JSONObject version_info = (JSONObject) obj;
                if (version_info.get("version").equals(version)) {
                    filename = (String) version_info.get("filename");
                }
            }
        }
        
        String source = "src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + scroll_id + "/" + filename + "-" + version;

        
        
        System.out.print("Specify path to the directory to save the scroll: ");
        String dir_path = Scan.scanString(null);

        File sourceFile = new File(source);
        File targetDirectory = new File(dir_path);

        
        if (sourceFile.exists() && sourceFile.isFile() && targetDirectory.isDirectory()) {
            File targetFile = new File(targetDirectory, filename);

            try {
                Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Successfully downloaded the scroll. Returning back to the menu.");
                updateScrollDownloads((String)scroll_info.get("scroll id"));
                AppStats.incrementCount("scrolls downloaded");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to download the scroll.\n");
            }
        } else {
            System.out.println(sourceFile.getName());
            System.out.println(targetDirectory.getName());
            System.out.println(targetDirectory.isDirectory());
            System.out.println("Source file does not exist or target directory is invalid.\n");
        }

    }

    public void updateScrollDownloads(String scrollID) {

        JSONArray scrollsArray = ScrollSeeker.getScrollsArray();
        for (Object scroll : scrollsArray) {
            JSONObject scrollObject = (JSONObject) scroll;
            if (scrollObject.get("scroll id").equals(scrollID)) {
                String downloads = (String) scrollObject.get("downloads");
                int downloadsInt = Integer.parseInt(downloads) + 1;
                scrollObject.put("downloads", Integer.toString(downloadsInt));
                break;
            }
        }

        JSONObject finalScrolls = new JSONObject();
        finalScrolls.put("scrolls", scrollsArray);

        try {
            // write to the Scrolls.json file in the database
            FileWriter file = new FileWriter(SCROLLS_PATH);
            file.write(finalScrolls.toJSONString());
            file.close();

        } catch (IOException e){
            // return false
            System.out.println("Error updating scroll downloads");
        }

    }

    public void downloadScroll(){

        JSONArray scrolls_array = getScrollsArray();

        if (scrolls_array.isEmpty()){
            System.out.println("There are no scrolls available to download sorry!");
            return;
        }

        JSONObject scroll = null;
        while (true) {
            System.out.print("Please enter the id of the scroll you want to download: ");
            String scrollID = Scan.scanString(null);
            if (scrollID.equals("exit")) return;
            scroll = getScroll(scrollID);
            if (scroll != null) break;
            System.out.println("Scroll with that id does not exist, please try again, or type 'exit' to return to main menu.");
        }

        downloadScroll(scroll, null);

    }

    public void searchFilter(){

        // list of all the choices
        String[] choices = {"Uploader ID", "Scroll ID", "Filename", "Upload Date", "Return to Main Menu"};
        
        // getting user choice
        System.out.println("\n Search Filters Menu: ");
        for (int i = 0; i < choices.length; i++) {
            System.out.println((i + 1) + ". " + choices[i]);
        }
        System.out.print("Enter your choice: ");
        int choice = Scan.scanInteger(1, choices.length);

        if (choice == choices.length) {
            System.out.println("Returning to the main menu.\n");
            return;
        }

        // getting filter
        String filter = choices[choice - 1].toLowerCase();

        System.out.print("Please enter the " + filter + " you would like to search for: ");
        String filterValue = Scan.scanString(null);

        JSONArray scrollsArray = getScrollsArray();

        searchAndDisplayFilteredScrolls(scrollsArray, filter, filterValue);

    }

    private void searchAndDisplayFilteredScrolls(List<JSONObject> scrolls, String filterKey, Object filterValue) {
        List<JSONObject> filteredScrolls = scrolls.stream()
                .filter(scroll -> filterValue.equals(scroll.get(filterKey)))
                .collect(Collectors.toList());

        System.out.println();

        if (filteredScrolls.isEmpty()) {
            System.out.println("No matching scrolls found.");
        } else {
            System.out.println("Matching Scrolls:");
            for (JSONObject scroll : filteredScrolls) {
                System.out.println("date = " + scroll.get("date"));
                System.out.println("uploader id = " + scroll.get("uploader id"));
                System.out.println("filename = " + scroll.get("filename"));
                System.out.println("scroll id = " + scroll.get("scroll id"));
                System.out.println("version = " + scroll.get("version") + "\n");
            }
        }
        System.out.println("Returning to the search filters menu.\n");
        searchFilter(); // call the method again
    }

}