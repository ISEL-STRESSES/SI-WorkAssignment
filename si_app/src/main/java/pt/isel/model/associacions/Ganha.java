package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.game.badge.Cracha;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "ganha", schema = "public")
public class Ganha {
    @EmbeddedId
    private GanhaId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nome_cracha", referencedColumnName = "nome", nullable = false)
    })
    private Cracha cracha;

    public GanhaId getId() {
        return id;
    }

    public void setId(GanhaId id) {
        this.id = id;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public Cracha getCracha() {
        return cracha;
    }

    public void setCracha(Cracha cracha) {
        this.cracha = cracha;
    }

}