package task.prography10th.presentation.dto.res;

import java.util.List;

public record UserPageRes(long totalElements, int totalPages, List<UserPageDetailRes> userList) {
}
