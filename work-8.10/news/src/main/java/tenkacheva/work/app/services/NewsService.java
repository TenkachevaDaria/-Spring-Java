package tenkacheva.work.app.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import tenkacheva.work.app.dtos.ComprehensiveNewsDTO;
import tenkacheva.work.app.dtos.NewsDTO;
import tenkacheva.work.app.models.News;

import java.util.List;

public interface NewsService {
    ComprehensiveNewsDTO get(long id);

    long add(long authorId, long categoryId, String content);

    List<NewsDTO> getAll(@Nullable Specification<News> specification, Pageable pageable);

    void update(long id, long authorId, long categoryId, String content);

    boolean delete(long id);
}
