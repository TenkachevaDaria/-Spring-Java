package tenkacheva.work.app.models;

import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, length = 32)
    private String name;

    public Category() {

    }

    public Category(String name) {
        setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }

        if (name.length() > 32) {
            throw new IllegalArgumentException("length of name cannot be greater than 32 symbols");
        }

        this.name = name;
    }
}
