package pt.isel.model.classImp;

import jakarta.persistence.*;
import pt.isel.model.Player;
import pt.isel.model.Region;
import pt.isel.model.classImp.Partida;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "regiao", schema = "public")
public class Regiao implements Region {
    @Id
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @OneToMany(mappedBy = "nomeRegiao")
    private Set<Jogador> jogadores = new LinkedHashSet<>();

    @OneToMany(mappedBy = "nomeRegiao")
    private Set<Partida> partidas = new LinkedHashSet<>();

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String name) {
        this.nome = name;
    }

    public Set<Player> getJogadores() {
        return jogadores.stream().map(
                jogador -> (Player) jogador
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    public void setJogadores(Set<Player> players) {
        this.jogadores = players.stream().map(
                player -> (Jogador) player
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }
}