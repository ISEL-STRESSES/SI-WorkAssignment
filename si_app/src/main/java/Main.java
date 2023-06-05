import ui.Command;
import ui.Commands;

import java.util.Map;

import static ui.Prompts.promptCommand;

class Main {
    public static void main(String[] args) {
        try {
            Map<String, Command> commands = Commands.buildCommands();

            while (true) {
                System.out.println("Please input a command");
                commands.get("help").act();
                final Command command = promptCommand(commands);
                System.out.println("Executing...");
                command.act();
            }
        } catch (Exception e) {
            System.err.println("An Uncaught error has been thrown");
            System.err.println(e.getMessage());
        } finally {
            System.out.println("Shutting down...");
        }
    }
}