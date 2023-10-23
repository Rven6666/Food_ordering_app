package lab24.ankit.group01.a2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;


import org.checkerframework.checker.units.qual.A;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FileUploader implements LogObserverable, AppState {
    private static final String REPOSITORY_PATH = "./src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/";
    private final LogObserver logObserver = new SystemLog();
    private final User user;
    private String filename;

    public FileUploader(User user) {
        this.user = user;
    }

    public void upload() {

        File sourceFile = null;

        while (sourceFile == null) {
            System.out.print("Enter the path to the file you want to upload: ");
            String filePath = Scan.scanString(null);
            sourceFile = new File(filePath);
            if (!sourceFile.exists()) {
                System.out.println("The specified file does not exist.");
                sourceFile = null;
            }
        }
       
        // Check if the 'uploaded_scrolls' directory exists, if not, create it
        File repositoryDir = new File(REPOSITORY_PATH);
        if (!repositoryDir.exists()) {
            repositoryDir.mkdir();
        }

        File destFile = new File(REPOSITORY_PATH + sourceFile.getName());
        

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

    public void updateScrolls() {
        // record the uploaded file information
        String path = "src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
        File jsonFile = new File(path);

        if (!jsonFile.exists()){
            // File does not exist, not considered empty
            System.out.println("File does not exist.");
            return;
        }

        JSONObject scroll_info = new JSONObject();
        scroll_info.put("id", getId());
        scroll_info.put("filename", getFilename());
        scroll_info.put("uploader", user.getName());
        scroll_info.put("date", getDate());

        JSONObject scrolls = new JSONObject();

        try {
            scrolls = (JSONObject) new JSONParser().parse(new FileReader(path));
            ((JSONArray)scrolls.get("scrolls")).add(scroll_info);
        } catch (Exception e){
            // we write to the scroll_array scroll_info
            // as the current scroll database are empty
            JSONArray scroll_array = new JSONArray();
            scroll_array.add(scroll_info);
            scrolls.put("scrolls", scroll_array);
        }

        // write to the json file
        try {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(scrolls.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return the id of the file being uploaded
     * @return, the id of the person
     */
    public int getId(){
        // get the latest id for the scroll they've uploaded or something.
        return Integer.valueOf(user.getID());
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
