package tenkacheva.work.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tenkacheva.work.app.models.Category;
import tenkacheva.work.app.models.Comment;
import tenkacheva.work.app.models.News;
import tenkacheva.work.app.models.Person;
import tenkacheva.work.app.repositories.CategoryRepository;
import tenkacheva.work.app.repositories.CommentRepository;
import tenkacheva.work.app.repositories.NewsRepository;
import tenkacheva.work.app.repositories.PersonRepository;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(PersonRepository personRepository, CategoryRepository categoryRepository, NewsRepository newsRepository, CommentRepository commentRepository) {
        return args -> {
            Category category = new Category("category");
            categoryRepository.save(category);

            Category category2 = new Category("category2");
            categoryRepository.save(category2);

            Person person = new Person("admin", "admin123");
            News news2 = new News(person, category, "Content");
            personRepository.save(person);
            newsRepository.save(news2);

            person = new Person("user", "user123");
            personRepository.save(person);

            News news = new News(person, category, "Content");
            newsRepository.save(news);

            news = new News(person, category2, "Content");
            newsRepository.save(news);

            Comment comment = new Comment();
            comment.setNews(news);
            comment.setContent("Hello, world!");
            comment.setAuthor(person);
            commentRepository.save(comment);
        };
    }
}
