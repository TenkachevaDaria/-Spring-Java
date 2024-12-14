package tenkacheva.work.app.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Person author;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "news", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Column(nullable = false, length = 1024)
    private String content;

    public News() {

    }

    public News(long id, Person author, Category category, String content) {
        this(author, category, content);
        setId(id);
    }

    public News(Person author, Category category, String content) {
        setAuthor(author);
        setCategory(category);
        setContent(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content.isBlank()) {
            throw new IllegalArgumentException("content cannot be empty");
        }

        if (content.length() > 1024) {
            throw new IllegalArgumentException("length of content cannot be greater than 1024 symbols");
        }

        this.content = content;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
