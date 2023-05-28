package pt.isel.model.entities.chat;

import jakarta.persistence.*;
import pt.isel.model.associacions.participates.Participa;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "Conversa.findByKey", query = "SELECT c FROM Conversa c WHERE c.id = :key")
@NamedQuery(name = "Conversa.findAll", query = "SELECT c FROM Conversa c")
@Table(name = "conversa", schema = "public")
public class Conversa implements Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @OneToMany(mappedBy = "chatId", orphanRemoval = true)
    private Set<Mensagem> messages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idChat", orphanRemoval = true)
    private Set<Participa> players = new LinkedHashSet<>();

    /**
     * Default empty constructor
     */
    public Conversa() {
    }

    /**
     * Constructor for the Conversa entity
     *
     * @param id   the chat id
     * @param nome the chat name
     */
    public Conversa(Integer id, String nome) {
        setId(id);
        setName(nome);
    }

    /**
     * Getter function for the chat id
     *
     * @return the chat id
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter function for the chat id
     *
     * @param id the chat id
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter function for the chat name
     *
     * @return the chat name
     */
    @Override
    public String getName() {
        return this.nome;
    }

    /**
     * Setter function for the chat name
     *
     * @param name the chat name
     */
    @Override
    public void setName(String name) {
        this.nome = name;
    }

    /**
     * Getter function for the chat messages
     *
     * @return the chat messages
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
     * Setter function for the chat messages
     *
     * @param messages the chat messages
     */
    @Override
    public void setMessages(Set<Message> messages) {
        this.messages = messages.stream().map(
                m -> (Mensagem) m
        ).collect(
                LinkedHashSet::new,
                Set::add,
                Set::addAll
        );
    }

    @Override
    public Set<Participa> getPlayers() {
        return players;
    }

    @Override
    public void setPlayers(Set<Participa> players) {
        this.players = players;
    }
}