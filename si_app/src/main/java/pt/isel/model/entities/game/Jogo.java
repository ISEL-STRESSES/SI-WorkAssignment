package pt.isel.model.entities.game;

import jakarta.persistence.*;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.badge.Cracha;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a game
 */
@Entity
@NamedQuery(name = "Jogo.findAll", query = "SELECT j FROM Jogo j")
@NamedQuery(name = "Jogo.findByKey", query = "SELECT j FROM Jogo j where j.id = :id")
@Table(name = "jogo", schema = "public", indexes = {
        @Index(name = "jogo_nome_key", columnList = "nome", unique = true)
})
public class Jogo implements Game {
    @Id
    @Column(name = "id", columnDefinition = "alphanumeric(0, 0) not null")
    private String id;

    @Column(name = "nome", nullable = false, length = 50)
    private String name;

    @Column(name = "url", columnDefinition = "url(0, 0) not null")
    private String url;

    @OneToOne(mappedBy = "game")
    private JogoEstatistica stats;

    @OneToMany(mappedBy = "idJogo", orphanRemoval = true)
    private Set<Cracha> badges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private Set<Partida> matches = new LinkedHashSet<>();

    /**
     * Getter function for the game id
     * @return the game id
     */
    @Override
    public Alphanumeric getId() {
        return new Alphanumeric(id);
    }

    /**
     * Getter function for the game name
     * @return the game name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Getter function for the game url
     * @return the game url
     */
    @Override
    public URL getUrl() {
        return new URL(url);
    }

    /**
     * Getter function for the game stats
     * @return the game stats
     */
    @Override
    public GameStats getStats() {
        return this.stats;
    }

    /**
     * Getter function for the game badges
     * @return the game badges
     */
    @Override
    public Set<Badge> getBadges() {
        return this.badges.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Getter function for the game matches
     * @return the game matches
     */
    @Override
    public Set<Match> getMatches() {
        return this.matches.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Setter function for the game id
     * @param id the game id
     */
    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    /**
     * Setter function for the game name
     * @param nome the game name
     */
    public void setName(String nome) {
        this.name = nome;
    }

    /**
     * Setter function for the game url
     * @param url the game url
     */
    public void setUrl(URL url) {
        this.url = url.toString();
    }

    /**
     * Setter function for the game stats
     * @param stats the game stats
     */
    @Override
    public void setStats(GameStats stats) {
        this.stats = (JogoEstatistica) stats;
    }

    /**
     * Setter function for the game badges
     * @param badges the game badges
     */
    @Override
    public void setBadges(Set<Badge> badges) {
        this.badges = badges.stream().map(b -> (Cracha) b).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Setter function for the game matches
     * @param matches the game matches
     */
    @Override
    public void setMatches(Set<Match> matches) {
        this.matches = matches.stream().map(m -> (Partida) m).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Default empty constructor
     */
    public Jogo() {
    }

    /**
     * Constructor for the game
     * @param id the game id
     * @param name the game name
     * @param url the game url
     */
    public Jogo(Alphanumeric id, String name, URL url) {
        setId(id);
        setName(name);
        setUrl(url);
    }
}