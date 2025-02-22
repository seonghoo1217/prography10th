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
    @Operation(summary = "Team 정보 수정")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "유저 ID 또는 방 ID가 없음"),
    })
    public ApiResponse<?> changeTeam(@PathVariable Integer roomId, @RequestBody ModifyTeamReq modifyTeamReq) {
        roomCommandService.changeTeam(roomId, modifyTeamReq.userId());

        return ApiResponse.success(null);
    }
}
