package tenkacheva.work.app.dtos;

import tenkacheva.work.app.models.TaskStatus;

import java.util.Set;

public record RequestTaskDTO(String name,
                             String description,
                             TaskStatus status,
                             String authorId,
                             String assigneeId,
                             Set<String> observerIds) {
}
