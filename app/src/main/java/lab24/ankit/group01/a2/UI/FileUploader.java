package lab24.ankit.group01.a2.UI;

import java.io.*;
import java.time.LocalDate;

import lab24.ankit.group01.a2.Logging.LogObserver;
import lab24.ankit.group01.a2.Logging.LogObserverable;
import lab24.ankit.group01.a2.Logging.SystemLog;
import lab24.ankit.group01.a2.Miscellaneous.Scan;
import lab24.ankit.group01.a2.User.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lab24.ankit.group01.a2.Scrolls.ScrollSeeker;

public class FileUploader implements LogObserverable, AppState {
    private static final String REPOSITORY_PATH = "./src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/";
    private final String SCROLLS_PATH = "src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
    private final LogObserver logObserver = new SystemLog();
    private final User user;
    private String filename;

    public FileUploader(User user) {
        this.user = user;
    }

    public void upload() {

        File sourceFile = null;

        while (sourceFile == null) {
            System.out.print("\nEnter the path to the file you want to upload: ");
            String filePath = Scan.scanString(null);
            sourceFile = new File(filePath);
            if (!sourceFile.exists()) {
                System.out.println("The specified file does not exist.");
                sourceFile = null;
            }
            else if(!isBinaryFile(sourceFile)) {
                System.out.println("The file is not a binary file.");
                return;
            }
        }
       
        // Check if the 'uploaded_scrolls' directory exists, if not, create it
        File repositoryDir = new File(REPOSITORY_PATH);
        if (!repositoryDir.exists()) {
            repositoryDir.mkdir();
        }

        // create a new folder to store the file and all its versions if edited
        String scroll_id = getNextScrollID();
        String folder_path = REPOSITORY_PATH + scroll_id + "/";
        File fileDir = new File(folder_path);
        fileDir.mkdir();

        File destFile = new File(folder_path + sourceFile.getName() + "-0");

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("File uploaded successfully!");

            notifyObserver("File " + sourceFile.getName() + " uploaded successfully");
            this.filename = sourceFile.getName();

        } catch (IOException e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }

        updateScrolls();

    }

    public String getNextScrollID() {

        JSONArray scrollsArray = ScrollSeeker.getScrollsArray();

        int max = 0;
        for (Object obj : scrollsArray){
            JSONObject scroll = (JSONObject) obj;
            int id = Integer.parseInt(scroll.get("scroll id").toString());
            if (id > max)
                max = id;
        }

        return Integer.toString(max + 1);
    }

    public void updateScrolls() {
        // record the uploaded file information
        String path = "src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
        File jsonFile = new File(path);

        if (!jsonFile.exists()){
            // File does not exist, not considered empty
            System.out.println("File does not exist.");
            return;
        }

        JSONArray versions = new JSONArray();
        JSONObject first_version = new JSONObject();
        first_version.put("version", "0");
        first_version.put("date", getDate());
        first_version.put("filename", getFilename());
        versions.add(first_version);

        JSONObject scroll_info = new JSONObject();
        scroll_info.put("filename", getFilename());
        scroll_info.put("scroll id", getNextScrollID());
        scroll_info.put("uploader id", user.getUserID());
        scroll_info.put("uploader real id", user.getID());
        scroll_info.put("date", getDate());
        scroll_info.put("version", "0");
        scroll_info.put("downloads", "0");
        scroll_info.put("versions", versions);

        JSONObject scrolls = new JSONObject();
        JSONArray scrollsArray = ScrollSeeker.getScrollsArray();

        scrollsArray.add(scroll_info);
        scrolls.put("scrolls", scrollsArray);

        // write to the json file
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(scrolls.toJSONString());
            fileWriter.close();
            AppStats.incrementCount("scrolls uploaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isBinaryFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int ch;
            while ((ch = br.read()) != -1) {
                if (ch != '0' && ch != '1' && ch != '\r' && ch != '\n') {

                    // If character is not 0, 1, or a line break, it's not a binary file

                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    /**
     * return the filename of the file being uploaded
     * @return, String representation of the filename
     */
    public String getFilename(){
        return this.filename;
    }

    /**
     * return the day of the file being uploaded
     * @return, string representation of the date
     * of upload
     */
    public String getDate(){
        return LocalDate.now().toString();
    }

    @Override
    public void notifyObserver(String message) {
        this.logObserver.updateLog("FileObserver", message);
    }

}
