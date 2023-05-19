package pt.isel.model.entities.game;

import jakarta.persistence.*;
import pt.isel.model.types.Alphanumeric;

@Entity
@NamedQuery(name = "JogoEstatistica.findAll", query = "SELECT j FROM JogoEstatistica j")
@NamedQuery(name = "JogoEstatistica.findByKey", query = "SELECT j FROM JogoEstatistica j where j.id = :id")
@Table(name = "jogo_estatistica", schema = "public")
public class JogoEstatistica implements GameStats {

    @Id
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String id;

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


    @Override
    public Game getGame() {
        return this.jogo;
    }

    @Override
    public Alphanumeric getGameId() {
        return new Alphanumeric(this.id);
    }

    @Override
    public Integer getNrPartidas() {
        return this.nrPartidas;
    }

    @Override
    public Integer getNrJogadores() {
        return this.nrJogadores;
    }

    @Override
    public Integer getTotalPontos() {
        return this.totalPontos;
    }

    @Override
    public void setGame(Game game) {
        this.jogo = (Jogo) game;
    }

    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    @Override
    public void setNrPartidas(Integer nrPartidas) {
        this.nrPartidas = nrPartidas;
    }

    @Override
    public void setNrJogadores(Integer nrJogadores) {
        this.nrJogadores = nrJogadores;
    }

    @Override
    public void setTotalPontos(Integer totalPontos) {
        this.totalPontos = totalPontos;
    }

    public JogoEstatistica() {
        // for ORM only
    }

    public JogoEstatistica(Game game, Integer nrPartidas, Integer nrJogadores, Integer totalPontos) {
        setGame(game);
        setId(game.getId());
        setNrPartidas(nrPartidas);
        setNrJogadores(nrJogadores);
        setTotalPontos(totalPontos);
    }
}