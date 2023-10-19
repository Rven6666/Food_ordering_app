package lab24.ankit.group01.a2;

import lab24.ankit.group01.a2.Scrolls.ScrollSeeker;
import lab24.ankit.group01.a2.User_types.User;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Menu implements LogObserverable {

    private LogObserver logObserver;
    private FileUploader uploader = new FileUploader();
    private Login login;
    private ScrollSeeker seeker;

    public Menu() {
        this.logObserver = new SystemLog();
        this.login = new Login();
        this.seeker = new ScrollSeeker();
    }

    public void menuIntro(User user){
        String name = "Guest";
        if (user != null){
            name = user.getName();
        }
        System.out.println("Hello "+ name +". I am VSAS, the Virtual Scroll Access System.\n"
                            +"The portal to the realm of digital wisdom!\n"
                            +"I await your selection:");
    }

    public int showMenu(User user){
        //Menu display
        while (true){
            if(user == null){
                System.out.println("1. View Scrolls\n2. Exit");
                int selection = Scan.scanInteger(1, 2);
                if (selection == 2){
                    System.out.println("Good luck anonymous member of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }else if(!user.isAdmin()){
                System.out.println("1. View Scrolls\n2. Add Scrolls\n3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. Change username\n"
                                +"7. Change password\n8. Exit");
                int selection = Scan.scanInteger(1, 8);
                if (selection == 8){
                    System.out.print("Good luck member of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }else if (user.isAdmin()){
                                System.out.println("1. View Scrolls\n2. Add Scrolls\n"
                                +"3. Edit Scrolls\n4. Recieve Scrolls\n5. Search Scrolls\n6. " 
                                +"Create new Users\n7. View users\n8. Delete users\n9. View App stats\n10. Change username\n"
                                +"11. Change password\n12. Exit");
                int selection = Scan.scanInteger(1, 12);
                if (selection == 12){
                    System.out.print("Good luck Admin of the Whiskers!");
                    System.exit(0);
                }else{
                    return selection;
                }
            }
        }
    }

    public void menuSelection(int selection, User user){


        if(user != null && !user.isAdmin()){
            if(selection == 6){
                selection = 10;
            }else if (selection == 7){
                selection = 11;
            }
        }

        switch (selection){
            case 1: //preview/view scrolls
                seeker.viewScroll();
                seeker.previewScroll();
                break;
            case 2: // Add scrolls
                if (user != null) {
                    // upload the file
                    uploader.upload();

                    // record the uploaded file information
                    String path = "/Users/obie/Desktop/SOFT2412/Assignments/A2/Lab24-Ankit-Group01-A2/app/src/main/java/lab24/ankit/group01/a2/Databases/Scrolls.json";
                    File jsonFile = new File(path);
                    JSONObject scrolls = new JSONObject();
                    JSONArray scroll_array = new JSONArray();
                    JSONObject scroll_info = new JSONObject();

                    if (jsonFile.length() == 0){
                        // write to the empty json file
                        scroll_info.put("id", uploader.getId());
                        scroll_info.put("filename", uploader.getFilename());
                        scroll_info.put("uploader", user.getName());
                        scroll_info.put("date", uploader.getDate());
                        scroll_array.add(scroll_info);
                        scrolls.put("scrolls", scroll_array);

                    } else {
                        try {
                            // read the existing json array and write to it
                            scrolls = (JSONObject) new JSONParser().parse(new FileReader(path));
                            scroll_array = (JSONArray) scrolls.get("scrolls");
                            scroll_info.put("id", uploader.getId());
                            scroll_info.put("filename", uploader.getFilename());
                            scroll_info.put("uploader", user.getName());
                            scroll_info.put("date", uploader.getDate());
                            scroll_array.add(scroll_info);
                            scrolls.put("scrolls", scroll_array);

                        } catch (Exception e){
                            System.err.println(e);
                        }
                    }

                    // write to the json file
                    try {
                        FileWriter file = new FileWriter(path);
                        file.write(scrolls.toJSONString());
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else {
                    System.out.println("You must be logged in to upload files.");
                }
                break;
            case 3: //Edit Scrolls
                System.out.println("CALL CLASS - To be done - EDIT scrolls");
                break;
            case 4: //recieve Scrolls (download)
                System.out.println("CALL CLASS - To be done - DOWNLOAD scrolls");
                break;
            case 5://Search Scrolls
                System.out.println("CALL CLASS - To be done - SEARCH scrolls");
                break;
            case 7:
                System.out.println("CALL CLASS - To be done - VIEW Users");
                break;
            case 8:
                System.out.println("CALL CLASS - To be done - DELETE users");
                break;
            case 9:
                System.out.println("CALL CLASS - To be done - VIEW app stats");
                break;
            case 10:
                login.changeLoginDetails(user.getUsername(),"username");
                break;
            case 11:
                login.changeLoginDetails(user.getUsername(),"password");
                break;
        }
     }

     @Override
     public void notifyObserver(String message) {
         this.logObserver.updateLog("Menu", message);
     }
    
}