package tenkacheva.work.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.dtos.CategoryDTO;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.services.CategoryService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService service;

    @Autowired
    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public CategoryDTO get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestParam String name) {
        long id = service.add(name);
        return ResponseEntity
                .created(URI.create("/api/category/" + id))
                .body(new PersonDTO(id, name));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @PathVariable long id,
            @RequestParam String name
    ) {
        service.update(id, name);
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
