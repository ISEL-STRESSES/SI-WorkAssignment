package model.associacions.friend;

import model.entities.player.Player;

/**
 * This class represents a friendship between two players.
 */
public interface Friend {
    /**
     * Getter function for the friendship id
     *
     * @return the friendship id
     */
    AmigoId getId();

    /**
     * Setter function for the friendship id
     *
     * @param id the friendship id
     */
    void setId(AmigoId id);

    /**
     * Getter function for the first player in the friendship
     *
     * @return the first player
     */
    Player getIdPlayer1();

    /**
     * Setter function for the first player in the friendship
     *
     * @param idPlayer1 the first player
     */
    void setIdPlayer1(Player idPlayer1);

    /**
     * Getter function for the second player in the friendship
     *
     * @return the second player
     */
    Player getIdPlayer2();

    /**
     * Setter function for the second player in the friendship
     *
     * @param idPlayer2 the second player
     */
    void setIdPlayer2(Player idPlayer2);
}
