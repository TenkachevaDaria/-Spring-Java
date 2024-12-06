package tenkacheva.work.app.models;

import jakarta.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String name;

    @Column(nullable = false, length = 32)
    private String password;

    public Person() {

    }

    public Person(String name, String password) {
        setName(name);
        setPassword(password);
    }

    public Person(long id, String name, String password) {
        this(name, password);
        setId(id);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.isBlank()) {
            throw new IllegalArgumentException("password cannot be empty");
        }

        if (password.length() > 32) {
            throw new IllegalArgumentException("length of password cannot be greater than 32 symbols");
        }

        this.password = password;
    }
}
