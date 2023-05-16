package pt.isel.model.classImp;

import jakarta.persistence.*;
import pt.isel.model.Player;
import pt.isel.model.PlayerStats;
import pt.isel.model.types.Email;


/**
 * Mapping of table "Jogador" present in the database.
 */
@Entity
@NamedQuery(name = "Jogador.findByKey", query = "SELECT j FROM Jogador j where j.id = :id")
@NamedQuery(name = "Jogador.findByUsername", query = "SELECT j FROM Jogador j where j.username = :username")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
            name="updatePlayerStatus",
            procedureName = "update_estado_jogador",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_estado", type = String.class)
            }
        ),
        @NamedStoredProcedureQuery(
            name="createPlayer",
            procedureName = "create_jogador",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "regiao_nome", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_username", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_email", type = String.class),
            }
        ),
        @NamedStoredProcedureQuery(
                name="createPlayerTransaction",
                procedureName = "createJogadorTransaction",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "regiao_nome", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_username", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_email", type = String.class),
                }
        )
}
)
@Table(name="jogador", schema = "public")
public class Jogador implements Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "estado", columnDefinition = "VARCHAR(20) DEFAULT 'Ativo'")
    private String estado;

    @Column(name = "nome_regiao", nullable = false)
    private String nomeRegiao;

    @OneToOne(mappedBy = "jogador")
    private JogadorEstatistica estatistica;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getUserName() {
        return this.username;
    }

    @Override
    public Email getEmail() {
        return new Email(this.email);
    }

    @Override
    public String getEstado() {
        return this.estado;
    }

    @Override
    public String getNomeRegiao() {
        return this.nomeRegiao;
    }

    @Override
    public void setUserName(String userName) {
        this.username = userName;
    }

    @Override
    public void setEmail(Email email) {
        this.email = email.toString();
    }

    @Override
    public void setEstado(String estado) {
        if(estado.matches("^(ativo|banido|inativo)$"))
            this.estado = estado;
        else
            throw new IllegalArgumentException("Estado inválido");
    }

    @Override
    public void setNomeRegiao(String nomeRegiao) {
        this.nomeRegiao = nomeRegiao;
    }

    @Override
    public JogadorEstatistica getJogadorEstatistica() {
        return this.estatistica;
    }

    @Override
    public void setJogadorEstatistica(PlayerStats estatistica) {
        this.estatistica = (JogadorEstatistica) estatistica;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    // Constructors
    public Jogador() {
    }

    public Jogador(String username, Email email, String estado, String nomeRegiao) {
        setUserName(username);
        setEmail(email);
        setEstado(estado);
        setNomeRegiao(nomeRegiao);
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + estado + '\'' +
                ", nomeRegiao='" + nomeRegiao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogador jogador)) return false;
        return username.equals(jogador.username) && email.equals(jogador.email);
    }

    @Override
    public int hashCode() {
        return username.hashCode() + email.hashCode();
    }
}

