package task.prography10th.presentation.dto.res.room;

import java.util.List;

public record RoomPageRes(long totalElements, int totalPages, List<RoomPageDetailRes> roomList) {
}
