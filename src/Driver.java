import java.util.Random;
public class Driver {
    public static void main(String[] args) {
        // ANSI escape codes for colors
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String white = "\u001B[37m";
        String purple = "\u001B[35m";
        String orange = "\u001B[33m";

        // Define the size of the square
        int size = 5;

        // Loop through rows
        for (int i = 0; i < size; i++) {
            // Loop through columns
            for (int j = 0; j < size; j++) {
                // Print colored square
                System.out.print(red + "■ " + reset);
                System.out.print(blue + "■ " + reset);
            }
            // Move to the next line
            System.out.println();
        }
    }
}
