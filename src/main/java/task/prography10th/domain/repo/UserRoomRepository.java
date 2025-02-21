package task.prography10th.domain.repo;

import org.springframework.data.repository.Repository;
import task.prography10th.domain.UserRoom;

public interface UserRoomRepository extends Repository<UserRoom, Integer> {
    void deleteAllInBatch();
}
