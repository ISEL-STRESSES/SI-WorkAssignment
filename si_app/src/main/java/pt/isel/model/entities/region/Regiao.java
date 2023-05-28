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
 */
@Entity
@NamedQuery(name= "Regiao.findByKey", query = "SELECT r FROM Regiao r WHERE r.name = :key")
@NamedQuery(name= "Regiao.findAll", query = "SELECT r FROM Regiao r")
@Table(name = "regiao", schema = "public")
public class Regiao implements Region {
    @Id
    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "regionName")
    private Set<Jogador> players = new LinkedHashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Partida> matches = new LinkedHashSet<>();

    /**
     * Getter function for the region name
     * @return the region name
     */
    @Override
    public String getId() {
        return this.name;
    }

    /**
     * Getter function for the region players
     * @return the region players
     */
    @Override
    public Set<Player> getPlayers() {
        return players.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Getter function for the region matches
     * @return the region matches
     */
    @Override
    public Set<Match> getMatches() {
        return matches.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Setter function for the region name
     * @param name the region name
     */
    @Override
    public void setId(String name) {
        this.name = name;
    }

    /**
     * Setter function for the region players
     * @param players the region players
     */
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

    /**
     * Setter function for the region matches
     * @param Matches the region matches
     */
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