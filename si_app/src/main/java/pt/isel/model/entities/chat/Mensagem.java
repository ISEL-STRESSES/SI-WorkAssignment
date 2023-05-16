package pt.isel.model.entities.chat;

import jakarta.persistence.*;
import pt.isel.model.entities.player.Jogador;

import java.time.LocalDate;

@Entity
@Table(name = "mensagem", schema = "public")
public class Mensagem {
    @EmbeddedId
    private MensagemId id;

    @MapsId("idConversa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_conversa", nullable = false)
    private Conversa idConversa;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @Lob
    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    public MensagemId getId() {
        return id;
    }

    public void setId(MensagemId id) {
        this.id = id;
    }

    public Conversa getIdConversa() {
        return idConversa;
    }

    public void setIdConversa(Conversa idConversa) {
        this.idConversa = idConversa;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

}