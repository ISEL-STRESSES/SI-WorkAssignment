package pt.isel.model.views;

import jakarta.persistence.*;
import pt.isel.model.types.Email;

/**
 * Mapping for DB view
 */
@Entity
@NamedQuery(name = "Jogadortotalinfo.findByKey", query = "SELECT j FROM Jogadortotalinfo j WHERE j.id = :key")
@NamedQuery(name = "Jogadortotalinfo.findAll", query = "SELECT j FROM Jogadortotalinfo j")
@Table(name = "jogadortotalinfo", schema = "public")
public class Jogadortotalinfo {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "estado", length = 20)
    private String estado;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "total_jogos")
    private Integer totalJogos;

    @Column(name = "total_partidas")
    private Integer totalPartidas;
    @Column(name = "total_pontos")
    private Integer totalPontos;

    @Column(name = "email", columnDefinition = "email(0, 0)")
    private String email;

    /**
     * Getter function for the player email
     *
     * @return the player email
     */
    public Email getEmail() {
        return new Email(email);
    }

    /**
     * Setter function for the player email
     *
     * @param email the player email
     */
    public void setEmail(Email email) {
        this.email = email.toString();
    }

    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Getter function for the player state
     *
     * @return the player state
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Getter function for the player username
     *
     * @return the player username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter function for the player total games
     *
     * @return the player total games
     */
    public Integer getTotalJogos() {
        return totalJogos;
    }

    /**
     * Getter function for the player total matches
     *
     * @return the player total matches
     */
    public Integer getTotalPartidas() {
        return totalPartidas;
    }

    /**
     * Getter function for the player total points
     *
     * @return the player total points
     */
    public Integer getTotalPontos() {
        return totalPontos;
    }
}