package task.prography10th.presentation.dto.res.room;

import task.prography10th.domain.room.RoomStatus;
import task.prography10th.domain.room.RoomType;

public record RoomPageDetailRes(Integer id, String title, Integer hostId, RoomType roomType, RoomStatus roomStatus) {
}
