package model.entities.game.badge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Cracha} entity.
 */
@Embeddable
public class CrachaId implements Serializable {
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    /**
     * Empty constructor
     */
    public CrachaId() {
    }

    /**
     * Constructor for the {@link CrachaId} entity
     *
     * @param idJogo the game id
     * @param nome   the badge name
     */
    public CrachaId(Alphanumeric idJogo, String nome) {
        this.idJogo = idJogo.toString();
        this.nome = nome;
    }

    /**
     * Getter function for the badge id
     *
     * @return the badge id
     */
    public CrachaId getId() {
        return new CrachaId(new Alphanumeric(idJogo), nome);
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    public Alphanumeric getGameId() {
        return new Alphanumeric(idJogo);
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    public void setGameId(Alphanumeric gameId) {
        idJogo = gameId.toString();
    }

    /**
     * Getter function for the badge name
     *
     * @return the badge name
     */
    public String getBadgeName() {
        return nome;
    }

    /**
     * Setter function for the badge name
     *
     * @param badgeName the badge name
     */
    public void setBadgeName(String badgeName) {
        nome = badgeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrachaId entity = (CrachaId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.nome, entity.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, nome);
    }

}