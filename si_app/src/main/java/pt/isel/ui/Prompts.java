package pt.isel.ui;

import com.sun.istack.Nullable;
import pt.isel.model.types.Email;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import static pt.isel.model.types.Email.EMAIL_REGEX;

public class Prompts {
    private static final String PROMPT = "> ";

    public static Command promptCommand(Map<String, Command> commands) {
        final String input = prompt(commands::containsKey, "Not a valid Command. Use the `help` command for more information");
        return commands.get(input);
    }

    public static int promptId(String promptMessage) {
        final String input = prompt(in -> true, "");
        return Integer.parseInt(input);
    }

    public static Email promptEmail(String promptMessage) {
        System.out.println(promptMessage);
        return new Email(prompt(input -> input.matches(EMAIL_REGEX), "Incorrect email, please input a correct email."));
    }

        public static String promptUsername(String promptMessage) {
        System.out.println(promptMessage);
        return prompt(input -> input.length() > 1, "Incorrect username, please input a correct username.");
    }

    public static String promptRegion(String promptMessage) {
        System.out.println(promptMessage);
        return prompt(input -> input.length() > 1, "Incorrect region name, please input a correct region name.");
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
        final String cleanString = command.trim();
        if (cleanString.isEmpty() || cleanString.isBlank())
            return null;
        else
            return cleanString;
    }

    private static void printPrompt() {
        System.out.print(PROMPT);
    }
}
