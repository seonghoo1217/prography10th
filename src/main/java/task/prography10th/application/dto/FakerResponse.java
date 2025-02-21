package task.prography10th.application.dto;

import java.util.List;

public record FakerResponse(String status, int code, int total, List<FakerUser> data) {
}
