package tenkacheva.work.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.exceptions.ControllerException;
import tenkacheva.work.app.exceptions.ExistingException;
import tenkacheva.work.app.services.PersonService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonRestController {

    private final PersonService service;

    @Autowired
    public PersonRestController(PersonService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<PersonDTO> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{name}")
    public PersonDTO get(@PathVariable String name) {
        return service.get(name);
    }

    @PostMapping
    public ResponseEntity<?> add(
            @RequestParam String name,
            @RequestParam String password
    ) {
        long id;
        try {
            id = service.add(name, password);
        } catch (ExistingException exception) {
            throw new ControllerException(exception.getMessage(), HttpStatus.CONFLICT);
        }

        return ResponseEntity
                .created(URI.create("/api/person/" + name))
                .body(new PersonDTO(id, name));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @PathVariable long id,
            @RequestParam String name,
            @RequestParam String password
    ) {
        service.update(id, name, password);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable long id
    ) {
        if (service.delete(id)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.noContent().build();
    }
}
