package pt.isel.ui;

import com.sun.istack.Nullable;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Prompts {
    private static final String PROMPT = "> ";

    public static Command promptCommand(Map<String, Command> commands) {
        final String input = prompt(commands::containsKey, "Not a valid Command. Use the `help` command for more information");
        return commands.get(input);
    }

    // TODO: move this regex to a proper restriction class
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static String promptEmail(String promptMessage) {
        System.out.println(promptMessage);
        return prompt(input -> input.matches(EMAIL_REGEX), "Incorrect email, please input a correct email.");
    }

    private static String prompt(Function<String, Boolean> verification) {
        return prompt(verification, null);
    }

    private static String prompt(Function<String, Boolean> verification, @Nullable String failMessage) {
        while(true) {
            printPrompt();
            final String rawInput = new Scanner(System.in).nextLine();
            final String input = parseString(rawInput);
            if(verification.apply(input)) {
                return input;
            } else {
                if(failMessage != null)
                    System.out.println(failMessage);
            }
        }
    }

    public static String parseString(String command) {
        String cleanString = command.trim();
        if (cleanString.isEmpty() || cleanString.isBlank())
            return null;
        else return cleanString;
    }

    private static void printPrompt() {
        System.out.print(PROMPT);
    }
}
