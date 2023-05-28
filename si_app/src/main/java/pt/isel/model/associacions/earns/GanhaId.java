package pt.isel.model.associacions.earns;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GanhaId implements Serializable {
    @Column(name = "id_jogador", nullable = false, insertable = false, updatable = false)
    private Integer idPlayer;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null", insertable = false, updatable = false)
    private String idGame;

    @Column(name = "nome_cracha", nullable = false, length = 50, insertable = false, updatable = false)
    private String badgeName;

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Alphanumeric getIdGame() {
        return new Alphanumeric(idGame);
    }

    public void setIdGame(Alphanumeric idGame) {
        this.idGame = idGame.toString();
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GanhaId entity = (GanhaId) o;
        return Objects.equals(this.idGame, entity.idGame) &&
                Objects.equals(this.idPlayer, entity.idPlayer) &&
                Objects.equals(this.badgeName, entity.badgeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGame, idPlayer, badgeName);
    }

}