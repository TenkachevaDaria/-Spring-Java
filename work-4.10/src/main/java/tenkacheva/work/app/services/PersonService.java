package tenkacheva.work.app.services;

import org.springframework.data.domain.Pageable;
import tenkacheva.work.app.dtos.PersonDTO;

import java.util.List;

public interface PersonService {

    PersonDTO get(String name);

    long add(String name, String password);

    List<PersonDTO> getAll(Pageable pageable);

    void update(long id, String name, String password);

    boolean delete(long id);

    Long validate(String name, String password);
}
