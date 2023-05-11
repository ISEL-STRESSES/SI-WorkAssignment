package pt.isel;

import pt.isel.ui.Commands;
import pt.isel.ui.Command;

import java.util.Map;

import static pt.isel.ui.Prompts.promptCommand;

class Main {
    public static void main(String[] args) {
         try {
             Map<String, Command> commands = Commands.buildCommands();

             while (true) {
                 final Command command = promptCommand(commands);
                 System.out.println("Executing...");
                 command.act();
             }
         } catch(Exception e) {
            System.err.println("An Uncaught error has been thrown");
            System.err.println(e.getMessage());
         } finally {
             System.out.println("Shutting down...");
         }
    }
}