package pt.isel.model.entities.game.badge;

import jakarta.persistence.*;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.Jogo;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

@Entity
@NamedQuery(name = "Cracha.findAll", query = "SELECT c FROM Cracha c")
@NamedQuery(name = "Cracha.findByKey", query = "SELECT c FROM Cracha c where c.id = :id")
@Table(name = "cracha", schema = "public")
public class Cracha implements Badge{
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


    /**
     * Getter function for the badge id
     *
     * @return the badge id
     */
    @Override
    public BadgeId getId() {
        return this.id;
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
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getGameId();
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
     * Getter function for the badge points
     *
     * @return the badge points
     */
    @Override
    public Integer getPoints() {
        return this.limitePontos;
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
     * Setter function for the badge id
     *
     * @param id the badge id
     */
    @Override
    public void setId(BadgeId id) {
        this.id = (CrachaId) id;
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
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        this.id.setGameId(gameId);
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
     * Setter function for the badge points
     *
     * @param points the badge points
     */
    @Override
    public void setPoints(Integer points) {
        this.limitePontos = points;
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