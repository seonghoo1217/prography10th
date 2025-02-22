package task.prography10th.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.prography10th.application.room.RoomCommandService;
import task.prography10th.application.room.RoomQueryService;
import task.prography10th.domain.room.Room;
import task.prography10th.global.dto.ApiResponse;
import task.prography10th.presentation.dto.req.PageReq;
import task.prography10th.presentation.dto.req.room.CreateRoomReq;
import task.prography10th.presentation.dto.req.room.GameStartReq;
import task.prography10th.presentation.dto.req.room.JoinRoomReq;
import task.prography10th.presentation.dto.req.room.LeaveRoomReq;
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

    @GetMapping("/{roomId}")
    public ApiResponse<?> findRoomById(@PathVariable Integer roomId) {
        Room room = roomQueryService.findRoomById(roomId);

        RoomDetailRes roomDetailRes = new RoomDetailRes(room);

        return ApiResponse.success(roomDetailRes);
    }

    @PostMapping("/attention/{roomId}")
    public ApiResponse<?> attentionRoom(@PathVariable Integer roomId, @RequestBody JoinRoomReq joinRoomReq) {
        Integer userRoomId = roomCommandService.joinRoom(roomId, joinRoomReq.userId());

        if (userRoomId == null) {
            return ApiResponse.error(null);
        }

        return ApiResponse.success(null);
    }

    @PostMapping("/out/{roomId}")
    public ApiResponse<?> leaveRoom(@PathVariable Integer roomId, @RequestBody LeaveRoomReq leaveRoomReq) {
        roomCommandService.leaveRoom(roomId, leaveRoomReq.userId());

        return ApiResponse.success(null);
    }

    @PostMapping("/start/{roomId}")
    public ApiResponse<?> startGamePingPong(@PathVariable Integer roomId, @RequestBody GameStartReq gameStartReq) {
        roomCommandService.gameStart(roomId, gameStartReq.userId());

        return ApiResponse.success(null);
    }
}
