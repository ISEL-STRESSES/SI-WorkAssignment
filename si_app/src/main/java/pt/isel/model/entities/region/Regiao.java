package pt.isel.model.entities.region;

import jakarta.persistence.*;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.entities.player.Jogador;
import pt.isel.model.entities.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class that represents the region.
 *
 * @property nome - name of the region.
 * @property players - set of players that belong to the region.
 * @property matches - set of matches that belong to the region.
 */
@Entity
@NamedQuery(name = "Regiao.findByKey", query = "SELECT r FROM Regiao r WHERE r.name = :key")
@NamedQuery(name = "Regiao.findAll", query = "SELECT r FROM Regiao r")
@Table(name = "regiao", schema = "public")
public class Regiao implements Region {
    @Id
    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "region")
    private Set<Jogador> players = new LinkedHashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Partida> matches = new LinkedHashSet<>();

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public void setId(String name) {
        this.name = name;
    }

    @Override
    public Set<Player> getPlayers() {
        return players.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void setPlayers(Set<Player> players) {
        this.players = players.stream().map(
                player -> (Jogador) player
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public Set<Match> getMatches() {
        return matches.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void setMatches(Set<Match> Matches) {
        this.matches = Matches.stream().map(
                match -> (Partida) match
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }
}