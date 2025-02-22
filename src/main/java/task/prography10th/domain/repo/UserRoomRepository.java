package task.prography10th.domain.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.UserTeam;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRoomRepository extends Repository<UserRoom, Integer> {
    void deleteAllInBatch();

    @Modifying
    @Query("DELETE FROM UserRoom ur WHERE ur IN :userRooms")
    void deleteAllInBatch(@Param("userRooms") List<UserRoom> userRooms);

    void delete(UserRoom userRoom);

    boolean existsByUser(User user);

    UserRoom save(UserRoom userRoom);

    Long countByRoomAndUserTeam(Room room, UserTeam userTeam);

    boolean existsByUserAndRoom(User user, Room room);

    List<UserRoom> findByRoom(Room room);

    Optional<UserRoom> findByUserAndRoom(User user, Room room);
}
