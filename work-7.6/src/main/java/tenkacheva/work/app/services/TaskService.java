package tenkacheva.work.app.services;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tenkacheva.work.app.dtos.TaskDTO;
import tenkacheva.work.app.mappers.TaskMapper;
import tenkacheva.work.app.models.Task;
import tenkacheva.work.app.models.TaskStatus;
import tenkacheva.work.app.repositories.TaskRepository;
import tenkacheva.work.app.repositories.UserRepository;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskMapper taskMapper, TaskRepository taskRepository, UserRepository userRepository) {
        this.taskMapper = taskMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Flux<TaskDTO> getAll() {
        return taskRepository
                .findAll()
                .flatMap(task -> userRepository
                        .findById(task.getAuthorId())
                        .map(task::setAuthor)
                ).flatMap(task -> userRepository
                        .findById(task.getAssigneeId())
                        .map(task::setAssignee)
                ).flatMap(task -> userRepository
                        .findAllById(task.getObserverIds())
                        .collectList()
                        .map(users -> task.setObservers(Set.copyOf(users)))
                ).map(taskMapper::taskToTaskDTO);
    }

    public Mono<TaskDTO> get(String id) {
        return taskRepository
                .findById(id)
                .flatMap(task -> userRepository
                        .findById(task.getAuthorId())
                        .map(task::setAuthor)
                ).flatMap(task -> userRepository
                        .findById(task.getAssigneeId())
                        .map(task::setAssignee)
                ).flatMap(task -> userRepository
                        .findAllById(task.getObserverIds())
                        .collectList()
                        .map(users -> task.setObservers(Set.copyOf(users)))
                )
                .map(taskMapper::taskToTaskDTO);
    }

    public Mono<TaskDTO> create(
            String name,
            String description,
            TaskStatus status,
            String authorId,
            String assigneeId,
            Set<String> observerIds) {
        return Mono.just(new Task(null, name, description, Instant.now(), null, status, authorId, assigneeId, observerIds))
                .zipWith(
                        userRepository
                                .findById(authorId)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the author"))),
                        Task::setAuthor
                ).zipWith(
                        userRepository
                                .findById(assigneeId)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the assignee"))),
                        Task::setAssignee
                ).zipWith(
                        Flux.just(observerIds.toArray(String[]::new))
                                .flatMap(observerId -> userRepository
                                        .findById(observerId)
                                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the observer")))
                                )
                                .switchIfEmpty(Flux.error(new IllegalArgumentException("Cannot find the observer")))
                                .collect(Collectors.toSet()),
                        Task::setObservers
                ).flatMap(taskRepository::save)
                .map(taskMapper::taskToTaskDTO);
    }

    public Mono<TaskDTO> update(
            String id,
            String name,
            String description,
            TaskStatus status,
            String authorId,
            String assigneeId,
            Set<String> observerIds) {
        return taskRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the task")))
                .map(task ->
                        new Task(task.getId(), name, description, task.getCreatedAt(), Instant.now(), status, task.getAuthorId(), task.getAssigneeId(), task.getObserverIds()))
                .zipWith(
                        userRepository
                                .findById(authorId)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the author"))),
                        Task::setAuthor
                ).zipWith(
                        userRepository
                                .findById(assigneeId)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the assignee"))),
                        Task::setAssignee
                ).zipWith(
                        Flux.just(observerIds.toArray(String[]::new))
                                .flatMap(observerId -> userRepository
                                        .findById(observerId)
                                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the observer")))
                                )
                                .switchIfEmpty(Flux.error(new IllegalArgumentException("Cannot find the observer")))
                                .collect(Collectors.toSet()),
                        Task::setObservers
                ).flatMap(taskRepository::save)
                .map(taskMapper::taskToTaskDTO);
    }

    public Mono<Void> delete(String id) {
        return taskRepository
                .deleteById(id);
    }

    public Mono<TaskDTO> addObserver(String taskId, String observerId) {
        return taskRepository
                .findById(taskId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the task")))
                .zipWith(
                        userRepository
                                .findById(observerId)
                                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the user"))),
                        (task, user) -> {
                            task.getObserverIds().add(user.getId());
                            return task;
                        }
                )
                .flatMap(taskRepository::save)
                .then(get(taskId));
    }
}
