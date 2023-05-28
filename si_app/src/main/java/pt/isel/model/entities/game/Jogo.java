package pt.isel.model.entities.game;

import jakarta.persistence.*;
import pt.isel.model.associacions.purchase.Compra;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.badge.Cracha;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

import java.util.LinkedHashSet;
import java.util.Set;

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

    public Jogo() {
    }

    public Jogo(Alphanumeric id, String name, URL url) {
        setId(id);
        setName(name);
        setUrl(url);
    }

    @Override
    public Alphanumeric getId() {
        return new Alphanumeric(id);
    }

    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String nome) {
        this.name = nome;
    }

    @Override
    public URL getUrl() {
        return new URL(url);
    }

    @Override
    public void setUrl(URL url) {
        this.url = url.toString();
    }

    @Override
    public GameStats getStats() {
        return this.stats;
    }

    @Override
    public void setStats(GameStats stats) {
        this.stats = (JogoEstatistica) stats;
    }

    @Override
    public Set<Badge> getBadges() {
        return this.badges.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void setBadges(Set<Badge> badges) {
        this.badges = badges.stream().map(b -> (Cracha) b).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public Set<Match> getMatches() {
        return this.matches.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void setMatches(Set<Match> matches) {
        this.matches = matches.stream().map(m -> (Partida) m).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public Set<Compra> getPurchases() {
        return purchases;
    }

    @Override
    public void setPurchases(Set<Compra> purchases) {
        this.purchases = purchases;
    }
}