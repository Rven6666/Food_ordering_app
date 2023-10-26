package lab24.ankit.group01.a2.Logging;

import lab24.ankit.group01.a2.Logging.LogObserver;

import java.io.FileWriter;
import java.io.BufferedWriter;

public class SystemLog implements LogObserver {
    
    private BufferedWriter writer;
    
    public SystemLog() {
        // creating the bufferedwriter to write to the log when notified
        try {
            this.writer = new BufferedWriter(new FileWriter("src/main/java/lab24/ankit/group01/a2/Databases/Log.txt", true));
        } catch (Exception e) {
            System.out.println("Cannot find Log.txt in databases folder");
            System.exit(1);
        }
    }
    
    public void updateLog(String from, String logEntry) {
        // getting the current date and time
        String dateTime = java.time.LocalDateTime.now().toString();
        String[] parts = dateTime.split("T");
        dateTime = parts[0] + " " + parts[1].substring(0, 8);
        
        // adding the date and time to the log entry
        logEntry = dateTime + " [" + from + "]: " + logEntry;

        // writing to the log
        try {
            writer.write(logEntry);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println("Cannot write to Log.txt");
            System.exit(1);
        }
    }

}
