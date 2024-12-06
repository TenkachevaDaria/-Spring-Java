package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import tenkacheva.work.app.dtos.ComprehensiveNewsDTO;
import tenkacheva.work.app.dtos.NewsDTO;
import tenkacheva.work.app.exceptions.NotExistingException;
import tenkacheva.work.app.exceptions.ServiceException;
import tenkacheva.work.app.mappers.NewsMapper;
import tenkacheva.work.app.models.Category;
import tenkacheva.work.app.models.News;
import tenkacheva.work.app.models.Person;
import tenkacheva.work.app.repositories.CategoryRepository;
import tenkacheva.work.app.repositories.NewsRepository;
import tenkacheva.work.app.repositories.PersonRepository;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsMapper mapper;
    private final NewsRepository newsRepository;
    private final PersonRepository personRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, NewsMapper mapper, PersonRepository personRepository, CategoryRepository categoryRepository) {
        this.mapper = mapper;
        this.newsRepository = newsRepository;
        this.personRepository = personRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ComprehensiveNewsDTO get(long id) {
        return newsRepository
                .findById(id)
                .map(mapper::newsToComprehensiveNewsDTO)
                .orElseThrow(() -> new NotExistingException("a news with same id does not exist"));
    }

    @Override
    public long add(long authorId, long categoryId, String content) {
        Person person = personRepository.findById(authorId).orElse(null);
        if (person == null) {
            throw new NotExistingException("a person with same id doesn't exist");
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new NotExistingException("a category with same id doesn't exist");
        }

        News news;
        try {
            news = new News(person, category, content);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        return newsRepository.save(news).getId();
    }

    @Override
    public List<NewsDTO> getAll(@Nullable Specification<News> specification, Pageable pageable) {
        return newsRepository
                .findAll(specification, pageable)
                .map(mapper::newsToNewsDTO)
                .toList();
    }

    @Override
    public void update(long id, long authorId, long categoryId, String content) {
        Person person = personRepository.findById(authorId).orElse(null);
        if (person == null) {
            throw new NotExistingException("a person with same id doesn't exist");
        }

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            throw new NotExistingException("a category with same id doesn't exist");
        }

        News news;
        try {
            news = new News(id, person, category, content);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        if (!newsRepository.existsById(id)) {
            throw new NotExistingException("a news with same id doesn't exist");
        }

        newsRepository.save(news);
    }

    @Override
    public boolean delete(long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
