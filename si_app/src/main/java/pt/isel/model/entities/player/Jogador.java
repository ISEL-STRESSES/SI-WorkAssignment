package pt.isel.model.entities.player;

import jakarta.persistence.*;
import pt.isel.model.entities.chat.Mensagem;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.types.Email;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Represents a player
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

    /**
     * Getter function for the player id
     * @return the player id
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * Getter function for the player username
     * @return the player username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter function for the player email
     * @return the player email
     */
    @Override
    public Email getEmail() {
        return new Email(this.email);
    }

    /**
     * Getter function for the player state
     * @return the player state
     */
    @Override
    public String getState() {
        return this.state;
    }

    /**
     * Getter function for the player region name
     * @return the player region name
     */
    @Override
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * Setter function for the player id
     * @param id the player id
     */
    @Override
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Setter function for the player email
     * @param email the player email
     */
    @Override
    public void setEmail(Email email) {
        if (!email.isValid())
            throw new IllegalArgumentException("Email inválido");
        this.email = email.toString();
    }

    /**
     * Setter function for the player state
     * @param state the player state
     */
    @Override
    public void setState(String state) {
        if(state.matches("^(ativo|banido|inativo)$"))
            this.state = state;
        else
            throw new IllegalArgumentException("Estado inválido");
    }

    /**
     * Setter function for the player region name
     * @param regionName the player region name
     */
    @Override
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * Getter function for the player stats
     * @return the player stats
     */
    @Override
    public PlayerStats getStats() {
        return this.stats;
    }

    /**
     * Getter function for the player messages
     * @return the player messages
     */
    @Override
    public Set<Message> getMessages() {
        return this.messages.stream().collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    /**
     * Setter function for the player stats
     * @param stats the player stats
     */
    @Override
    public void setStats(PlayerStats stats) {
        this.stats = (JogadorEstatistica) stats;
    }

    /**
     * Setter function for the player messages
     * @param messages the player messages
     */
    @Override
    public void setMessages(Set<Message> messages) {
        this.messages = messages.stream().map(m -> (Mensagem) m).collect(LinkedHashSet::new, Set::add, Set::addAll);
    }

    /**
     * Empty constructor
     */
    public Jogador() {
    }

    /**
     * Constructor
     * @param username the player username
     * @param email the player email
     * @param estado the player state
     * @param nomeRegiao the player region name
     */
    public Jogador(String username, Email email, String estado, String nomeRegiao) {
        setUsername(username);
        setEmail(email);
        setState(estado);
        setRegionName(nomeRegiao);
    }

    /**
     * Constructor
     * @param username the player username
     * @param email the player email
     * @param nomeRegiao the player region name
     */
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

