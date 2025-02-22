package task.prography10th.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import task.prography10th.application.room.RoomCommandService;
import task.prography10th.global.dto.ApiResponse;
import task.prography10th.presentation.dto.req.ModifyTeamReq;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final RoomCommandService roomCommandService;

    @PutMapping("/{roomId}")
    @Operation(
            summary = "팀 정보 수정 API",
            description = "해당 방에 속한 유저의 팀 정보를 수정합니다. \n\n" +
                    "요청 경로의 roomId는 팀 정보를 수정할 방의 ID를 의미하며, 요청 본문에는 " +
                    "수정할 대상 유저의 userId가 포함됩니다. \n\n" +
                    "팀 변경이 불가능한 상황(예: 변경하려는 팀의 인원이 이미 최대치인 경우, " +
                    "또는 해당 방이나 유저가 존재하지 않는 경우)에는 201 응답을 반환합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "팀 정보 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "유저 ID 또는 방 ID가 존재하지 않거나, 팀 변경이 불가능한 상태")
    })
    public ApiResponse<?> changeTeam(
            @PathVariable Integer roomId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "팀 수정 요청 데이터로, 수정 대상 유저의 userId를 포함합니다.",
                    required = true
            )
            @RequestBody ModifyTeamReq modifyTeamReq) {

        roomCommandService.changeTeam(roomId, modifyTeamReq.userId());

        return ApiResponse.success(null);
    }
}
