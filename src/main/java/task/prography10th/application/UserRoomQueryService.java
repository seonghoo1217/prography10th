package task.prography10th.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.prography10th.domain.UserRoom;
import task.prography10th.domain.UserTeam;
import task.prography10th.domain.repo.UserRoomRepository;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.user.User;
import task.prography10th.global.exception.BadAPIRequestException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRoomQueryService {

    private final UserRoomRepository userRoomRepository;

    public boolean existsParticipant(User user) {
        return userRoomRepository.existsByUser(user);
    }

    public boolean isUserParticipantRoom(User user, Room room) {
        return userRoomRepository.existsByUserAndRoom(user, room);
    }

    public boolean isTeamChangeAllowed(User user, Room room) {
        int maxPerTeam = room.getRoomDetails().getRoomType().getCapacity() / 2;

        UserTeam targetTeam = getUserOppositeTeam(user, room);

        long currentCount = userRoomRepository.countByRoomAndUserTeam(room, targetTeam);

        return currentCount < maxPerTeam;
    }

    public UserTeam getUserOppositeTeam(User user, Room room) {
        UserRoom userRoom = userRoomRepository.findByUserAndRoom(user, room).orElseThrow(BadAPIRequestException::new);
        return userRoom.getUserTeam() == UserTeam.RED ? UserTeam.BLUE : UserTeam.RED;
    }
}
