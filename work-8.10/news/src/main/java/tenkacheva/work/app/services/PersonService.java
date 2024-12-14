package tenkacheva.work.app.services;

import org.springframework.data.domain.Pageable;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.models.Role;

import java.util.List;

public interface PersonService {

    PersonDTO get(long id);

    long add(String name, String password, Role role);

    List<PersonDTO> getAll(Pageable pageable);

    void update(long id, String name, String password);

    boolean delete(long id);

    Long validate(String name, String password);
}
