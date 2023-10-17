package lab24.ankit.group01.a2.Scrolls;
import lab24.ankit.group01.a2.Scan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScrollSeeker{
    private String filepath = "/Users/obie/Desktop/SOFT2412/Assignments/A2/Lab24-Ankit-Group01-A2/app/src/main/java/lab24/ankit/group01/a2/Databases/bin_01.txt";

    public String convertStringToBinary(String input){
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")       // zero pads
            );
        }
        return result.toString();
    }

    /**
     * right now we are reading it from bin_01.txt
     * @return
     */
    public String previewScroll(String input){
        String raw = Arrays.stream(input.split(" "))
                .map(binary -> Integer.parseInt(binary, 2))
                .map(Character::toString)
                .collect(Collectors.joining()); // cut the space
        return raw;
    }

    public void writeToFile(String content){
        try {
            FileWriter fileWriter = new FileWriter(filepath);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public String splitBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return result.stream().collect(Collectors.joining(separator));
    }


    public static void main(String[] args) {
        ScrollSeeker seeker = new ScrollSeeker();
        System.out.print("Enter a message to print to the screen: ");
        String bin = seeker.splitBinary(seeker.convertStringToBinary(Scan.scanString(null)), 8, " ");
        System.out.printf("Binary representation: %s\n", bin);
        seeker.writeToFile(bin);
        System.out.printf("Original message after decodiing: %s\n", seeker.previewScroll(bin));
    }
}