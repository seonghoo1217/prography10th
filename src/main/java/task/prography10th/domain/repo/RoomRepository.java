package task.prography10th.domain.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import task.prography10th.domain.room.Room;

import java.util.Optional;

public interface RoomRepository extends Repository<Room, Integer> {
    void deleteAllInBatch();

    Room save(Room room);

    Page<Room> findAll(Pageable pageable);

    Optional<Room> findById(Integer id);
}
