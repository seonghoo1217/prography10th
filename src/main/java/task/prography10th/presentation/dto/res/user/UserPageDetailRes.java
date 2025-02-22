package task.prography10th.presentation.dto.res.user;

import task.prography10th.domain.user.UserStatus;

import java.time.LocalDateTime;

public record UserPageDetailRes(Integer id, Integer fakerId, String name, String email, UserStatus userStatus,
                                LocalDateTime createAt,
                                LocalDateTime updateAt) {

}
