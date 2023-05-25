package pt.isel.model.entities.player;

import jakarta.persistence.*;
import pt.isel.model.entities.chat.Mensagem;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.types.Email;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Mapping of table "Jogador" present in the database.
 */
@Entity
@NamedQuery(name = "Jogador.findByKey", query = "SELECT j FROM Jogador j where j.id = :id")
@NamedQuery(name = "Jogador.findByUsername", query = "SELECT j FROM Jogador j where j.username = :username")
@NamedQuery(name = "Jogador.findByEmail", query = "SELECT j FROM Jogador j where j.email = :email")
@NamedQuery(name = "Jogador.findAll", query = "SELECT j FROM Jogador j")
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
    private String state;

    @Column(name = "nome_regiao", nullable = false)
    private String regionName;

    @OneToOne(mappedBy = "player")
    private JogadorEstatistica stats;

    @OneToMany(mappedBy = "playerId", orphanRemoval = true)
    private Set<Mensagem> messages = new LinkedHashSet<>();

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Email getEmail() {
        return new Email(this.email);
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public String getRegionName() {
        return this.regionName;
    }

    @Override
    public void setUsername(String userName) {
        this.username = userName;
    }

    @Override
    public void setEmail(Email email) {
        if (!email.isValid())
            throw new IllegalArgumentException("Email inválido");
        this.email = email.toString();
    }

    @Override
    public void setState(String state) {
        if(state.matches("^(ativo|banido|inativo)$"))
            this.state = state;
        else
            throw new IllegalArgumentException("Estado inválido");
    }

    @Override
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public PlayerStats getStats() {
        return this.stats;
    }

    @Override
    public Set<Message> getMessages() {
        return this.messages.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public void setStats(PlayerStats stats) {
        this.stats = (JogadorEstatistica) stats;
    }


    @Override
    public void setMessages(Set<Message> messages) {
        this.messages = messages.stream().map(m -> (Mensagem) m).collect(LinkedHashSet::new, Set::add, Set::addAll);
    }

    // Constructors
    public Jogador() {
    }

    public Jogador(String username, Email email, String estado, String nomeRegiao) {
        setUsername(username);
        setEmail(email);
        setState(estado);
        setRegionName(nomeRegiao);
    }

    public Jogador(String username, Email email, String nomeRegiao) {
        this(username, email, "ativo", nomeRegiao);
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + state + '\'' +
                ", nomeRegiao='" + regionName + '\'' +
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

