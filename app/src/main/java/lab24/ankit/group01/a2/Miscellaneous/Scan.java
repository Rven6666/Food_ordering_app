package lab24.ankit.group01.a2.Miscellaneous;

import java.util.Scanner;
import java.util.List;

public class Scan {

    static Scanner scan = new Scanner(System.in);

    public static int scanInteger(int min, int max) {
        // getting user integer input
        int response = 0;
        while (true) {
            try {
                response = Integer.parseInt(scan.nextLine().strip());
                if (response >= min && response <= max)
                    break;
                else
                    throw new Exception();
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter another value: ");
            }
        }
        return response;
    }

    public static String scanString(List<String> options) {
        String response = "";
        while (true) {
            response = scan.nextLine().strip();
            if (options == null && response.length() > 0) {
                break;
            }
            else if (options != null && options.contains(response))
                break;
            else
                System.out.print("Invalid input. Please enter another value: ");
        }
        return response;
    }
    
}
