package pt.isel.model;

import pt.isel.model.types.Email;

public interface Player {
    //getters
    Integer getId();

    String getNome();

    Email getEmail();

    String getEstado();

    String getNomeRegiao();

    //setters
    void setNome(String nome);

    void setEmail(Email email);

    void setEstado(String estado);

    void setNomeRegiao(String nomeRegiao);

    //others
    boolean isValid();

}
