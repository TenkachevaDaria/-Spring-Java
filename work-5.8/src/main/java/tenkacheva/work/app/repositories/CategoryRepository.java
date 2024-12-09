package tenkacheva.work.app.repositories;

import org.springframework.data.repository.CrudRepository;
import tenkacheva.work.app.models.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
