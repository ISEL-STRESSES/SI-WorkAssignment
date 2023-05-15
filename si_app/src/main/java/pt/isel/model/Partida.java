package pt.isel.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "partida", schema = "public")
public class Partida {
    @EmbeddedId
    private PartidaId id;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nome_regiao", nullable = false)
    private Regiao nomeRegiao;

    public PartidaId getId() {
        return id;
    }

    public void setId(PartidaId id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Regiao getNomeRegiao() {
        return nomeRegiao;
    }

    public void setNomeRegiao(Regiao nomeRegiao) {
        this.nomeRegiao = nomeRegiao;
    }

}