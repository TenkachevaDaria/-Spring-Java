package tenkacheva.work.app.repositories;

import org.springframework.data.repository.CrudRepository;
import tenkacheva.work.app.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByCategoryName(String name);
    Optional<Book> findOneByTitleAndAuthor(String title, String author);
}
