package model.associacions.earns;

import jakarta.persistence.*;
import model.entities.game.badge.Badge;
import model.entities.game.badge.Cracha;
import model.entities.player.Jogador;
import model.entities.player.Player;
import org.eclipse.persistence.annotations.OptimisticLocking;
import org.eclipse.persistence.annotations.OptimisticLockingType;

/**
 * This class represents a player that has won a badge.
 */
@Entity
@OptimisticLocking(type = OptimisticLockingType.CHANGED_COLUMNS)
@Table(name = "ganha", schema = "public")
public class Ganha implements Earns {
    @EmbeddedId
    private GanhaId id;

    @MapsId("idPlayer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador idPlayer;

    @MapsId("badgeName")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cracha badge;

    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    @Override
    public GanhaId getId() {
        return id;
    }

    /**
     * Setter function for the player that won the badge
     *
     * @param id the player that won the badge
     */
    @Override
    public void setId(GanhaId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    @Override
    public Player getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setter function for the player that won the badge
     *
     * @param idPlayer the player that won the badge
     */
    @Override
    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = (Jogador) idPlayer;
    }

    /**
     * Getter function for the badge that was won
     *
     * @return the badge that was won
     */
    @Override
    public Badge getBadge() {
        return badge;
    }

    /**
     * Setter function for the badge that was won
     *
     * @param badge the badge that was won
     */
    @Override
    public void setBadge(Badge badge) {
        this.badge = (Cracha) badge;
    }
}