package pt.isel.model.entities.player;

import jakarta.persistence.*;

@Entity
@NamedQuery(name = "JogadorEstatistica.findAll", query = "SELECT je FROM JogadorEstatistica je")
@NamedQuery(name = "JogadorEstatistica.findByKey", query = "SELECT je FROM JogadorEstatistica je where je.id = :id")
@Table(name = "jogador_estatistica", schema = "public")
public class JogadorEstatistica implements PlayerStats {
    @Id
    @Column(name = "id_jogador", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador player;
    @Column(name = "nr_partidas")
    private Integer nrOfMatches;

    @Column(name = "nr_jogos")
    private Integer nrOfGames;

    @Column(name = "total_pontos")
    private Integer totalOfPoints;

    @Override
    public Integer getId() {
        return this.id;
    }
    @Override
    public Player getPlayer() {
        return this.player;
    }
    @Override
    public Integer getNrOfMatches() {
        return this.nrOfMatches;
    }

    @Override
    public Integer getNrOfGames() {
        return this.nrOfGames;
    }

    @Override
    public Integer getTotalOfPoints() {
        return this.totalOfPoints;
    }

    @Override
    public void setId(Integer playerId) {
        this.id = playerId;
    }
    @Override
    public void setPlayer(Player player) {
        this.player = (Jogador) player;
    }

    @Override
    public void setNrOfMatches(Integer nrOfMatches) {
        this.nrOfMatches = nrOfMatches;
    }

    @Override
    public void setNrOfGames(Integer nrOfGames) {
        this.nrOfGames = nrOfGames;
    }

    @Override
    public void setTotalOfPoints(Integer totalOfPoints) {
        this.totalOfPoints = totalOfPoints;
    }
}