package pt.isel.model.entities.player;

import pt.isel.model.entities.player.JogadorEstatistica;
import pt.isel.model.types.Email;

public interface Player {
    //getters
    Integer getId();

    String getUserName();

    Email getEmail();

    String getEstado();

    String getNomeRegiao();

    //setters
    void setUserName(String userName);

    void setEmail(Email email);

    void setEstado(String estado);

    void setNomeRegiao(String nomeRegiao);

    //others
    JogadorEstatistica getJogadorEstatistica();

    void setJogadorEstatistica(PlayerStats jogadorEstatistica);

    boolean isValid();

}
