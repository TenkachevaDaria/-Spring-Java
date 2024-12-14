package tenkacheva.work.app.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import tenkacheva.work.app.models.Category;

public interface CategoryRepository extends ListPagingAndSortingRepository<Category, Long>, ListCrudRepository<Category, Long> {
    boolean existsByName(String name);
}
