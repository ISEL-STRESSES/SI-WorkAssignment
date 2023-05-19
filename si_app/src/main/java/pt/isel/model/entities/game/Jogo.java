package pt.isel.model.entities.game;

import jakarta.persistence.*;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

@Entity
@NamedQuery(name = "Jogo.findAll", query = "SELECT j FROM Jogo j")
@NamedQuery(name = "Jogo.findByKey", query = "SELECT j FROM Jogo j where j.id = :id")
@Table(name = "jogo", schema = "public", indexes = {
        @Index(name = "jogo_nome_key", columnList = "nome", unique = true)
})
public class Jogo implements Game {
    //properties
    @Id
    @Column(name = "id", columnDefinition = "alphanumeric(0, 0) not null")
    private String id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "url", columnDefinition = "url(0, 0) not null")
    private String url;

    //Associations
    @OneToOne(mappedBy = "jogo")
    private JogoEstatistica jogoEstatistica;

    //Getters
    @Override
    public Alphanumeric getId() {
        return new Alphanumeric(id);
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public URL getUrl() {
        return new URL(url);
    }

    //Setters
    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUrl(URL url) {
        this.url = url.toString();
    }

    //Constructors
    public Jogo() {
    }

    public Jogo(Alphanumeric id, String nome, URL url) {
        setId(id);
        setNome(nome);
        setUrl(url);
    }

}