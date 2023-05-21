package pt.isel.model.entities.game.matches;

import jakarta.persistence.*;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.region.Regiao;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;

import java.time.LocalDate;

@Entity
@NamedQuery(name= "Partida.findByKey", query = "SELECT p FROM Partida p WHERE p.id = :key")
@NamedQuery(name= "Partida.findAll", query = "SELECT p FROM Partida p")
@Table(name = "partida", schema = "public")
public class Partida implements Match {
    @EmbeddedId
    private PartidaId id;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nome_regiao", nullable = false)
    private Regiao nomeRegiao;

    @Override
    public MatchId getId() {
        return id;
    }

    /**
     * Getter function for the game
     *
     * @return the game
     */
    @Override
    public Game getGame() {
        throw new UnsupportedOperationException("Not implemented yet.");
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
     * Getter function for the match number
     *
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchNumber();
    }

    /**
     * Getter function for the match start date
     *
     * @return the match start date
     */
    @Override
    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    /**
     * Getter function for the match end date
     *
     * @return the match end date
     */
    @Override
    public LocalDate getDataFim() {
        return this.dataFim;
    }

    /**
     * Getter function for the match region
     *
     * @return the match region
     */
    @Override
    public Region getRegiao() {
        return this.nomeRegiao;
    }

    /**
     * Getter function for the normal match
     *
     * @return the match
     */
    @Override
    public NormalMatch getNormalMatch() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Getter function for the multiplayer match
     *
     * @return the match
     */
    @Override
    public MultiPlayerMatch getMultiPlayerMatch() {
        throw new UnsupportedOperationException("Not implemented yet.");
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

    /**
     * Setter function for the game
     *
     * @param game the game
     */
    @Override
    public void setGame(Game game) {
        throw new UnsupportedOperationException("Not implemented yet.");
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
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    @Override
    public void setMatchNumber(Integer matchNumber) {
        this.id.setMatchNumber(matchNumber);
    }

    /**
     * Setter function for the match start date
     *
     * @param dataInicio the match start date
     */
    @Override
    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Setter function for the match end date
     *
     * @param dataFim the match end date
     */
    @Override
    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    /**
     * Setter function for the match region
     *
     * @param regiao the match region
     */
    @Override
    public void setNomeRegiao(Region regiao) {
        this.nomeRegiao = (Regiao) regiao;
    }

    /**
     * Setter function for the normal match
     *
     * @param normalMatch the match
     */
    @Override
    public void setNormalMatch(NormalMatch normalMatch) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Setter function for the multiplayer match
     *
     * @param multiPlayerMatch the match
     */
    @Override
    public void setMultiPlayerMatch(MultiPlayerMatch multiPlayerMatch) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}