package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tenkacheva.work.app.dtos.CategoryDTO;
import tenkacheva.work.app.exceptions.ExistingException;
import tenkacheva.work.app.exceptions.NotExistingException;
import tenkacheva.work.app.exceptions.ServiceException;
import tenkacheva.work.app.mappers.CategoryMapper;
import tenkacheva.work.app.models.Category;
import tenkacheva.work.app.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDTO get(long id) {
        return repository
                .findById(id)
                .map(mapper::categoryToCategoryDTO)
                .orElseThrow(() -> new NotExistingException("a category with same id does not exist"));
    }

    @Override
    public long add(String name) {
        Category category;
        try {
            category = new Category(name);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        if (repository.existsByName(name)) {
            throw new ExistingException("a category with same name already exists");
        }

        return repository.save(category).getId();
    }

    @Override
    public List<CategoryDTO> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(mapper::categoryToCategoryDTO)
                .toList();
    }

    @Override
    public void update(Long id, String name) {
        Category category;
        try {
            category = new Category(name);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        if (repository.existsByName(name)) {
            throw new ServiceException("a category with same name already exists");
        }

        if (!repository.existsById(id)) {
            throw new NotExistingException("a category with that id doesn't exist");
        }

        repository.save(category);
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
