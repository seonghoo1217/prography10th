package task.prography10th.domain.repo;

import org.springframework.data.repository.Repository;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.UserTeam;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.user.User;

public interface UserRoomRepository extends Repository<UserRoom, Integer> {
    void deleteAllInBatch();

    boolean existsByUser(User user);

    UserRoom save(UserRoom userRoom);

    Long countByRoomAndTeam(Room room, UserTeam userTeam);
}
