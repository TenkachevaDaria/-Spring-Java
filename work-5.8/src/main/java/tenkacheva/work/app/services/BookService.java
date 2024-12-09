package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tenkacheva.work.app.dtos.BookDTO;
import tenkacheva.work.app.mappers.BookMapper;
import tenkacheva.work.app.models.Book;
import tenkacheva.work.app.models.Category;
import tenkacheva.work.app.repositories.BookRepository;
import tenkacheva.work.app.repositories.CategoryRepository;

import java.util.List;

@Service
public class BookService {

    private final BookMapper mapper;

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    private final CacheManager cacheManager;

    @Autowired
    public BookService(BookMapper mapper, BookRepository bookRepository, CategoryRepository categoryRepository, CacheManager cacheManager) {
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "searchingBookByTitleAndAuthor", key = "#title + #author")
    public BookDTO getByTitleAndAuthor(String title, String author) {
        return bookRepository
                .findOneByTitleAndAuthor(title, author)
                .map(mapper::bookToBookDTO)
                .orElse(null);
    }

    @Cacheable(value = "searchingBookByTitleAndAuthor", key = "#categoryName")
    public List<BookDTO> getByCategoryName(String categoryName) {
        return bookRepository
                .findByCategoryName(categoryName)
                .stream()
                .map(mapper::bookToBookDTO)
                .toList();
    }

    @CacheEvict(value = "searchingBookByTitleAndAuthor", key = "#title + #author")
    public long add(String title, String author, String categoryName) {
        var category = categoryRepository
                .findByName(categoryName)
                .orElse(new Category(null, categoryName));
        var book = new Book(null, title, author, category);
        return bookRepository.save(book).getId();
    }

    @CacheEvict(value = "searchingBookByTitleAndAuthor", key = "#title + #author")
    public boolean update(long id, String title, String author, String categoryName) {
        var book = bookRepository
                .findById(id)
                .orElse(null);

        if (book == null) {
            return false;
        }

        if (title != null) {
            book.setTitle(title);
        }

        if (author != null) {
            book.setAuthor(author);
        }

        if (categoryName != null) {
            var category = categoryRepository
                    .findByName(categoryName)
                    .orElse(new Category(null, categoryName));

            book.setCategory(category);
        }


        bookRepository.save(book);
        return true;
    }

    public boolean delete(long id) {
        var book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return false;
        }

        var cacheKey = book.getTitle() + book.getAuthor();
        var cache = cacheManager.getCache("searchingBookByTitleAndAuthor");
        if (cache != null) {
            cache.evictIfPresent(cacheKey);
        }
        bookRepository.deleteById(id);
        return true;
    }
}
