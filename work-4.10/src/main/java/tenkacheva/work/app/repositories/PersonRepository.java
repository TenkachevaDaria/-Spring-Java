package tenkacheva.work.app.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import tenkacheva.work.app.models.Person;

import java.util.Optional;

public interface PersonRepository extends ListPagingAndSortingRepository<Person, Long>, ListCrudRepository<Person, Long> {
    boolean existsByName(String name);
    Optional<Person> findByName(String name);
}
