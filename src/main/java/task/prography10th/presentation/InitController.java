package task.prography10th.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.prography10th.application.init.InitCommandService;
import task.prography10th.global.dto.ApiResponse;
import task.prography10th.presentation.dto.req.InitReq;

@RestController
@RequiredArgsConstructor
@RequestMapping("init")
public class InitController {

    private final InitCommandService initCommandService;

    @PostMapping
    @Operation(
            summary = "초기화 API",
            description = "몇 명의 더미데이터를 생성할 것인지에 대한 인자값을 통해 더미 유저생성"
    )
    public ApiResponse<?> initDatabase(@RequestBody InitReq initReq) {
        initCommandService.initializeDatabase(initReq);

        return ApiResponse.success(null);
    }
}
