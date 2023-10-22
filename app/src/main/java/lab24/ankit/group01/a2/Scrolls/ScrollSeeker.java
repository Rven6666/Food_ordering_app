package lab24.ankit.group01.a2.Scrolls;

import lab24.ankit.group01.a2.AppState;

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
    private JSONParser parser = new JSONParser();

    public void viewScroll(){

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
            // file is empty, return to menu
            System.out.println("No Scrolls to view. Returning to menu.");
            return;
        }
    }

    public void previewScroll(){

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

            File fileObj = new File("src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + filename);
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

            System.out.print("Select a scroll to download, specify an id: ");
            int id = Scan.scanInteger(1, scrolls_array.size());
            JSONObject scroll_info = (JSONObject) scrolls_array.get(id-1);
            String source = "src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/" + (String) scroll_info.get("filename");

            System.out.print("Specify path to the directory to save the scroll: ");
            String dir_path = Scan.scanString(null);

            File sourceFile = new File(source);
            File targetDirectory = new File(dir_path);

            if (sourceFile.exists() && sourceFile.isFile() && targetDirectory.isDirectory()) {
                File targetFile = new File(targetDirectory, sourceFile.getName());

                try {
                    Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Successfully downloaded the scroll. Returning back to the menu.\n");
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

            if (scrolls_array.isEmpty()) {
                // throw new exception & return to menu
                throw new Exception();
            }

            System.out.println("\nSearch Filters Menu:");
            System.out.println("1. Search by Uploader name");
            System.out.println("2. Search by Scroll ID");
            System.out.println("3. Search by Name");
            System.out.println("4. Search by Upload Date");
            System.out.println("5. Back to Main Menu\n");

            int choice = Scan.scanInteger(1, 5);
            switch (choice) {
                case 1:
                    System.out.print("Enter Uploader: ");
                    String uploader = Scan.scanString(null);
                    searchAndDisplayFilteredScrolls(scrolls_array, "uploader", uploader);
                    break;
                case 2:
                    System.out.print("Enter Scroll ID: ");
                    int scrollId = Scan.scanInteger(1, scrolls_array.size());
                    searchAndDisplayFilteredScrolls(scrolls_array, "id", scrollId);
                    break;
                case 3:
                    System.out.print("Enter Scroll Name: ");
                    String name = Scan.scanString(null);
                    searchAndDisplayFilteredScrolls(scrolls_array, "filename", name);
                    break;
                case 4:
                    System.out.print("Enter Upload Date (yyyy-MM-dd): ");
                    String uploadDate = Scan.scanString(null);
                    searchAndDisplayFilteredScrolls(scrolls_array, "date", uploadDate);
                    break;
                case 5:
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
                System.out.println("id = " + scroll.get("id"));
                System.out.println("uploader = " + scroll.get("uploader"));
                System.out.println("upload_date = " + scroll.get("date"));
                System.out.println("filename = " + scroll.get("filename") + "\n");
            }
        }
        System.out.println("Returning to the search filters menu.\n");
        searchFilter(); // call the method again
    }

}