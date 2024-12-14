package tenkacheva.work.app.dtos;

import tenkacheva.work.app.models.TaskStatus;
import tenkacheva.work.app.models.User;

import java.time.Instant;
import java.util.Set;

public record TaskDTO(
        String id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt,
        TaskStatus status,
        User author,
        User assignee,
        Set<User> observers) {
}
