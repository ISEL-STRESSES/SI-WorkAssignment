package pt.isel.model.associacions;

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

    public CompraId getId() {
        return id;
    }

    public void setId(CompraId id) {
        this.id = id;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

}