package pt.isel.model.entities.game.badge;

import jakarta.persistence.*;
import pt.isel.model.entities.game.Jogo;
import pt.isel.model.types.URL;

@Entity
@Table(name = "cracha", schema = "public")
public class Cracha {
    @EmbeddedId
    private CrachaId id;

    @MapsId("idJogo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo idJogo;

    @Column(name = "limite_pontos", nullable = false)
    private Integer limitePontos;

    @Column(name = "imagem", columnDefinition = "url(0, 0) not null")
    private String imagem;

    public URL getImagem() {
        return new URL(imagem);
    }

    public void setImagem(URL imagem) {
        this.imagem = imagem.toString();
    }

    public CrachaId getId() {
        return id;
    }

    public void setId(CrachaId id) {
        this.id = id;
    }

    public Jogo getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Jogo idJogo) {
        this.idJogo = idJogo;
    }

    public Integer getLimitePontos() {
        return limitePontos;
    }

    public void setLimitePontos(Integer limitePontos) {
        this.limitePontos = limitePontos;
    }

}