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

    @Column(name = "nr_partidas", nullable = false)
    private Integer nrOfMatches;

    @Column(name = "nr_jogadores", nullable = false)
    private Integer nrOfPlayers;

    @Column(name = "total_pontos", nullable = false)
    private Integer totalOfPoints;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo game;

    @Override
    public Alphanumeric getId() {
        return new Alphanumeric(this.id);
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    @Override
    public Integer getNrOfMatches() {
        return this.nrOfMatches;
    }

    @Override
    public Integer getNrOfPlayers() {
        return this.nrOfPlayers;
    }

    @Override
    public Integer getTotalOfPoints() {
        return this.totalOfPoints;
    }

    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    @Override
    public void setGame(Game game) {
        this.game = (Jogo) game;
    }

    @Override
    public void setNrOfMatches(Integer nrOfMatches) {
        this.nrOfMatches = nrOfMatches;
    }

    @Override
    public void setNrOfPlayers(Integer nrOfPlayers) {
        this.nrOfPlayers = nrOfPlayers;
    }

    @Override
    public void setTotalOfPoints(Integer totalOfPoints) {
        this.totalOfPoints = totalOfPoints;
    }

    public JogoEstatistica() {
        // for ORM only
    }

    public JogoEstatistica(Game game, Integer nrOfMatches, Integer nrOfPlayers, Integer totalOfPoints) {
        setGame(game);
        setId(game.getId());
        setNrOfMatches(nrOfMatches);
        setNrOfPlayers(nrOfPlayers);
        setTotalOfPoints(totalOfPoints);
    }
}