package tenkacheva.work.app.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import tenkacheva.work.app.models.News;

public interface NewsRepository extends CrudRepository<News, Long>, JpaSpecificationExecutor<News> {
}
