package tenkacheva.work.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tenkacheva.work.app.models.*;
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
            categoryRepository.deleteAll();
            commentRepository.deleteAll();
            newsRepository.deleteAll();
            personRepository.deleteAll();

            Category category = new Category("category");
            categoryRepository.save(category);

            Category category2 = new Category("category2");
            categoryRepository.save(category2);

            Person person = new Person("admin", "admin123", Role.ADMIN);
            News news2 = new News(person, category, "Content");
            personRepository.save(person);
            newsRepository.save(news2);

            person = new Person("user", "user123", Role.USER);
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
