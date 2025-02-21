package task.prography10th.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.prography10th.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("health")
public class HealthCheckController {

    @GetMapping
    public ApiResponse<?> healthCheck() {
        return ApiResponse.success();
    }
}
