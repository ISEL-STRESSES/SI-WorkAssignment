package pt.isel.ui;

public abstract class Command {

    public final String description;

    Command(String description) {
        this.description = description;
    }

    public abstract void act();
}
