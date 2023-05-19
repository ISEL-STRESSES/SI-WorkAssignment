package pt.isel.model.entities.region;

import jakarta.persistence.*;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.player.Jogador;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.game.matches.Partida;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class that represents the region.
 *
 * @property nome - name of the region.
 * @property jogadores - set of players that belong to the region.
 * @property partidas - set of matches that belong to the region.
 */
@Entity
@NamedQuery(name= "Regiao.findByKey", query = "SELECT r FROM Regiao r WHERE r.nome = :key")
@NamedQuery(name= "Regiao.findAll", query = "SELECT r FROM Regiao r")
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

    @Override
    public Set<Player> getJogadores() {
        return jogadores.stream().map(
                jogador -> (Player) jogador
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void addJogador(Player players) {
        this.jogadores.add((Jogador) players);
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

    @Override
    public Set<Match> getPartidas() {
        return partidas.stream().map(
                partida -> (Match) partida
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void setPartidas(Set<Match> Matches) {
        this.partidas = Matches.stream().map(
                match -> (Partida) match
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void addPartida(Match match) {
        this.partidas.add((Partida) match);
    }
}