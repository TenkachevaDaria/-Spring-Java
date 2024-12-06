package tenkacheva.work.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.annotation.PersonAuthorizationRequired;
import tenkacheva.work.app.annotation.PersonMustOwnComment;
import tenkacheva.work.app.dtos.CommentDTO;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    private final CommentService service;

    @Autowired
    public CommentRestController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/all/{newsId}")
    public List<CommentDTO> getAll(@PathVariable long newsId) {
        return service.getAll(newsId);
    }

    @GetMapping("/{id}")
    public CommentDTO get(@PathVariable long id) {
        return service.get(id);
    }

    @PostMapping
    @PersonAuthorizationRequired
    public CommentDTO add(
            @RequestHeader("Authorization") String authBasic,
            PersonDTO person,
            @RequestParam long newsId,
            @RequestParam String content
    ) {
        long id = service.add(person.getId(), newsId, content);
        return new CommentDTO(id, person.getId(), newsId, content);
    }

    @PutMapping("/{id}")
    @PersonMustOwnComment
    public void update(
            @PathVariable long id,
            @RequestHeader("Authorization") String authBasic,
            PersonDTO person,
            @RequestParam long newsId,
            @RequestParam String content
    ) {
        service.update(id, person.getId(), newsId, content);
    }

    @DeleteMapping("/{id}")
    @PersonMustOwnComment
    public ResponseEntity<Object> delete(
            @PathVariable long id,
            @RequestHeader("Authorization") String authBasic
    ) {
        if (service.delete(id)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.noContent().build();
    }
}
