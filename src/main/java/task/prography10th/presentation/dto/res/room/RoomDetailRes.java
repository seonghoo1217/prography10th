package task.prography10th.presentation.dto.res.room;

import task.prography10th.domain.room.Room;
import task.prography10th.domain.room.RoomStatus;
import task.prography10th.domain.room.RoomType;

import java.time.LocalDateTime;

public record RoomDetailRes(int id, String title, int hostId, RoomType roomType, RoomStatus roomStatus,
                            LocalDateTime createAt,
                            LocalDateTime updateAt) {
    public RoomDetailRes(Room room) {
        this(
                room.getId(),
                room.getTitle(),
                room.getHost().getId(),
                room.getRoomDetails().getRoomType(),
                room.getRoomDetails().getRoomStatus(),
                room.getCreateAt(),
                room.getUpdateAt());
    }
}
