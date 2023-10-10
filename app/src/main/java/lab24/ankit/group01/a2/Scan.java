package lab24.ankit.group01.a2;

import java.util.Scanner;
import java.util.List;

public class Scan {

    public static int scanInteger(int min, int max) {
        // getting user integer input
        Scanner scan = new Scanner(System.in);
        int response = 0;
        while (true) {
            try {
                response = Integer.parseInt(scan.nextLine().strip());
                System.out.flush();
                if (response >= min && response <= max)
                    break;
                else
                    throw new Exception();
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter another value: ");
            }
        }
        scan.close();
        return response;
    }

    public static String scanString(List<String> options) {
        Scanner scan = new Scanner(System.in);
        String response = "";
        while (true) {
            response = scan.nextLine().strip();
            if (options == null || options.contains(response))
                break;
            else
                System.out.print("Invalid input. Please enter another value: ");
        }
        scan.close();
        return response;
    }
    
}
