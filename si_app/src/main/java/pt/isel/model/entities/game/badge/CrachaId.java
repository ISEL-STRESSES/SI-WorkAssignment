package pt.isel.model.entities.game.badge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;
import pt.isel.utils.Pair;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CrachaId implements Serializable, BadgeId {
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    /**
     * Getter function for the badge id
     *
     * @return the badge id
     */
    @Override
    public Pair<Alphanumeric, String> getBadgeId() {
        return new Pair<>(new Alphanumeric(idJogo), nome);
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return new Alphanumeric(idJogo);
    }

    /**
     * Getter function for the badge name
     *
     * @return the badge name
     */
    @Override
    public String getBadgeName() {
        return nome;
    }

    /**
     * Setter function for the badge id
     *
     * @param badgeId the badge id
     */
    @Override
    public void setBadgeId(Pair<Alphanumeric, String> badgeId) {
        idJogo = badgeId.first().toString();
        nome = badgeId.second();
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        idJogo = gameId.toString();
    }

    /**
     * Setter function for the badge name
     *
     * @param badgeName the badge name
     */
    @Override
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