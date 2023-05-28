package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.game.Jogo;
import pt.isel.model.entities.player.Jogador;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This class represents a purchase of a game by a player.
 */
@Entity
@Table(name = "compra", schema = "public")
public class Compra {
    @EmbeddedId
    private CompraId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId("idJogo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo idJogo;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "preco", nullable = false)
    private BigDecimal preco;

    /**
     * Getter function for the purchase id
     * @return the purchase id
     */
    public CompraId getId() {
        return id;
    }

    /**
     * Setter function for the purchase id
     * @param id the purchase id
     */
    public void setId(CompraId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that made the purchase
     * @return the player that made the purchase
     */
    public Jogador getIdJogador() {
        return idJogador;
    }

    /**
     * Setter function for the player that made the purchase
     * @param idJogador the player that made the purchase
     */
    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    /**
     * Getter function for the game that was purchased
     * @return the game that was purchased
     */
    public Jogo getIdJogo() {
        return idJogo;
    }

    /**
     * Setter function for the game that was purchased
     * @param idJogo the game that was purchased
     */
    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }

    /**
     * Getter function for the purchase date
     * @return the purchase date
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Setter function for the purchase date
     * @param data the purchase date
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Getter function for the purchase price
     * @return the purchase price
     */
    public BigDecimal getPreco() {
        return preco;
    }

    /**
     * Setter function for the purchase price
     * @param preco the purchase price
     */
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

}