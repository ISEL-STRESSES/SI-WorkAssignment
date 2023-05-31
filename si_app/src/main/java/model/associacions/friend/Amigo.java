package model.associacions.friend;

import jakarta.persistence.*;
import model.entities.player.Jogador;
import model.entities.player.Player;

/**
 * This class represents a friendship between two players.
 */
@Entity
@Table(name = "amigo", schema = "public")
public class Amigo implements Friend {
    @EmbeddedId
    private AmigoId id;

    @MapsId("player1Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador idPlayer1;

    @MapsId("player2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador idPlayer2;

    /**
     * Getter function for the friendship id
     *
     * @return the friendship id
     */
    @Override
    public AmigoId getId() {
        return id;
    }

    /**
     * Setter function for the friendship id
     *
     * @param id the friendship id
     */
    @Override
    public void setId(AmigoId id) {
        this.id = id;
    }

    /**
     * Getter function for the first player in the friendship
     *
     * @return the first player
     */
    @Override
    public Player getIdPlayer1() {
        return idPlayer1;
    }

    /**
     * Setter function for the first player in the friendship
     *
     * @param idPlayer1 the first player
     */
    @Override
    public void setIdPlayer1(Player idPlayer1) {
        this.idPlayer1 = (Jogador) idPlayer1;
    }

    /**
     * Getter function for the second player in the friendship
     *
     * @return the second player
     */
    @Override
    public Player getIdPlayer2() {
        return idPlayer2;
    }

    /**
     * Setter function for the second player in the friendship
     *
     * @param idPlayer2 the second player
     */
    @Override
    public void setIdPlayer2(Player idPlayer2) {
        this.idPlayer2 = (Jogador) idPlayer2;
    }
}