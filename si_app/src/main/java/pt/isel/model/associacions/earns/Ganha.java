package pt.isel.model.associacions.earns;

import jakarta.persistence.*;
import pt.isel.model.entities.game.badge.Cracha;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "ganha", schema = "public")
public class Ganha {
    @EmbeddedId
    private GanhaId id;

    @MapsId("idPlayer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador idPlayer;

    @MapsId("badgeName")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cracha badge;

    public GanhaId getId() {
        return id;
    }

    public void setId(GanhaId id) {
        this.id = id;
    }

    public Jogador getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Jogador idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Cracha getBadge() {
        return badge;
    }

    public void setBadge(Cracha badge) {
        this.badge = badge;
    }

}