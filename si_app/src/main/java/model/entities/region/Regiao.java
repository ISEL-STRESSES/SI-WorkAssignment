package model.entities.region;

import jakarta.persistence.*;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.entities.player.Jogador;
import model.entities.player.Player;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class that represents the region.
 */
@Entity
@Table(name = "regiao", schema = "public")
@NamedQuery(name = "Regiao.findAll", query = "SELECT r FROM Regiao r")
@NamedQuery(name = "Regiao.findByKey", query = "SELECT r FROM Regiao r WHERE r.name = :key")
public class Regiao implements Region {
    @Id
    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "region")
    private Set<Jogador> players = new LinkedHashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Partida> matches = new LinkedHashSet<>();

    /**
     * Default empty constructor
     */
    public Regiao() {

    }

    /**
     * Constructor for the region entity
     *
     * @param region the region name
     */
    public Regiao(String region) {
        this.name = region;
    }

    /**
     * Getter function for the region name
     *
     * @return the region name
     */
    @Override
    public String getId() {
        return this.name;
    }

    /**
     * Setter function for the region name
     *
     * @param name the region name
     */
    @Override
    public void setId(String name) {
        this.name = name;
    }

    /**
     * Getter function for the region players
     *
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
     * Setter function for the region players
     *
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
     * Getter function for the region matches
     *
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
     * Setter function for the region matches
     *
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