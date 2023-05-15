package pt.isel.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "regiao", schema = "public")
public class Regiao {
    @Id
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @OneToMany(mappedBy = "nomeRegiao")
    private Set<Player> jogadors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "nomeRegiao")
    private Set<Partida> partidas = new LinkedHashSet<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Player> getJogadors() {
        return jogadors;
    }

    public void setJogadors(Set<Player> players) {
        this.jogadors = players;
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }

}