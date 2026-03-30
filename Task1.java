import java.io.*;
import java.util.*;

public class FileHandlingUtility {

    // Method to write data into file
    public static void writeFile(String fileName, String content) {
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(content);
            writer.close();
            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Method to read data from file
    public static void readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            System.out.println("\nFile Content:");
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    // Method to modify file content
    public static void modifyFile(String fileName, String oldWord, String newWord) {
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            StringBuilder content = new StringBuilder();

            while (sc.hasNextLine()) {
                content.append(sc.nextLine()).append("\n");
            }
            sc.close();

            // Replace old word with new word
            String updatedContent = content.toString().replace(oldWord, newWord);

            FileWriter writer = new FileWriter(fileName);
            writer.write(updatedContent);
            writer.close();

            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.out.println("Error modifying file: " + e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) {
        String fileName = "sample.txt";

        // Writing to file
        writeFile(fileName, "Hello Darshan!\nWelcome to CODTECH Internship.\nJava File Handling Task.");

        // Reading file
        readFile(fileName);

        // Modifying file
        modifyFile(fileName, "Java", "Advanced Java");

        // Reading modified file
        readFile(fileName);
    }
}