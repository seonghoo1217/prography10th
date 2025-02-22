package task.prography10th.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Room(게임 방) API", description = "방 생성, 조회, 참가, 나가기 및 게임 시작 관련 API")
public class RoomController {
    private final RoomCommandService roomCommandService;
    private final RoomQueryService roomQueryService;

    @PostMapping
    @Operation(
            summary = "방 생성 API",
            description = "새로운 게임방 생성을 위해 user의 Id, RoomType(단식, 복식), 제목을 요청값으로 전달합니다. " +
                    "생성에 성공하면 성공 메시지를 반환하고, 요청값이 잘못된경우 201, 서버 에러시 500을 반환합니다"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "방 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "유저가 활성상태가 아니거나, 이미 참여한 방이 있습니다.")
    })
    public ApiResponse<?> createRoom(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "방 생성을 위해 userId, RoomType(SINGLE or DOUBLE), title이 필요",
                    required = true
            )
            @RequestBody CreateRoomReq createRoomReq) {
        Integer roomId = roomCommandService.createRoom(createRoomReq);

        if (roomId == null) {
            return ApiResponse.error(null);
        }

        return ApiResponse.success(null);
    }

    @GetMapping
    @Operation(
            summary = "방 목록 조회 API",
            description = "페이지 번호와 크기를 기반으로 방 목록을 페이징 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "방 목록 조회 성공")
    })
    public ApiResponse<?> findAllRoomByPage(
            @io.swagger.v3.oas.annotations.Parameter(
                    description = "페이지 요청 정보 (PageReq: page, size) 음수 값 전달시 500 에러 전달",
                    required = true
            )
            @Valid PageReq pageReq) {
        RoomPageRes roomPageRes = roomQueryService.findAllRoomByPage(pageReq.size(), pageReq.page());

        return ApiResponse.success(roomPageRes);
    }

    @GetMapping("/{roomId}")
    @Operation(
            summary = "방 상세 조회 API",
            description = "방 ID를 기반으로 해당 방의 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "방 상세 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "존재하지 않는 방 ID")
    })
    public ApiResponse<?> findRoomById(@PathVariable Integer roomId) {
        Room room = roomQueryService.findRoomById(roomId);

        RoomDetailRes roomDetailRes = new RoomDetailRes(room);

        return ApiResponse.success(roomDetailRes);
    }

    @PostMapping("/attention/{roomId}")
    @Operation(
            summary = "방 참가 API",
            description = "요청 본문에 포함된 JoinRoomReq의 userId를 기반으로 해당 방에 참가합니다. " +
                    "참가가 성공하면 해당 참여 정보의 ID를 반환하며, 실패 시 error 응답을 반환합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "방 참가 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "방 참가 실패 (유저 활성화 X, 유저가 다른방 현재 참여중, 방이 대기(WAIT)상태가아님, 정원 꽉참)")
    })
    public ApiResponse<?> attentionRoom(
            @io.swagger.v3.oas.annotations.Parameter(
                    description = "참가할 방의 ID",
                    required = true
            )
            @PathVariable Integer roomId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "방 참가에 필요한 정보가 담긴 객체 (JoinRoomReq: userId)",
                    required = true
            )
            @RequestBody JoinRoomReq joinRoomReq) {
        Integer userRoomId = roomCommandService.joinRoom(roomId, joinRoomReq.userId());

        if (userRoomId == null) {
            return ApiResponse.error(null);
        }

        return ApiResponse.success(null);
    }

    @PostMapping("/out/{roomId}")
    @Operation(
            summary = "방 나가기 API",
            description = "요청 본문에 포함된 LeaveRoomReq의 userId를 기반으로 해당 방에서 나갑니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "방 나가기 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "방 나가기 실패 (유저가 해당 방의 참가자가 아님, 이미 게임 시작 or 종료 상태, 존재하지 않는 id)")
    })
    public ApiResponse<?> leaveRoom(
            @io.swagger.v3.oas.annotations.Parameter(
                    description = "나갈 방의 ID",
                    required = true
            )
            @PathVariable Integer roomId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "방 나가기 요청 정보가 담긴 객체 (LeaveRoomReq: userId)",
                    required = true
            )
            @RequestBody LeaveRoomReq leaveRoomReq) {
        roomCommandService.leaveRoom(roomId, leaveRoomReq.userId());

        return ApiResponse.success(null);
    }


    @PostMapping("/start/{roomId}")
    @Operation(
            summary = "게임 시작 API",
            description = "해당 방의 호스트에 의해 게임 시작을 요청합니다. " +
                    "게임 시작 조건이 충족되면 방의 상태를 PROGRESS로 변경하고, 후속 처리를 진행합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "게임 시작 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "게임 시작 실패 (유저가 호스트가 아님, 방이 대기상태가 아님, 정원이 미달)")
    })
    public ApiResponse<?> startGamePingPong(@PathVariable Integer roomId, @RequestBody GameStartReq gameStartReq) {
        roomCommandService.gameStart(roomId, gameStartReq.userId());

        return ApiResponse.success(null);
    }
}
