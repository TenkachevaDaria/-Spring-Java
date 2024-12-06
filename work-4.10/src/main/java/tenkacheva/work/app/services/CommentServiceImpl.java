package tenkacheva.work.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tenkacheva.work.app.dtos.CommentDTO;
import tenkacheva.work.app.exceptions.NotExistingException;
import tenkacheva.work.app.exceptions.ServiceException;
import tenkacheva.work.app.mappers.CommentMapper;
import tenkacheva.work.app.models.Comment;
import tenkacheva.work.app.models.News;
import tenkacheva.work.app.models.Person;
import tenkacheva.work.app.repositories.CommentRepository;
import tenkacheva.work.app.repositories.NewsRepository;
import tenkacheva.work.app.repositories.PersonRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper mapper;

    private final CommentRepository commentRepository;
    private final PersonRepository personRepository;
    private final NewsRepository newsRepository;

    @Autowired
    public CommentServiceImpl(
            CommentMapper mapper,
            CommentRepository commentRepository,
            PersonRepository personRepository,
            NewsRepository newsRepository) {
        this.mapper = mapper;
        this.commentRepository = commentRepository;
        this.personRepository = personRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public CommentDTO get(long id) {
        return commentRepository
                .findById(id)
                .map(mapper::commentToCommentDTO)
                .orElseThrow(() -> new NotExistingException("a comment with same id does not exist"));
    }

    @Override
    public long add(long authorId, long newsId, String content) {
        Person person = personRepository.findById(authorId).orElse(null);
        if (person == null) {
            throw new NotExistingException("a person with same id doesn't exist");
        }

        News news = newsRepository.findById(newsId).orElse(null);
        if (news == null) {
            throw new NotExistingException("a news with same id doesn't exist");
        }

        Comment comment;
        try {
            comment = new Comment(person, news, content);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        return commentRepository.save(comment).getId();
    }

    @Override
    public List<CommentDTO> getAll(long newsId) {
        return newsRepository
                .findById(newsId)
                .map(News::getComments)
                .orElseThrow(() -> new NotExistingException("a news with same id doesn't exist"))
                .stream()
                .map(mapper::commentToCommentDTO)
                .toList();
    }

    @Override
    public void update(long id, long authorId, long newsId, String content) {
        Person person = personRepository.findById(authorId).orElse(null);
        if (person == null) {
            throw new NotExistingException("a person with same id doesn't exist");
        }

        News news = newsRepository.findById(newsId).orElse(null);
        if (news == null) {
            throw new NotExistingException("a news with same id doesn't exist");
        }

        Comment comment;
        try {
            comment = new Comment(id, person, news, content);
        } catch (IllegalArgumentException exception) {
            throw new ServiceException(exception.getMessage());
        }

        if (!commentRepository.existsById(id)) {
            throw new NotExistingException("a comment with same id doesn't exist");
        }

        commentRepository.save(comment);
    }

    @Override
    public boolean delete(long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
