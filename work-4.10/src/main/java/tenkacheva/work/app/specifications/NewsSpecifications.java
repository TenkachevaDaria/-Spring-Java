package tenkacheva.work.app.specifications;

import org.springframework.data.jpa.domain.Specification;
import tenkacheva.work.app.models.Category_;
import tenkacheva.work.app.models.News;
import tenkacheva.work.app.models.News_;
import tenkacheva.work.app.models.Person_;

public class NewsSpecifications {
    public static Specification<News> hasCategory(Long id) {
        return (root, query, builder)
                -> id == null ? builder.conjunction()
                : builder.equal(root.get(News_.CATEGORY).get(Category_.ID), id);
    }

    public static Specification<News> hasAuthor(Long id) {
        return (root, query, builder)
                -> id == null ? builder.conjunction()
                : builder.equal(root.get(News_.AUTHOR).get(Person_.ID), id);
    }
}
