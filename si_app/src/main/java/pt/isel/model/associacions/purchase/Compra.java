package pt.isel.model.associacions.purchase;

import jakarta.persistence.*;
import pt.isel.model.entities.game.Jogo;
import pt.isel.model.entities.player.Jogador;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compra", schema = "public")
public class Compra {
    @EmbeddedId
    private CompraId id;

    //@MapsId("idPlayer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false, insertable = false, updatable = false)
    private Jogador idPlayer;

    //@MapsId("idGame")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false, insertable = false, updatable = false)
    private Jogo idGame;

    @Column(name = "data", nullable = false)
    private LocalDate date;

    @Column(name = "preco", nullable = false)
    private BigDecimal price;

    public CompraId getId() {
        return id;
    }

    public void setId(CompraId id) {
        this.id = id;
    }

    public Jogador getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Jogador idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Jogo getIdGame() {
        return idGame;
    }

    public void setIdGame(Jogo idGame) {
        this.idGame = idGame;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}