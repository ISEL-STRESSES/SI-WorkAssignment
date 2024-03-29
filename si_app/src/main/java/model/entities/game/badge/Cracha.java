package model.entities.game.badge;

import jakarta.persistence.*;
import model.associacions.earns.Ganha;
import model.entities.game.Game;
import model.entities.game.Jogo;
import model.types.Alphanumeric;
import model.types.URL;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * class that represents a badge
 */
@Entity
@NamedQuery(name = "Cracha.findAll", query = "SELECT c FROM Cracha c")
@NamedQuery(name = "Cracha.findByKey", query = "SELECT c FROM Cracha c where c.id = :key")
@Table(name = "cracha", schema = "public")
public class Cracha implements Badge {
    @EmbeddedId
    private CrachaId id;

    @MapsId("idJogo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo idJogo;

    @Column(name = "limite_pontos", nullable = false)
    private Integer limitePontos;

    @Column(name = "imagem", columnDefinition = "url(0, 0) not null")
    private String imagem;

    @OneToMany(mappedBy = "badge", orphanRemoval = true)
    private Set<Ganha> player = new LinkedHashSet<>();

    public Cracha(Alphanumeric gameId, String badgeName, int pointLimit, URL image) {
        this.id = new CrachaId(gameId, badgeName);
        this.limitePontos = pointLimit;
        this.imagem = image.toString();
    }

    public Cracha() {
        // empty constructor
    }

    public Set<Ganha> getPlayer() {
        return player;
    }

    public void setPlayer(Set<Ganha> player) {
        this.player = player;
    }

    /**
     * Getter function for the badge id
     *
     * @return the badge id
     */
    @Override
    public CrachaId getId() {
        return this.id;
    }

    /**
     * Setter function for the badge id
     *
     * @param id the badge id
     */
    @Override
    public void setId(CrachaId id) {
        this.id = id;
    }

    /**
     * Getter function for the badge name
     *
     * @return the badge name
     */
    @Override
    public String getName() {
        return this.id.getBadgeName();
    }

    /**
     * Setter function for the badge name
     *
     * @param name the badge name
     */
    @Override
    public void setName(String name) {
        this.id.setBadgeName(name);
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getGameId();
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        this.id.setGameId(gameId);
    }

    /**
     * Getter function for the badge image
     *
     * @return the badge url image
     */
    @Override
    public URL getImage() {
        return new URL(this.imagem);
    }

    /**
     * Setter function for the badge image
     *
     * @param image the badge url image
     */
    @Override
    public void setImage(URL image) {
        this.imagem = image.toString();
    }

    /**
     * Getter function for the badge points
     *
     * @return the badge points
     */
    @Override
    public Integer getPoints() {
        return this.limitePontos;
    }

    /**
     * Setter function for the badge points
     *
     * @param points the badge points
     */
    @Override
    public void setPoints(Integer points) {
        this.limitePontos = points;
    }

    /**
     * Getter function for the game
     *
     * @return the game
     */
    @Override
    public Game getGame() {
        return this.idJogo;
    }

    /**
     * Setter function for the game
     *
     * @param game the game
     */
    @Override
    public void setGame(Game game) {
        this.idJogo = (Jogo) game;
    }
}