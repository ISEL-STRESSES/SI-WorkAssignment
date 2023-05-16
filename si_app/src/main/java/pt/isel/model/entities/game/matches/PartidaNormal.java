package pt.isel.model.entities.game.matches;

import jakarta.persistence.*;

@Entity
@Table(name = "partida_normal", schema = "public")
public class PartidaNormal {
    @EmbeddedId
    private PartidaNormalId id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida partida;

    @Column(name = "dificuldade", nullable = false)
    private Integer dificuldade;

    public PartidaNormalId getId() {
        return id;
    }

    public void setId(PartidaNormalId id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Integer getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Integer dificuldade) {
        this.dificuldade = dificuldade;
    }

}