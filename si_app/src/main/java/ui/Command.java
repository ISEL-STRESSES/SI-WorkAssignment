package ui;

/**
 * Represents a command
 */
public abstract class Command {

    /**
     * The command description
     */
    public final String description;

    /**
     * Builds a command with the given description
     *
     * @param description the command description
     */
    Command(String description) {
        this.description = description;
    }

    /**
     * Executes the command
     */
    public abstract void act();
}
