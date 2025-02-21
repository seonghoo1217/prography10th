package task.prography10th.presentation.dto.req;

import jakarta.validation.constraints.PositiveOrZero;

public record PageReq(@PositiveOrZero int size, @PositiveOrZero int page) {
}
