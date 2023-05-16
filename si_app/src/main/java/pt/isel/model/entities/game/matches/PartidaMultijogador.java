package pt.isel.model.entities.game.matches;

import jakarta.persistence.*;

@Entity
@Table(name = "partida_multijogador", schema = "public")
public class PartidaMultijogador {
    @EmbeddedId
    private PartidaMultijogadorId id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida partida;

    @Column(name = "estado", length = 20)
    private String estado;

    public PartidaMultijogadorId getId() {
        return id;
    }

    public void setId(PartidaMultijogadorId id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}