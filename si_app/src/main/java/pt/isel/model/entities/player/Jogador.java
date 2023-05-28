package pt.isel.model.entities.player;

import jakarta.persistence.*;
import pt.isel.model.associacions.earns.Ganha;
import pt.isel.model.associacions.friend.Amigo;
import pt.isel.model.associacions.participates.Participa;
import pt.isel.model.associacions.plays.Joga;
import pt.isel.model.associacions.purchase.Compra;
import pt.isel.model.entities.chat.Mensagem;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.region.Regiao;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Email;
import pt.isel.model.types.PlayerState;

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
                name = "updatePlayerStatus",
                procedureName = "update_estado_jogador",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "id", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_estado", type = String.class)
                }
        ),
        @NamedStoredProcedureQuery(
                name = "createPlayer",
                procedureName = "create_jogador",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "regiao_nome", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_username", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_email", type = String.class),
                }
        ),
        @NamedStoredProcedureQuery(
                name = "createPlayerTransaction",
                procedureName = "createJogadorTransaction",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "regiao_nome", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_username", type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, name = "new_email", type = String.class),
                }
        )
}
)
@Table(name = "jogador", schema = "public")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nome_regiao", referencedColumnName = "nome", nullable = false)
    private Regiao region;

    @OneToOne(mappedBy = "player")
    private JogadorEstatistica stats;

    @OneToMany(mappedBy = "playerId", orphanRemoval = true)
    private Set<Mensagem> messages = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "amigo",
            joinColumns = {
                    @JoinColumn(name = "id_jogador1", referencedColumnName = "id"),
                    @JoinColumn(name = "id_jogador2", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "id_jogador1", referencedColumnName = "id"),
                    @JoinColumn(name = "id_jogador2", referencedColumnName = "id")
            }
    )
    private Set<Amigo> friends = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPlayer", orphanRemoval = true)
    private Set<Ganha> badges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPlayer", orphanRemoval = true)
    private Set<Participa> chats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPlayer", orphanRemoval = true)
    private Set<Compra> purchases = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPlayer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Joga> playMatches = new LinkedHashSet<>();

    // Constructors
    public Jogador() {
    }

    public Jogador(String username, Email email, PlayerState estado, Region region) {
        setUsername(username);
        setEmail(email);
        setState(estado);
        setRegion(region);
    }

    public Jogador(String username, Email email, Region region) {
        this(username, email, PlayerState.ACTIVE, region);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String userName) {
        this.username = userName;
    }

    @Override
    public Email getEmail() {
        return new Email(this.email);
    }

    @Override
    public void setEmail(Email email) {
        if (!email.isValid())
            throw new IllegalArgumentException("Email inválido");
        this.email = email.toString();
    }

    @Override
    public PlayerState getState() {
        return switch (this.state.toLowerCase()) {
            case "ativo" -> PlayerState.ACTIVE;
            case "inativo" -> PlayerState.INACTIVE;
            case "banido" -> PlayerState.BANED;
            default -> throw new IllegalStateException("Unexpected value: " + this.state);
        };
    }

    @Override
    public void setState(PlayerState state) {
        this.state = switch (state) {
            case ACTIVE -> "Ativo";
            case INACTIVE -> "Inativo";
            case BANED -> "Banido";
        };
    }

    @Override
    public Region getRegion() {
        return this.region;
    }

    @Override
    public void setRegion(Region region) {
        this.region = (Regiao) region;
    }

    @Override
    public PlayerStats getStats() {
        return this.stats;
    }

    @Override
    public void setStats(PlayerStats stats) {
        this.stats = (JogadorEstatistica) stats;
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
    public void setMessages(Set<Message> messages) {
        this.messages = messages.stream().map(m -> (Mensagem) m).collect(LinkedHashSet::new, Set::add, Set::addAll);
    }

    @Override
    public Set<Amigo> getFriends() {
        return friends;
    }

    @Override
    public void setFriends(Set<Amigo> friends) {
        this.friends = friends;
    }

    @Override
    public Set<Ganha> getBadges() {
        return badges;
    }

    @Override
    public void setBadges(Set<Ganha> badges) {
        this.badges = badges;
    }

    @Override
    public Set<Participa> getChats() {
        return chats;
    }

    @Override
    public void setChats(Set<Participa> chats) {
        this.chats = chats;
    }

    @Override
    public Set<Compra> getPurchases() {
        return purchases;
    }

    @Override
    public void setPurchases(Set<Compra> purchases) {
        this.purchases = purchases;
    }

    @Override
    public Set<Joga> getPlayMatches() {
        return playMatches;
    }

    @Override
    public void setPlayMatches(Set<Joga> playMatches) {
        this.playMatches = playMatches;
    }

    @Override
    public boolean addMessage(Message message) {
        return this.messages.add((Mensagem) message);
    }

    @Override
    public boolean removeMessage(Message message) {
        return this.messages.remove((Mensagem) message);
    }

    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", estado='" + state + '\'' +
                ", nomeRegiao='" + region.getId() + '\'' +
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
