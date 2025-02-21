package task.prography10th.application.room;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.prography10th.application.UserRoomQueryService;
import task.prography10th.application.user.UserQueryService;
import task.prography10th.domain.repo.RoomRepository;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.room.RoomStatus;
import task.prography10th.domain.user.User;
import task.prography10th.global.exception.BadAPIRequestException;
import task.prography10th.presentation.dto.req.room.CreateRoomReq;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomCommandService {

    private final RoomRepository roomRepository;

    private final UserQueryService userQueryService;

    private final UserRoomQueryService userRoomQueryService;

    public Integer createRoom(CreateRoomReq createRoomReq) {
        User user = userQueryService.findUserById(createRoomReq.userId());

        if (!user.isActive()) {
            throw new BadAPIRequestException();
        }

        if (userRoomQueryService.existsParticipant(user)) {
            throw new BadAPIRequestException();
        }
        Room room = new Room(createRoomReq.title(), user, RoomStatus.WAIT, createRoomReq.roomType());
        roomRepository.save(room);

        return room.getId();
    }
}
