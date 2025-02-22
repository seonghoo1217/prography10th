package task.prography10th.application.room;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import task.prography10th.application.UserRoomCommandService;
import task.prography10th.application.UserRoomQueryService;
import task.prography10th.application.room.event.ProgressStatusEvent;
import task.prography10th.application.user.UserQueryService;
import task.prography10th.domain.repo.RoomRepository;
import task.prography10th.domain.room.Room;
import task.prography10th.domain.room.RoomStatus;
import task.prography10th.domain.user.User;
import task.prography10th.global.exception.BadAPIRequestException;
import task.prography10th.presentation.dto.req.room.CreateRoomReq;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomCommandService {

    private final RoomRepository roomRepository;

    private final RoomQueryService roomQueryService;

    private final UserQueryService userQueryService;

    private final UserRoomQueryService userRoomQueryService;

    private final UserRoomCommandService userRoomCommandService;

    private final TransactionTemplate transactionTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final ApplicationEventPublisher eventPublisher;


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


        if (userRoomCommandService.userRoomParticipation(user, room) == null) {
            throw new BadAPIRequestException();
        }

        return room.getId();
    }

    public Integer joinRoom(int roomId, int userId) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        if (!user.isActive()) {
            throw new BadAPIRequestException();
        }

        if (!room.isActive() || !room.isParticipate()) {
            throw new BadAPIRequestException();
        }

        if (userRoomQueryService.existsParticipant(user)) {
            throw new BadAPIRequestException();
        }

        return userRoomCommandService.userRoomParticipation(user, room);
    }

    public void leaveRoom(int roomId, int userId) {
        User user = userQueryService.findUserById(userId);
        Room room = roomQueryService.findRoomById(roomId);

        if (!room.isActive()) {
            throw new BadAPIRequestException();
        }

        if (!userRoomQueryService.isUserParticipantRoom(user, room)) {
            throw new BadAPIRequestException();
        }

        if (!userRoomCommandService.leaveRoom(user, room)) {
            throw new BadAPIRequestException();
        }
    }

    public void gameStart(Integer roomId, Integer userId) {
        Room room = roomQueryService.findRoomById(roomId);

        if (!room.isHost(userId) || !room.isActive() || !room.isReady()) {
            throw new BadAPIRequestException();
        }

        eventPublisher.publishEvent(new ProgressStatusEvent(roomId, RoomStatus.PROGRESS));
    }

    @EventListener
    public void handleProgressStatusEvent(ProgressStatusEvent event) {
        Room room = roomRepository.findById(event.roomId()).orElseThrow(BadAPIRequestException::new);
        room.modifyGameStatus(event.roomStatus());

        if (event.roomStatus() == RoomStatus.PROGRESS) {
            scheduler.schedule(() -> {
                transactionTemplate.execute(status -> {
                    finishRoomStatus(event.roomId());
                    return null;
                });
            }, 1, TimeUnit.MINUTES);
        }
    }

    private void finishRoomStatus(Integer roomId) {
        Room findRoom = roomQueryService.findRoomById(roomId);
        findRoom.modifyGameStatus(RoomStatus.FINISH);
    }
}
