package pt.isel.model.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pt.isel.model.types.Email;

/**
 * Mapping for DB view
 */
@Entity
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

    public Email getEmail() {
        return new Email(email);
    }

    public void setEmail(Email email) {
        this.email = email.toString();
    }

    public Integer getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

    public String getUsername() {
        return username;
    }

    public Integer getTotalJogos() {
        return totalJogos;
    }

    public Integer getTotalPartidas() {
        return totalPartidas;
    }

    public Integer getTotalPontos() {
        return totalPontos;
    }

}