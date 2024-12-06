package tenkacheva.work.app.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tenkacheva.work.app.dtos.CommentDTO;
import tenkacheva.work.app.dtos.ComprehensiveNewsDTO;
import tenkacheva.work.app.dtos.PersonDTO;
import tenkacheva.work.app.exceptions.ControllerException;
import tenkacheva.work.app.services.CommentService;
import tenkacheva.work.app.services.NewsService;
import tenkacheva.work.app.services.PersonService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Aspect
@Component
public class UserAuthorizationAspect {

    private final CommentService commentService;
    private final PersonService personService;
    private final NewsService newsService;

    @Autowired
    public UserAuthorizationAspect(PersonService personService, NewsService newsService, CommentService commentService) {
        this.personService = personService;
        this.newsService = newsService;
        this.commentService = commentService;
    }

    @Before("@annotation(tenkacheva.work.app.annotation.PersonAuthorizationRequired) && "
            + "(args(.., authBasic) || args(authBasic, ..))")
    public PersonDTO authorizePerson(String authBasic) {
        String[] splittedAuth = authBasic.split(" ", 2);
        if (splittedAuth.length != 2) {
            throw new ControllerException("authorization failed", HttpStatus.UNAUTHORIZED);
        }

        String authScheme = splittedAuth[0].trim();
        String authParameters = splittedAuth[1].trim();

        if (!authScheme.equals("Basic")) {
            throw new ControllerException("expected basic auth scheme", HttpStatus.EXPECTATION_FAILED);
        }

        byte[] decodedCredentials;
        try {
            decodedCredentials = Base64.getDecoder().decode(authParameters);
        } catch (IllegalArgumentException exception) {
            throw new ControllerException("expected base64 encoding", HttpStatus.EXPECTATION_FAILED);
        }

        String[] credentials = new String(decodedCredentials, StandardCharsets.UTF_8).split(":", 2);
        String name = credentials[0].trim();
        String password = credentials[1].trim();

        Long id = personService.validate(name, password);
        if (id == null) {
            throw new ControllerException("credentials are invalid", HttpStatus.UNAUTHORIZED);
        }

        return new PersonDTO(id, name);
    }

    @Before("@annotation(tenkacheva.work.app.annotation.PersonAuthorizationRequired) && "
            + "args(authBasic, person, ..)")
    public void authorizePerson(String authBasic, PersonDTO person) {
        PersonDTO authorizedPerson = authorizePerson(authBasic);

        person.setId(authorizedPerson.getId());
        person.setName(authorizedPerson.getName());
    }

    @Before("@annotation(tenkacheva.work.app.annotation.PersonMustOwnNews) && args(id, authBasic, person, ..)")
    public void checkIfPersonOwnsNews(long id, String authBasic, PersonDTO person) {
        PersonDTO personDTO = checkIfPersonOwnsNews(id, authBasic);
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
    }

    @Before("@annotation(tenkacheva.work.app.annotation.PersonMustOwnNews) && args(id, authBasic, ..)")
    public PersonDTO checkIfPersonOwnsNews(long id, String authBasic) {
        PersonDTO person = authorizePerson(authBasic);
        ComprehensiveNewsDTO news = newsService.get(id);

        if (person.getId() != news.authorId()) {
            throw new ControllerException("you must own the news", HttpStatus.FORBIDDEN);
        }

        return person;
    }

    @Before("@annotation(tenkacheva.work.app.annotation.PersonMustOwnComment) && args(id, authBasic, ..)")
    public PersonDTO checkIfPersonOwnsComment(long id, String authBasic) {
        PersonDTO person = authorizePerson(authBasic);
        CommentDTO commentDTO = commentService.get(id);

        if (person.getId() != commentDTO.authorId()) {
            throw new ControllerException("you must own the comment", HttpStatus.FORBIDDEN);
        }

        return person;
    }

    @Before("@annotation(tenkacheva.work.app.annotation.PersonMustOwnComment) && args(id, authBasic, person, ..)")
    public void checkIfPersonOwnsComment(long id, String authBasic, PersonDTO person) {
        PersonDTO personDTO = checkIfPersonOwnsNews(id, authBasic);
        person.setId(personDTO.getId());
        person.setName(personDTO.getName());
    }
}
