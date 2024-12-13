package tenkacheva.work.app.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tenkacheva.work.app.models.Task;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
