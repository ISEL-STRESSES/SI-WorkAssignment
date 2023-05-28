package pt.isel.model.associacions.plays;

import jakarta.persistence.*;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "joga", schema = "public")
public class Joga {
    @EmbeddedId
    private JogaId id;

    @MapsId("matchNr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida match;

    @MapsId("idPlayer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idPlayer;

    @Column(name = "pontuacao")
    private Integer points;

    public JogaId getId() {
        return id;
    }

    public void setId(JogaId id) {
        this.id = id;
    }

    public Partida getMatch() {
        return match;
    }

    public void setMatch(Partida match) {
        this.match = match;
    }

    public Jogador getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Jogador idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}