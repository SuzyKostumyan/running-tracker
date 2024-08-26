package running.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import running.tracker.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}