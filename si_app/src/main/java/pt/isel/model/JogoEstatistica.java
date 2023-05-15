package pt.isel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jogo_estatistica", schema = "public")
public class JogoEstatistica {
    @Id
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo jogo;

    @Column(name = "nr_partidas", nullable = false)
    private Integer nrPartidas;

    @Column(name = "nr_jogadores", nullable = false)
    private Integer nrJogadores;

    @Column(name = "total_pontos", nullable = false)
    private Integer totalPontos;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Integer getNrPartidas() {
        return nrPartidas;
    }

    public void setNrPartidas(Integer nrPartidas) {
        this.nrPartidas = nrPartidas;
    }

    public Integer getNrJogadores() {
        return nrJogadores;
    }

    public void setNrJogadores(Integer nrJogadores) {
        this.nrJogadores = nrJogadores;
    }

    public Integer getTotalPontos() {
        return totalPontos;
    }

    public void setTotalPontos(Integer totalPontos) {
        this.totalPontos = totalPontos;
    }

}