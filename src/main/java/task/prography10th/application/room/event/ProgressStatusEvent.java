package task.prography10th.application.room.event;

import task.prography10th.domain.room.RoomStatus;

public record ProgressStatusEvent(int roomId, RoomStatus roomStatus) {
}
