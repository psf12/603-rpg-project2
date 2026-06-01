package com.mycompany.rpg.ui;

import java.util.Scanner;

/**
 * Console (CUI) implementation of {@link GameView}.
 *
 * Preserves the original Project 1 text-based behaviour: output goes to
 * System.out and input is read from the keyboard via a Scanner. This is the
 * default view used by {@link GameIO}.
 *
 * @author balla
 */
public class ConsoleView implements GameView {

    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void show(String message) {
        System.out.println(message);
    }

    @Override
    public boolean askYesNo() {
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

    @Override
    public void waitForContinue() {
        System.out.println("=============================================");
        scanner.nextLine();
    }

    @Override
    public String askText(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    @Override
    public void showImage(String imageName) {
        // Images are a graphical concern; the console view has nothing to show.
    }
}
