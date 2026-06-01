/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author balla
 */
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void waitForEnter(){
        System.out.println("=============================================");
        scanner.nextLine();
    }
    
    public static boolean getYesNoInput() {
        while (true) {
            System.out.print("(Y/N): ");
            String input = scanner.nextLine().trim();

            if (input.length() == 1) {
                char c = Character.toLowerCase(input.charAt(0));

                if (c == 'y') return true;
                if (c == 'n') return false;
            }

            System.out.println("Invalid input. Please enter Y or N.");
        }
    }
}