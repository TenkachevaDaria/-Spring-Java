package tenkacheva.work.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.dtos.BookDTO;
import tenkacheva.work.app.services.BookService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/book")
public class BookRestController {
    private final BookService service;

    @Autowired
    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping(params = {"title", "author"})
    public ResponseEntity<BookDTO> getByTitleAndAuthor(
            @RequestParam("title") String title,
            @RequestParam("author") String author
    ) {
        var book = service.getByTitleAndAuthor(title, author);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @GetMapping(params = "category")
    public List<BookDTO> getByCategoryName(
            @RequestParam String category
    ) {
        return service.getByCategoryName(category);
    }

    @PostMapping
    public BookDTO add(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String category
    ) {
        long id = service.add(title, author, category);
        return new BookDTO(id, title, author, category);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category
    ) {
        if (!service.update(id, title, author, category)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(null);
    }
}
