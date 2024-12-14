package tenkacheva.work.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.annotation.PersonAuthorizationRequired;
import tenkacheva.work.app.annotation.PersonMustOwnNews;
import tenkacheva.work.app.annotation.SecurityNewsOperation;
import tenkacheva.work.app.dtos.ComprehensiveNewsDTO;
import tenkacheva.work.app.dtos.NewsDTO;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.services.NewsService;

import java.security.Principal;
import java.util.List;

import static tenkacheva.work.app.specifications.NewsSpecifications.hasAuthor;
import static tenkacheva.work.app.specifications.NewsSpecifications.hasCategory;

@RestController
@RequestMapping("/api/news")
public class NewsRestController {

    private final NewsService service;

    @Autowired
    public NewsRestController(NewsService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<NewsDTO> getAll(
            Pageable pageable,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long authorId
    ) {
        return service.getAll(hasAuthor(authorId).and(hasCategory(categoryId)), pageable);
    }

    @GetMapping("{id}")
    public ComprehensiveNewsDTO get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    @PersonAuthorizationRequired
    public NewsDTO add(
            @RequestHeader("Authorization") String authBasic,
            PersonDTO person,
            @RequestParam long categoryId,
            @RequestParam String content
    ) {
        long id = service.add(person.getId(), categoryId, content);
        return new NewsDTO(id, person.getId(), categoryId, content, 0);
    }

    @PutMapping("/{id}")
    @PersonMustOwnNews
    @SecurityNewsOperation
    public void update(
            @PathVariable long id,
            @RequestHeader("Authorization") String authBasic,
            PersonDTO person,
            @RequestParam long categoryId,
            @RequestParam String content,
            Principal principal
    ) {
        service.update(id, person.getId(), categoryId, content);
    }

    @DeleteMapping("/{id}")
    @PersonMustOwnNews
    @SecurityNewsOperation
    public ResponseEntity<Object> delete(
            @PathVariable long id,
            @RequestHeader("Authorization") String authBasic,
            Principal principal
    ) {
        if (service.delete(id)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.noContent().build();
    }
}
