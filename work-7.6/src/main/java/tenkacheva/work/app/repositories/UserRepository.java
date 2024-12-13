package tenkacheva.work.app.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tenkacheva.work.app.models.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
