package tenkacheva.work.app.repositories;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import tenkacheva.work.app.models.Comment;

public interface CommentRepository extends ListPagingAndSortingRepository<Comment, Long>, ListCrudRepository<Comment, Long> {
}
