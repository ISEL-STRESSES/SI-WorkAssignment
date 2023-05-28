package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.player.Jogador;

/**
 * This class represents a friendship between two players.
 */
@Entity
@Table(name = "amigo", schema = "public")
public class Amigo {
    @EmbeddedId
    private AmigoId id;

    @MapsId("idJogador1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador1", nullable = false)
    private Jogador idJogador1;

    @MapsId("idJogador2")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador2", nullable = false)
    private Jogador idJogador2;

    /**
     * Getter function for the friendship id
     * @return the friendship id
     */
    public AmigoId getId() {
        return id;
    }

    /**
     * Setter function for the friendship id
     * @param id the friendship id
     */

    public void setId(AmigoId id) {
        this.id = id;
    }

    /**
     * Getter function for the first player in the friendship
     * @return the first player
     */
    public Jogador getIdJogador1() {
        return idJogador1;
    }

    /**
     * Setter function for the first player in the friendship
     * @param idJogador1 the first player
     */
    public void setIdJogador1(Jogador idJogador1) {
        this.idJogador1 = idJogador1;
    }

    /**
     * Getter function for the second player in the friendship
     * @return the second player
     */
    public Jogador getIdJogador2() {
        return idJogador2;
    }

    /**
     * Setter function for the second player in the friendship
     * @param idJogador2 the second player
     */
    public void setIdJogador2(Jogador idJogador2) {
        this.idJogador2 = idJogador2;
    }

}