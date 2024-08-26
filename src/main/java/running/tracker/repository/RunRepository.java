package running.tracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import running.tracker.model.Run;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RunRepository extends MongoRepository<Run, String> {

    Optional<Run> findByUserIdAndFinishDateTimeIsNull(String userId);

    List<Run> findAllByUserIdAndStartDateTimeBetween(
            String userId,
            LocalDateTime fromDatetime,
            LocalDateTime toDatetime
    );

    List<Run> findAllByUserId(String userId);
}