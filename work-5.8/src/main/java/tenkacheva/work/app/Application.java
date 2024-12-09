package tenkacheva.work.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tenkacheva.work.app.models.Book;
import tenkacheva.work.app.models.Category;
import tenkacheva.work.app.repositories.BookRepository;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner runner(BookRepository bookRepository) {
        return args -> {
            var fiction = new Category(null, "fiction");
            var nonFiction = new Category(null, "non-fiction");

            var book1 = new Book(null, "The Great Gatsby", "F. Scott Fitzgerald", fiction);
            var book2 = new Book(null, "1984", "George Orwell", fiction);
            var book3 = new Book(null, "Sapiens: A Brief History of Humankind", "Yuval Noah Harari", nonFiction);
            var book4 = new Book(null, "Educated", "Tara Westover", nonFiction);
            bookRepository.saveAll(List.of(book1, book2, book3, book4));
        };
    }
}
