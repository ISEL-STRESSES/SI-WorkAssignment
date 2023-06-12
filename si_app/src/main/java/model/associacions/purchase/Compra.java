package model.associacions.purchase;

import jakarta.persistence.*;
import model.entities.game.Game;
import model.entities.game.Jogo;
import model.entities.player.Jogador;
import model.entities.player.Player;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * This class represents a purchase of a game by a player.
 */
@Entity
@NamedQuery(name = "Compra.findByKey", query = "SELECT c FROM Compra c WHERE c.id = :key")
@NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
@Table(name = "compra", schema = "public")
public class Compra implements Purchase {
    @EmbeddedId
    private CompraId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false, insertable = false, updatable = false)
    private Jogador idPlayer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false, insertable = false, updatable = false)
    private Jogo idGame;

    @Column(name = "data", nullable = false)
    private LocalTime time;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    /**
     * Getter function for the purchase id
     *
     * @return the purchase id
     */
    @Override
    public CompraId getId() {
        return id;
    }

    /**
     * Setter function for the purchase id
     *
     * @param id the purchase id
     */
    @Override
    public void setId(CompraId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that made the purchase
     *
     * @return the player that made the purchase
     */
    @Override
    public Player getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setter function for the player that made the purchase
     *
     * @param idPlayer the player that made the purchase
     */
    @Override
    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = (Jogador) idPlayer;
    }

    /**
     * Getter function for the game that was purchased
     *
     * @return the game that was purchased
     */
    @Override
    public Game getIdGame() {
        return idGame;
    }

    /**
     * Setter function for the game that was purchased
     *
     * @param idGame the game that was purchased
     */
    @Override
    public void setIdGame(Game idGame) {
        this.idGame = (Jogo) idGame;
    }

    /**
     * Getter function for the purchase date
     *
     * @return the purchase date
     */
    @Override
    public LocalTime getDate() {
        return time;
    }

    /**
     * Setter function for the purchase date
     *
     * @param localTime the purchase date
     */
    @Override
    public void setDate(LocalTime localTime) {
        this.time = localTime;
    }

    /**
     * Getter function for the purchase price
     *
     * @return the purchase price
     */
    @Override
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Setter function for the purchase price
     *
     * @param price the purchase price
     */
    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}