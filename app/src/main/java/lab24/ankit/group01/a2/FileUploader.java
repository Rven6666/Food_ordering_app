package lab24.ankit.group01.a2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

// For testing
// C:\Users\ethan\Desktop\eeeee.txt

import org.checkerframework.checker.units.qual.A;
public class FileUploader implements LogObserverable {

    private static final String REPOSITORY_PATH = "./src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/";
    private LogObserver logObserver;
    private int id;
    private String filename;

    public FileUploader() {
        this.logObserver = new SystemLog();
        // set id to 0
        this.id = 0;
    }

    public void upload() {
        System.out.print("Enter the path to the file you want to upload: ");
        String filePath = Scan.scanString(null);
        File sourceFile = new File(filePath);

        // Check if the 'uploaded_scrolls' directory exists, if not, create it
        File repositoryDir = new File(REPOSITORY_PATH);
        if (!repositoryDir.exists()) {
            repositoryDir.mkdir();
        }

        File destFile = new File(REPOSITORY_PATH + sourceFile.getName());

        if (!sourceFile.exists()) {
            System.out.println("The specified file does not exist.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            System.out.println("File uploaded successfully!");

            // increment id for the next scroll
            id++;
            notifyObserver("File " + sourceFile.getName() + " uploaded successfully");
            this.filename = sourceFile.getName();

        } catch (IOException e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }
    }

    /**
     * return the id of the file being uploaded
     * @return, the id of the person
     */
    public int getId(){
        return this.id;
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
