package task.prography10th.global.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import task.prography10th.global.dto.ApiResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    private final Map<Class<? extends Exception>, ErrorCode> exceptionMap;

    public GlobalExceptionHandler() {
        Map<Class<? extends Exception>, ErrorCode> tempMap = new HashMap<>();
        for (ErrorCode errorCode : ErrorCode.values()) {
            Set<Class<? extends Exception>> exceptions = errorCode.getExceptions();
            for (Class<? extends Exception> exception : exceptions) {
                tempMap.put(exception, errorCode);
            }
        }
        this.exceptionMap = Collections.unmodifiableMap(tempMap);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        if (exceptionMap.containsKey(e.getClass())) {
            ErrorCode errorCode = exceptionMap.get(e.getClass());
            Object data = parseErrorData(e);

            if (errorCode.getStatus().value() == 201) {
                return ResponseEntity.status(errorCode.getStatus())
                        .body(ApiResponse.badRequest(data));
            }
            
            return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.error(data));
        }

        log.error("Unexpected error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(null));
    }

    private Object parseErrorData(Exception e) {
        return switch (e.getClass().getSimpleName()) {
            case "MethodArgumentNotValidException" ->
                    dataForMethodArgumentNotValidException((MethodArgumentNotValidException) e);
            case "ConstraintViolationException" ->
                    dataForConstraintViolationException((ConstraintViolationException) e);
            default -> null;
        };
    }

    private Object dataForMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .toList();
    }

    private Object dataForConstraintViolationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream()
                .map(violation -> new FieldError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
    }

    private record FieldError(String field, String message) {
    }
}

