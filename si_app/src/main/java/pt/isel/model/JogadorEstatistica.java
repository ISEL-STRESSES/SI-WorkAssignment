package pt.isel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jogador_estatistica", schema = "public")
public class JogadorEstatistica {
    @Id
    @Column(name = "id_jogador", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Player player;

    @Column(name = "nr_partidas")
    private Integer nrPartidas;

    @Column(name = "nr_jogos")
    private Integer nrJogos;

    @Column(name = "total_pontos")
    private Integer totalPontos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getJogador() {
        return player;
    }

    public void setJogador(Player player) {
        this.player = player;
    }

    public Integer getNrPartidas() {
        return nrPartidas;
    }

    public void setNrPartidas(Integer nrPartidas) {
        this.nrPartidas = nrPartidas;
    }

    public Integer getNrJogos() {
        return nrJogos;
    }

    public void setNrJogos(Integer nrJogos) {
        this.nrJogos = nrJogos;
    }

    public Integer getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(Integer totalPontos) {
        this.totalPontos = totalPontos;
    }

}