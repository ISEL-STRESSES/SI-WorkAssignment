package model.entities.game.matches;

import jakarta.persistence.*;
import model.associacions.plays.Joga;
import model.entities.game.Game;
import model.entities.game.Jogo;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.multiplayer.PartidaMultijogador;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormal;
import model.entities.region.Regiao;
import model.entities.region.Region;
import model.types.Alphanumeric;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class that represents a match
 */
@Entity
@NamedQuery(name = "Partida.findByKey", query = "SELECT p FROM Partida p WHERE p.id = :key")
@NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p")
@Table(name = "partida", schema = "public")
public class Partida implements Match {
    @EmbeddedId
    private PartidaId id;

    @Column(name = "data_inicio", nullable = false)
    private LocalTime startDate;

    @Column(name = "data_fim")
    private LocalTime endDate;

    @MapsId("gameId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nome_regiao", nullable = false)
    private Regiao region;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Joga> plays = new LinkedHashSet<>();

    @OneToOne(mappedBy = "match", orphanRemoval = true)
    private PartidaNormal normalMatch;

    @OneToOne(mappedBy = "match", orphanRemoval = true)
    private PartidaMultijogador multiplayerMatch;

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    @Override
    public PartidaId getId() {
        return id;
    }

    /**
     * Getter function for the game
     *
     * @return the game
     */
    @Override
    public Game getGame() {
        return this.game;
    }

    /**
     * Setter function for the game
     *
     * @param game the game
     */
    @Override
    public void setGame(Game game) {
        this.game = (Jogo) game;
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getGameId();
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        this.id.setGameId(gameId);
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchNumber();
    }

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    @Override
    public void setMatchNumber(Integer matchNumber) {
        this.id.setMatchNumber(matchNumber);
    }

    /**
     * Getter function for the match start date
     *
     * @return the match start date
     */
    @Override
    public LocalTime getStartDate() {
        return this.startDate;
    }

    /**
     * Setter function for the match start date
     *
     * @param startDate the match start date
     */
    @Override
    public void setStartDate(LocalTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter function for the match end date
     *
     * @return the match end date
     */
    @Override
    public LocalTime getEndDate() {
        return this.endDate;
    }

    /**
     * Setter function for the match end date
     *
     * @param endDate the match end date
     */
    @Override
    public void setEndDate(LocalTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter function for the match region
     *
     * @return the match region
     */
    @Override
    public Region getRegion() {
        return this.region;
    }

    /**
     * Setter function for the match region
     *
     * @param region the match region
     */
    @Override
    public void setRegion(Region region) {
        this.region = (Regiao) region;
    }

    /**
     * Getter function for the normal match
     *
     * @return the match
     */
    @Override
    public NormalMatch getNormalMatch() {
        return this.normalMatch;
    }

    /**
     * Setter function for the normal match
     *
     * @param normalMatch the match
     */
    @Override
    public void setNormalMatch(NormalMatch normalMatch) {
        this.normalMatch = (PartidaNormal) normalMatch;
    }

    /**
     * Getter function for the multiplayer match
     *
     * @return the match
     */
    @Override
    public MultiPlayerMatch getMultiPlayerMatch() {
        return this.multiplayerMatch;
    }

    /**
     * Setter function for the multiplayer match
     *
     * @param multiPlayerMatch the match
     */
    @Override
    public void setMultiPlayerMatch(MultiPlayerMatch multiPlayerMatch) {
        this.multiplayerMatch = (PartidaMultijogador) multiPlayerMatch;
    }

    /**
     * Getter function for the plays
     *
     * @return the plays
     */
    @Override
    public Set<Joga> getPlays() {
        return plays;
    }

    /**
     * Setter function for the plays
     *
     * @param plays the plays
     */
    @Override
    public void setPlays(Set<Joga> plays) {
        this.plays = plays;
    }

    /**
     * Setter function for the match id
     *
     * @param matchId the match id
     */
    @Override
    public void setMatchId(Integer matchId) {
        this.id.setMatchNumber(matchId);
    }
}