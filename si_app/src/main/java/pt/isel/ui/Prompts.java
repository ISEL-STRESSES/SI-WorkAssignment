package pt.isel.ui;

import com.sun.istack.Nullable;
import pt.isel.model.types.Email;

import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import static pt.isel.model.types.Email.EMAIL_REGEX;

/**
 * Represents a prompt
 */
public class Prompts {
    private static final String PROMPT = "> ";

    /**
     * Prompts the user for a command
     * @param commands the commands
     * @return the command
     */
    public static Command promptCommand(Map<String, Command> commands) {
        final String input = prompt(commands::containsKey, "Not a valid Command. Use the `help` command for more information");
        return commands.get(input);
    }

    /**
     * Gets the prompt id
     * @param promptMessage the prompt message
     * @return the prompt id
     */
    public static int promptId(String promptMessage) {
        final String input = prompt(in -> true, "");
        return Integer.parseInt(input);
    }

    /**
     * Gets the prompt email
     * @param promptMessage the prompt message
     * @return the prompt email
     */
    public static Email promptEmail(String promptMessage) {
        System.out.println(promptMessage);
        return new Email(prompt(input -> input.matches(EMAIL_REGEX), "Incorrect email, please input a correct email."));
    }

    /**
     * Gets the prompt username
     * @param promptMessage the prompt message
     * @return the prompt username
     */
    public static String promptUsername(String promptMessage) {
        System.out.println(promptMessage);
        return prompt(input -> input.length() > 1, "Incorrect username, please input a correct username.");
    }

    /**
     * Gets the prompt region
     * @param promptMessage the prompt message
     * @return the prompt region
     */
    public static String promptRegion(String promptMessage) {
        System.out.println(promptMessage);
        return prompt(input -> input.length() > 1, "Incorrect region name, please input a correct region name.");
    }

    /**
     * Creates a prompt
     * @param verification the verification
     * @param failMessage the fail message
     * @return the prompt
     */
    private static String prompt(Function<String, Boolean> verification, @Nullable String failMessage) {
        while (true) {
            printPrompt();
            final String rawInput = new Scanner(System.in).nextLine();
            final String input = parseString(rawInput);
            if (verification.apply(input)) {
                return input;
            } else {
                if (failMessage != null)
                    System.out.println(failMessage);
            }
        }
    }

    /**
     * Parses a string
     * @param command the command
     * @return the parsed string
     */
    public static String parseString(String command) {
        final String cleanString = command.trim();
        if (cleanString.isEmpty() || cleanString.isBlank())
            return null;
        else
            return cleanString;
    }

    /**
     * Prints the prompt
     */
    private static void printPrompt() {
        System.out.print(PROMPT);
    }
}
