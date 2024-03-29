package model.entities.game.matches.multiplayer;

import jakarta.persistence.*;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.types.Alphanumeric;
import model.types.MultiPlayerMatchState;

/**
 * Class that represents a multiplayer match
 */
@Entity
@NamedQuery(name = "Partida_multijogador.findByKey", query = "SELECT p FROM PartidaMultijogador p WHERE p.id = :key")
@NamedQuery(name = "Partida_multijogador.findAll", query = "SELECT p FROM PartidaMultijogador p")
@Table(name = "partida_multijogador", schema = "public")
public class PartidaMultijogador implements MultiPlayerMatch {
    @EmbeddedId
    private PartidaMultijogadorId id;

    @MapsId("matchId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr", referencedColumnName = "nr", nullable = false)
    })
    private Partida match;

    @Column(name = "estado", length = 20)
    private String state;

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    @Override
    public PartidaMultijogadorId getId() {
        return id;
    }

    /**
     * Setter function for the match id
     *
     * @param id the match id
     */
    @Override
    public void setId(PartidaMultijogadorId id) {
        this.id = id;
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchId().getMatchNumber();
    }


    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getMatchId().getGameId();
    }

    /**
     * Getter function for the game status
     *
     * @return the game status
     */
    @Override
    public MultiPlayerMatchState getState() {
        return switch (this.state.toLowerCase()) {
            case "por iniciar" -> MultiPlayerMatchState.NOT_STARTED;
            case "a aguardar jogadores" -> MultiPlayerMatchState.WAITING_FOR_PLAYERS;
            case "em curso" -> MultiPlayerMatchState.IN_PROGRESS;
            case "terminada" -> MultiPlayerMatchState.FINISHED;
            default -> throw new IllegalStateException("Unexpected value: " + this.state.toUpperCase());
        };
    }

    /**
     * Setter function for the game status
     *
     * @param state the game status
     */
    @Override
    public void setState(MultiPlayerMatchState state) {
        this.state = switch (state) {
            case NOT_STARTED -> "Por iniciar";
            case WAITING_FOR_PLAYERS -> "A aguardar jogadores";
            case IN_PROGRESS -> "Em curso";
            case FINISHED -> "Terminada";
        };
    }

    /**
     * Getter function for the match
     *
     * @return the match
     */
    @Override
    public Match getMatch() {
        return this.match;
    }

    /**
     * Setter function for the match
     *
     * @param match the match
     */
    @Override
    public void setMatch(Match match) {
        this.match = (Partida) match;
    }
}