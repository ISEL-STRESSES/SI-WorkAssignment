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
 * Represents a player
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

    /**
     * Empty constructor
     */
    public Jogador() {
    }

    /**
     * Constructor
     *
     * @param username the player username
     * @param email    the player email
     * @param state    the player state
     * @param region   the player region name
     */
    public Jogador(String username, Email email, PlayerState state, Region region) {
        setUsername(username);
        setEmail(email);
        setState(state);
        setRegion(region);
    }

    /**
     * Constructor
     *
     * @param username the player username
     * @param email    the player email
     * @param region   the player region name
     */
    public Jogador(String username, Email email, Region region) {
        this(username, email, PlayerState.ACTIVE, region);
    }

    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * Getter function for the player username
     *
     * @return the player username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Setter function for the player username
     *
     * @param userName the player username
     */
    @Override
    public void setUsername(String userName) {
        this.username = userName;
    }

    /**
     * Getter function for the player email
     *
     * @return the player email
     */
    @Override
    public Email getEmail() {
        return new Email(this.email);
    }

    /**
     * Setter function for the player email
     *
     * @param email the player email
     */
    @Override
    public void setEmail(Email email) {
        if (!email.isValid())
            throw new IllegalArgumentException("Email inválido");
        this.email = email.toString();
    }

    /**
     * Getter function for the player state
     *
     * @return the player state
     */
    @Override
    public PlayerState getState() {
        return switch (this.state.toLowerCase()) {
            case "ativo" -> PlayerState.ACTIVE;
            case "inativo" -> PlayerState.INACTIVE;
            case "banido" -> PlayerState.BANED;
            default -> throw new IllegalStateException("Unexpected value: " + this.state);
        };
    }

    /**
     * Setter function for the player state
     *
     * @param state the player state
     */
    @Override
    public void setState(PlayerState state) {
        this.state = switch (state) {
            case ACTIVE -> "Ativo";
            case INACTIVE -> "Inativo";
            case BANED -> "Banido";
        };
    }

    /**
     * Getter function for the player region
     *
     * @return the player region
     */
    @Override
    public Region getRegion() {
        return this.region;
    }

    /**
     * Setter function for the player region
     *
     * @param region the player region
     */
    @Override
    public void setRegion(Region region) {
        this.region = (Regiao) region;
    }

    /**
     * Getter function for the player stats
     *
     * @return the player stats
     */
    @Override
    public PlayerStats getStats() {
        return this.stats;
    }

    /**
     * Setter function for the player stats
     *
     * @param stats the player stats
     */
    @Override
    public void setStats(PlayerStats stats) {
        this.stats = (JogadorEstatistica) stats;
    }

    /**
     * Getter function for the player messages
     *
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
     * Setter function for the player messages
     *
     * @param messages the player messages
     */
    @Override
    public void setMessages(Set<Message> messages) {
        this.messages = messages.stream().map(m -> (Mensagem) m).collect(LinkedHashSet::new, Set::add, Set::addAll);
    }

    /**
     * Getter function for the player friends
     *
     * @return the player friends
     */
    @Override
    public Set<Amigo> getFriends() {
        return friends;
    }

    /**
     * Setter function for the player friends
     *
     * @param friends the player friends
     */
    @Override
    public void setFriends(Set<Amigo> friends) {
        this.friends = friends;
    }

    /**
     * Getter function for the player badges
     *
     * @return the player badges
     */
    @Override
    public Set<Ganha> getBadges() {
        return badges;
    }

    /**
     * Setter function for the player badges
     *
     * @param badges the player badges
     */
    @Override
    public void setBadges(Set<Ganha> badges) {
        this.badges = badges;
    }

    /**
     * Getter function for the player chats
     *
     * @return the player chats
     */
    @Override
    public Set<Participa> getChats() {
        return chats;
    }

    /**
     * Setter function for the player chats
     *
     * @param chats the player chats
     */
    @Override
    public void setChats(Set<Participa> chats) {
        this.chats = chats;
    }

    /**
     * Getter function for the player purchases
     *
     * @return the player purchases
     */
    @Override
    public Set<Compra> getPurchases() {
        return purchases;
    }

    /**
     * Setter function for the player purchases
     *
     * @param purchases the player purchases
     */
    @Override
    public void setPurchases(Set<Compra> purchases) {
        this.purchases = purchases;
    }

    /**
     * Getter function for the player play matches
     *
     * @return the player play matches
     */
    @Override
    public Set<Joga> getPlayMatches() {
        return playMatches;
    }

    /**
     * Setter function for the player play matches
     *
     * @param playMatches the player play matches
     */
    @Override
    public void setPlayMatches(Set<Joga> playMatches) {
        this.playMatches = playMatches;
    }

    /**
     * Getter function for the player messages
     *
     * @return the player messages
     */
    @Override
    public boolean addMessage(Message message) {
        return this.messages.add((Mensagem) message);
    }

    /**
     * Setter function for the player messages
     *
     * @param message the player messages
     */
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
