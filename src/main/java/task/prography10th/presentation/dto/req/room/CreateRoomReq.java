package task.prography10th.presentation.dto.req.room;

import task.prography10th.domain.room.RoomType;

public record CreateRoomReq(Integer userId, RoomType roomType, String title) {
    
}
