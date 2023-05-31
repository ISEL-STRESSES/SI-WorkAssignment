package model.entities.game;

import jakarta.persistence.*;
import model.associacions.purchase.Compra;
import model.entities.game.badge.Badge;
import model.entities.game.badge.Cracha;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.types.Alphanumeric;
import model.types.URL;

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

    @OneToMany(mappedBy = "idGame", orphanRemoval = true)
    private Set<Compra> purchases = new LinkedHashSet<>();

    /**
     * Default empty constructor
     */
    public Jogo() {
    }

    /**
     * Constructor
     *
     * @param id   the game id
     * @param name the game name
     * @param url  the game url
     */
    public Jogo(Alphanumeric id, String name, URL url) {
        setId(id);
        setName(name);
        setUrl(url);
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getId() {
        return new Alphanumeric(id);
    }

    /**
     * Setter function for the game id
     *
     * @param id the game id
     */
    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    /**
     * Getter function for the game name
     *
     * @return the game name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter function for the game name
     *
     * @param name the game name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter function for the game url
     *
     * @return the game url
     */
    @Override
    public URL getUrl() {
        return new URL(url);
    }

    /**
     * Setter function for the game url
     *
     * @param url the game url
     */
    @Override
    public void setUrl(URL url) {
        this.url = url.toString();
    }

    /**
     * Getter function for the game stats
     *
     * @return the game stats
     */
    @Override
    public GameStats getStats() {
        return this.stats;
    }

    /**
     * Setter function for the game stats
     *
     * @param stats the game stats
     */
    @Override
    public void setStats(GameStats stats) {
        this.stats = (JogoEstatistica) stats;
    }

    /**
     * Getter function for the game badges
     *
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
     * Setter function for the game badges
     *
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
     * Getter function for the game matches
     *
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
     * Setter function for the game matches
     *
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
     * Getter function for the game purchases
     *
     * @return the game purchases
     */
    @Override
    public Set<Compra> getPurchases() {
        return purchases;
    }

    /**
     * Setter function for the game purchases
     *
     * @param purchases the game purchases
     */
    @Override
    public void setPurchases(Set<Compra> purchases) {
        this.purchases = purchases;
    }
}