package tenkacheva.work.app.restControllers;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import tenkacheva.work.app.dtos.RequestTaskDTO;
import tenkacheva.work.app.dtos.TaskDTO;
import tenkacheva.work.app.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskRestController {
    private final TaskService taskService;

    @Autowired
    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public Publisher<TaskDTO> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public Publisher<TaskDTO> getById(@PathVariable String id) {
        return taskService.get(id);
    }

    @PostMapping(consumes = "application/json")
    public Publisher<TaskDTO> create(@RequestBody RequestTaskDTO task) {
        return taskService
                .create(task.name(), task.description(), task.status(), task.authorId(), task.assigneeId(), task.observerIds());
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Publisher<TaskDTO> update(
            @PathVariable String id,
            @RequestBody RequestTaskDTO task
    ) {
        return taskService
                .update(id, task.name(), task.description(), task.status(), task.authorId(), task.assigneeId(), task.observerIds());
    }

    @PutMapping(value = "/{taskId}/{observerId}")
    public Publisher<TaskDTO> addObserver(
            @PathVariable String taskId,
            @PathVariable String observerId
    ) {
        return taskService.addObserver(taskId, observerId);
    }

    @DeleteMapping("/{id}")
    public Publisher<Void> delete(@PathVariable String id) {
        return taskService.delete(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ErrorResponseException(HttpStatus.BAD_REQUEST, problemDetail, exception);
    }
}
