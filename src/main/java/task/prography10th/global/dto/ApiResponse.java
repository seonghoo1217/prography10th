package task.prography10th.global.dto;

public record ApiResponse<T>(Integer code, String message, T result) {

    public static <T> ApiResponse<T> of (Integer code, String message, T result){
        return new ApiResponse<>(code, message, result);
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
