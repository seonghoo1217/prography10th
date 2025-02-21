package task.prography10th.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.prography10th.application.room.RoomCommandService;
import task.prography10th.global.dto.ApiResponse;
import task.prography10th.presentation.dto.req.room.CreateRoomReq;

@RestController
@RequiredArgsConstructor
@RequestMapping("room")
public class RoomController {
    private final RoomCommandService roomCommandService;

    @PostMapping
    public ApiResponse<?> createRoom(@RequestBody CreateRoomReq createRoomReq) {
        Integer roomId = roomCommandService.createRoom(createRoomReq);

        if (roomId == null) {
            return ApiResponse.error(null);
        }

        return ApiResponse.success(null);
    }
}
