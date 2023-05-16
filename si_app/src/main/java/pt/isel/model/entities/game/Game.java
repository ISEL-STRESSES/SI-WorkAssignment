package pt.isel.model.entities.game;

import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

public interface Game {
    //Getters
    Alphanumeric getId();

    String getNome();
    URL getUrl();

    //Setters
    void setId(Alphanumeric id);

    void setNome(String name);
    void setUrl(URL url);

}
