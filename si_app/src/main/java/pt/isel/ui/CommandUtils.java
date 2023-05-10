package pt.isel.ui;

import java.util.Scanner;

public class CommandUtils {

    String prompInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            final String input = new Scanner(System.in).nextLine();
            // TODO: add some input error checking
            return input;
        }
    }

}
