package pt.isel;

import pt.isel.ui.Commands;
import pt.isel.ui.ICommand;

import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
         try {
            Map<String, ICommand> commands = Commands.buildCommands();

            while(true) {
                final ICommand command = promptCommand(commands);
                command.act();
            }
         } catch(Exception e) {
            System.err.println("An Uncaught error has been thrown");
            System.err.println(e.getMessage());
            System.err.println("----------------------------------");
            e.printStackTrace(System.out);
         } finally {
             System.out.println("Shutting down...");
         }
    }

    public static ICommand promptCommand(Map<String, ICommand> commands) {
        while (true) {
            printPrompt();
            String input = parseString(new Scanner(System.in).nextLine());
            if (input == null)
                continue;

            final ICommand command = commands.get(input);
            if(command == null) {
                System.out.println("Invalid Command, use help");
                continue;
            }
            return command;
        }
    }

    public static String parseString(String command) {
        String cleanString = command.trim();
        if (cleanString.isEmpty() || cleanString.isBlank())
            return null;
        else return cleanString;
    }

    public static void printPrompt() {
        System.out.print("> ");
    }
}