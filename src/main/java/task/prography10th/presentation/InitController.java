package task.prography10th.presentation;

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
    public ApiResponse<?> initDatabase(@RequestBody InitReq initReq) {
        initCommandService.initializeDatabase(initReq);

        return ApiResponse.success();
    }
}
