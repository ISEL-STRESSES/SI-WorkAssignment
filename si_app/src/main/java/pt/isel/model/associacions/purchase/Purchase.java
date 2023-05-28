package pt.isel.model.associacions.purchase;

import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.player.Player;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a player that has purchased a game.
 */
public interface Purchase {

    /**
     * Getter function for the purchase id
     *
     * @return the purchase id
     */
    CompraId getId();

    /**
     * Setter function for the purchase id
     *
     * @param id the purchase id
     */
    void setId(CompraId id);

    /**
     * Getter function for the player that made the purchase
     *
     * @return the player that made the purchase
     */
    Player getIdPlayer();

    /**
     * Setter function for the player that made the purchase
     *
     * @param idPlayer the player that made the purchase
     */
    void setIdPlayer(Player idPlayer);

    /**
     * Getter function for the game that was purchased
     *
     * @return the game that was purchased
     */
    Game getIdGame();

    /**
     * Setter function for the game that was purchased
     *
     * @param idGame the game that was purchased
     */
    void setIdGame(Game idGame);

    /**
     * Getter function for the purchase date
     *
     * @return the purchase date
     */
    LocalDate getDate();

    /**
     * Setter function for the purchase date
     *
     * @param date the purchase date
     */
    void setDate(LocalDate date);

    /**
     * Getter function for the purchase price
     *
     * @return the purchase price
     */
    BigDecimal getPrice();

    /**
     * Setter function for the purchase price
     *
     * @param price the purchase price
     */
    void setPrice(BigDecimal price);
}
