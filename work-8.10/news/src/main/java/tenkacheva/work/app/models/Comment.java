package tenkacheva.work.app.models;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private News news;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Person author;

    @Column(nullable = false)
    private String content;

    public Comment() {

    }

    public Comment(long id, Person author, News news, String content) {
        this(author, news, content);
        setId(id);
    }

    public Comment(Person author, News news, String content) {
        setAuthor(author);
        setNews(news);
        setContent(content);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }
}
