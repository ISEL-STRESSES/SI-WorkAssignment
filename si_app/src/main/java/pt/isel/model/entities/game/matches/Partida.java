package pt.isel.model.entities.game.matches;

import jakarta.persistence.*;
import pt.isel.model.associacions.plays.Joga;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.Jogo;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.multiplayer.PartidaMultijogador;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.game.matches.normal.PartidaNormal;
import pt.isel.model.entities.region.Regiao;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Partida.findByKey", query = "SELECT p FROM Partida p WHERE p.id = :key")
@NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p")
@Table(name = "partida", schema = "public")
public class Partida implements Match {
    @EmbeddedId
    private PartidaId id;
    @Column(name = "data_inicio", nullable = false)
    private LocalDate startDate;

    @Column(name = "data_fim")
    private LocalDate endDate;

    @MapsId("idJogo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false, insertable = false, updatable = false)
    private Jogo game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nome_regiao", nullable = false)
    private Regiao region;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Joga> plays = new LinkedHashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo"),
            @JoinColumn(name = "nr", referencedColumnName = "nr_partida")
    })
    private PartidaNormal normalMatch;

    @OneToOne(orphanRemoval = true)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo"),
            @JoinColumn(name = "nr", referencedColumnName = "nr_partida")
    })
    private PartidaMultijogador multiplayerMatch;

    @Override
    public PartidaId getId() {
        return id;
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    @Override
    public void setGame(Game game) {
        this.game = (Jogo) game;
    }

    @Override
    public Alphanumeric getGameId() {
        return this.id.getGameId();
    }

    @Override
    public void setGameId(Alphanumeric gameId) {
        this.id.setGameId(gameId);
    }

    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchNumber();
    }

    @Override
    public void setMatchNumber(Integer matchNumber) {
        this.id.setMatchNumber(matchNumber);
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return this.endDate;
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public Region getRegion() {
        return this.region;
    }

    @Override
    public void setRegion(Region region) {
        this.region = (Regiao) region;
    }

    @Override
    public NormalMatch getNormalMatch() {
        return this.normalMatch;
    }

    @Override
    public void setNormalMatch(NormalMatch normalMatch) {
        this.normalMatch = (PartidaNormal) normalMatch;
    }

    @Override
    public MultiPlayerMatch getMultiPlayerMatch() {
        return this.multiplayerMatch;
    }

    @Override
    public void setMultiPlayerMatch(MultiPlayerMatch multiPlayerMatch) {
        this.multiplayerMatch = (PartidaMultijogador) multiPlayerMatch;
    }

    @Override
    public Set<Joga> getPlays() {
        return plays;
    }

    @Override
    public void setPlays(Set<Joga> plays) {
        this.plays = plays;
    }

    @Override
    public void setMatchId(Integer matchId) {
        this.id.setMatchNumber(matchId);
    }
}