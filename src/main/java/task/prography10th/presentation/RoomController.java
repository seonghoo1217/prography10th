package task.prography10th.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.prography10th.application.room.RoomCommandService;
import task.prography10th.application.room.RoomQueryService;
import task.prography10th.global.dto.ApiResponse;
import task.prography10th.presentation.dto.req.PageReq;
import task.prography10th.presentation.dto.req.room.CreateRoomReq;
import task.prography10th.presentation.dto.res.room.RoomDetailRes;
import task.prography10th.presentation.dto.res.room.RoomPageRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("room")
public class RoomController {
    private final RoomCommandService roomCommandService;
    private final RoomQueryService roomQueryService;

    @PostMapping
    public ApiResponse<?> createRoom(@RequestBody CreateRoomReq createRoomReq) {
        Integer roomId = roomCommandService.createRoom(createRoomReq);

        if (roomId == null) {
            return ApiResponse.error(null);
        }

        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<?> findAllRoomByPage(@Valid PageReq pageReq) {
        RoomPageRes roomPageRes = roomQueryService.findAllRoomByPage(pageReq.size(), pageReq.page());

        return ApiResponse.success(roomPageRes);
    }

    @GetMapping("/{id}")
    public ApiResponse<?> findRoomById(@PathVariable Integer id) {
        RoomDetailRes roomDetailRes = roomQueryService.findRoomById(id);

        return ApiResponse.success(roomDetailRes);
    }
}
