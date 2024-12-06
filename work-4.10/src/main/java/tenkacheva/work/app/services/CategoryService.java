package tenkacheva.work.app.services;

import org.springframework.data.domain.Pageable;
import tenkacheva.work.app.dtos.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO get(long id);

    long add(String name);

    List<CategoryDTO> getAll(Pageable pageable);

    void update(Long id, String name);

    boolean delete(long id);
}
