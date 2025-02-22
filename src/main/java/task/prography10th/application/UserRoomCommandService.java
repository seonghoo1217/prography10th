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
        UserTeam assignedTeam = allocationTeam(room);
        UserRoom userRoom = new UserRoom(room, user, assignedTeam);

        return userRoomRepository.save(userRoom).getId();
    }

    private UserTeam allocationTeam(Room room) {
        int maxPerTeam = room.getRoomDetails().getRoomType().getCapacity() / 2;

        long redTeamCount = userRoomRepository.countByRoomAndUserTeam(room, UserTeam.RED);
        long blueTeamCount = userRoomRepository.countByRoomAndUserTeam(room, UserTeam.BLUE);

        UserTeam assignedTeam;

        if (redTeamCount >= maxPerTeam) {
            assignedTeam = UserTeam.BLUE;
        } else if (blueTeamCount >= maxPerTeam) {
            assignedTeam = UserTeam.RED;
        } else {
            assignedTeam = UserTeam.RED;
        }

        return assignedTeam;
    }
}
