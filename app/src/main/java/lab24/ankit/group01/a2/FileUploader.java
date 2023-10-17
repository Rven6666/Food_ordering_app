package lab24.ankit.group01.a2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
// For testing
// C:\Users\ethan\Desktop\eeeee.txt
public class FileUploader {

    private static final String REPOSITORY_PATH = "./src/main/java/lab24/ankit/group01/a2/uploaded_scrolls/";

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

        } catch (IOException e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }
    }
}
