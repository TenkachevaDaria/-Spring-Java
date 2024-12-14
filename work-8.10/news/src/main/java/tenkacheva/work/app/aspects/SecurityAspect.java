package tenkacheva.work.app.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tenkacheva.work.app.annotation.SecurityCommentOperation;
import tenkacheva.work.app.exceptions.ControllerException;
import tenkacheva.work.app.models.Role;
import tenkacheva.work.app.repositories.CommentRepository;
import tenkacheva.work.app.repositories.NewsRepository;
import tenkacheva.work.app.repositories.PersonRepository;

import java.security.Principal;
import java.util.Objects;

@Aspect
@Component
public class SecurityAspect {

    private final PersonRepository personRepository;
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public SecurityAspect(PersonRepository personRepository, NewsRepository newsRepository, CommentRepository commentRepository) {
        this.personRepository = personRepository;
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
    }

    @Before("@annotation(tenkacheva.work.app.annotation.SecurityUserOperation) " +
            "&& args(principal, id, ..)")
    public void checkUserForUserOperationPermission(Principal principal, long id) {
        var person = personRepository.findByName(principal.getName())
                .orElseThrow(() -> new ControllerException("", HttpStatus.UNAUTHORIZED));

        if (person.getId() != id && person.getRole() == Role.USER) {
            throw new ControllerException("Illegal access", HttpStatus.FORBIDDEN);
        }
    }

    @Before("@annotation(tenkacheva.work.app.annotation.SecurityNewsOperation) " +
            "&& args(id, .., principal)")
    public void checkUserForNewsOperationPermission(Principal principal, long id) {
        var person = personRepository.findByName(principal.getName())
                .orElseThrow(() -> new ControllerException("", HttpStatus.UNAUTHORIZED));

        var news = newsRepository.findById(id)
                .orElseThrow(() -> new ControllerException("", HttpStatus.NOT_FOUND));

        if (person.getRole() == Role.USER && !Objects.equals(news.getAuthor().getId(), person.getId())) {
            throw new ControllerException("Illegal access", HttpStatus.FORBIDDEN);
        }
    }

    @Before("@annotation(tenkacheva.work.app.annotation.SecurityCommentOperation) " +
            "&& args(id, .., principal)")
    public void checkUserForCommentOperationPermission(Principal principal, long id) {
        var person = personRepository.findByName(principal.getName())
                .orElseThrow(() -> new ControllerException("", HttpStatus.UNAUTHORIZED));

        var comment = commentRepository.findById(id)
                .orElseThrow(() -> new ControllerException("", HttpStatus.NOT_FOUND));

        if (person.getRole() == Role.USER && !Objects.equals(comment.getAuthor().getId(), person.getId())) {
            throw new ControllerException("Illegal access", HttpStatus.FORBIDDEN);
        }
    }
}
