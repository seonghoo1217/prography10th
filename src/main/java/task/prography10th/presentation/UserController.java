package task.prography10th.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.prography10th.application.user.UserQueryService;
import task.prography10th.global.dto.ApiResponse;
import task.prography10th.presentation.dto.req.PageReq;
import task.prography10th.presentation.dto.res.user.UserPageRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserQueryService userQueryService;

    @GetMapping
    @Operation(summary = "유저 전체 정보 조회 Page")
    public ApiResponse<?> findAllUserByPage(@Parameter(name = "size, page", description = "페이징처리를 위한 size, page값")
                                            @Valid PageReq pageReq) {
        UserPageRes userPageRes = userQueryService.findAllUserByPage(pageReq);

        return ApiResponse.success(userPageRes);
    }
}
