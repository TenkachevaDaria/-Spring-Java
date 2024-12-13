package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import tenkacheva.work.app.models.Task;
import tenkacheva.work.app.models.TaskStatus;
import tenkacheva.work.app.models.User;
import tenkacheva.work.app.repositories.TaskRepository;
import tenkacheva.work.app.repositories.UserRepository;

import java.time.Instant;
import java.util.Set;

@SpringBootApplication
public class Application {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public Application(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @EventListener
    public void handleApplicationReadyEvent(ApplicationReadyEvent ignored) {
        userRepository.deleteAll().block();
        taskRepository.deleteAll().block();

        var user1 = userRepository.save(new User("1", "username1", "email1@mail.mail")).block();
        var user2 = userRepository.save(new User("2", "username2", "email2@mail.mail")).block();
        var user3 = userRepository.save(new User("3", "username3", "email3@mail.mail")).block();

        assert user1 != null && user2 != null && user3 != null;

        taskRepository.save(new Task("1", "Task1", "description1", Instant.now(), null, TaskStatus.TODO, user1.getId(), user2.getId(), Set.of(user3.getId()))).block();
        taskRepository.save(new Task("2", "Task2", "description2", Instant.now(), null, TaskStatus.TODO, user2.getId(), user1.getId(), Set.of(user3.getId()))).block();
    }
}
