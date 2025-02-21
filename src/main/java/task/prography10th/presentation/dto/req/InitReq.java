package task.prography10th.presentation.dto.req;

import jakarta.validation.constraints.Positive;

public record InitReq(@Positive int seed, @Positive int quantity) {
}
