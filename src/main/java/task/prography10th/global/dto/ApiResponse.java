package task.prography10th.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiResponse<T> {
    private final Integer code;
    private final String message;
    private T result;

    public ApiResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static <T> ApiResponse<T> of(Integer code, String message, T result) {
        return new ApiResponse<>(code, message, result);
    }

    private static <T> ApiResponse<T> of(Integer code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <T> ApiResponse<T> success(T result) {
        return of(200, "API 요청이 성공했습니다.", result);
    }

    public static <T> ApiResponse<T> badRequest(T result) {
        return of(201, "불가능한 요청입니다.", result);
    }

    public static <T> ApiResponse<T> error(T result) {
        return of(500, "에러가 발생했습니다.", result);
    }
}