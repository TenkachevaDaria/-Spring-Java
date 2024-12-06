package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.exceptions.ExistingException;
import tenkacheva.work.app.exceptions.NotExistingException;
import tenkacheva.work.app.exceptions.ServiceException;
import tenkacheva.work.app.mappers.PersonMapper;
import tenkacheva.work.app.models.Person;
import tenkacheva.work.app.repositories.PersonRepository;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Autowired
    public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Long validate(String name, String password) {
        Person person = repository.findByName(name).orElse(null);
        if (person != null && person.getPassword().equals(password)) {
            return person.getId();
        }
        return null;
    }

    @Override
    public PersonDTO get(String name) {
        return repository
                .findByName(name)
                .map(mapper::personToPersonDTO)
                .orElseThrow(() -> new NotExistingException("a person with same name does not exist"));
    }

    @Override
    public long add(String name, String password) {
        Person person;

        try {
            person = new Person(name, password);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        if (repository.existsByName(name)) {
            throw new ExistingException("a person with same name already exists");
        }

        return repository.save(person).getId();
    }

    @Override
    public List<PersonDTO> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .stream()
                .map(mapper::personToPersonDTO)
                .toList();
    }

    @Override
    public void update(long id, String name, String password) {
        Person person;

        try {
            person = new Person(id, name, password);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        if (repository.existsByName(name)) {
            throw new ServiceException("a person with same name already exists");
        }

        if (!repository.existsById(id)) {
            throw new NotExistingException("a person with that id doesn't exist");
        }

        repository.save(person);
    }

    @Override
    public boolean delete(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }
}
