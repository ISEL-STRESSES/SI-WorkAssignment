package pt.isel.model.entities.chat;

import jakarta.persistence.*;

@Entity
@NamedQuery(name= "Conversa.findByKey", query = "SELECT c FROM Conversa c WHERE c.id = :key")
@NamedQuery(name= "Conversa.findAll", query = "SELECT c FROM Conversa c")
@Table(name = "conversa", schema = "public")
public class Conversa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}