package pt.isel.model.classImp;

import jakarta.persistence.*;
import pt.isel.model.types.Alphanumeric;

@Entity
@Table(name = "jogo", schema = "public", indexes = {
        @Index(name = "jogo_nome_key", columnList = "nome", unique = true)
})
public class Jogo {
    @Id
    @Column(name = "id", columnDefinition = "alphanumeric(0, 0) not null")
    private String id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    public Object getId() {
        return id;
    }

    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

/*
    TODO [JPA Buddy] create field to map the 'url' column
     Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "url", columnDefinition = "url(0, 0) not null")
    private Object url;
*/
}