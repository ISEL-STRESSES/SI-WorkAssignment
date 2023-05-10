package pt.isel.ui;

public abstract class ICommand {

    public final String description;

    ICommand(String description) {
        this.description = description;
    }
     public abstract void act();
}
