package task.prography10th.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.UserTeam;
import task.prography10th.domain.repo.UserRoomRepository;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.user.User;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRoomCommandService {

    private final UserRoomRepository userRoomRepository;

    public Integer userRoomParticipation(User user, Room room) {
        UserRoom userRoom = new UserRoom(room, user, UserTeam.RED);

        return userRoomRepository.save(userRoom).getId();
    }
}
