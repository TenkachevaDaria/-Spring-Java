package tenkacheva.work.app.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Document
public class Task {

    @Id
    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds;

    @ReadOnlyProperty
    private User author;

    @ReadOnlyProperty
    private User assignee;

    @ReadOnlyProperty
    private Set<User> observers;

    public Task() {

    }

    public Task(String id, String name, String description, Instant createdAt, Instant updatedAt, TaskStatus status, String authorId, String assigneeId, Set<String> observerIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.authorId = authorId;
        this.assigneeId = assigneeId;
        this.observerIds = observerIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Task setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Set<String> getObserverIds() {
        return observerIds;
    }

    public void setObserverIds(Set<String> observerIds) {
        this.observerIds = observerIds;
    }

    public User getAuthor() {
        return author;
    }

    public User getAssignee() {
        return assignee;
    }

    public Set<User> getObservers() {
        return observers;
    }

    public Task setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Task setAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public Task setObservers(Set<User> observers) {
        this.observers = observers;
        return this;
    }
}
