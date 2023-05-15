package pt.isel.model.classImp;

import jakarta.persistence.*;
import pt.isel.model.Player;
import pt.isel.model.PlayerStats;

@Entity
@Table(name = "jogador_estatistica", schema = "public")
public class JogadorEstatistica implements PlayerStats {
    //Fields
    @Id
    @Column(name = "id_jogador", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador jogador;

    @Column(name = "nr_partidas")
    private Integer nrPartidas;

    @Column(name = "nr_jogos")
    private Integer nrJogos;

    @Column(name = "total_pontos")
    private Integer totalPontos;

    @Override
    public Player getPlayer() {
        return this.jogador;
    }

    @Override
    public Integer getPlayerId() {
        return this.id;
    }

    @Override
    public Integer getNrPartidas() {
        return this.nrPartidas;
    }

    @Override
    public Integer getNrJogos() {
        return this.nrJogos;
    }

    @Override
    public Integer getTotalPontos() {
        return this.totalPontos;
    }

    @Override
    public void setJogador(Player player) {
        this.jogador = (Jogador) player;
    }

    @Override
    public void setPlayerId(Integer playerId) {
        this.id = playerId;
    }

    @Override
    public void setNrPartidas(Integer nrPartidas) {
        this.nrPartidas = nrPartidas;
    }

    @Override
    public void setNrJogos(Integer nrJogos) {
        this.nrJogos = nrJogos;
    }

    @Override
    public void setTotalPontos(Integer totalPontos) {
        this.totalPontos = totalPontos;
    }
}