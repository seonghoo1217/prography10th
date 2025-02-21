package task.prography10th.domain.repo;

import org.springframework.data.repository.Repository;
import task.prography10th.domain.room.Room;

public interface RoomRepository extends Repository<Room, Integer> {
    void deleteAllInBatch();
}
