package co.istad.dealkh.exception;

import co.istad.dealkh.base.BasedError;
import co.istad.dealkh.base.BasedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestControllerAdviser {

    @ExceptionHandler(SingleRoleException.class)
    public ResponseEntity<BasedErrorResponse<String>> handleSingleRoleException(SingleRoleException ex) {
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                .description(ex.getReason())
                .build();

        BasedErrorResponse<String> basedErrorResponse = BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();

        return new ResponseEntity<>(basedErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BasedErrorResponse<List<Map<String, Object>>>> handleCustomException(CustomException ex) {
        BasedError<List<Map<String, Object>>> basedError = BasedError.<List<Map<String, Object>>>builder()
                .code(ex.getStatusCode().toString())
                .description(ex.getErrors())
                .build();

        BasedErrorResponse<List<Map<String, Object>>> basedErrorResponse = BasedErrorResponse.<List<Map<String, Object>>>builder()
                .error(basedError)
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(basedErrorResponse);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<BasedErrorResponse<String>> handleUnauthorizedException(HttpClientErrorException.Unauthorized ex) {
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.UNAUTHORIZED.toString())
                .description(ex.getResponseBodyAsString())
                .build();

        BasedErrorResponse<String> basedErrorResponse = BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();

        return new ResponseEntity<>(basedErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<BasedErrorResponse<String>> handleForbiddenException(HttpClientErrorException.Forbidden ex) {
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.FORBIDDEN.toString())
                .description(ex.getResponseBodyAsString())
                .build();

        BasedErrorResponse<String> basedErrorResponse = BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();

        return new ResponseEntity<>(basedErrorResponse, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasedErrorResponse<List<Map<String, Object>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<Map<String, Object>> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            Map<String, Object> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("reason", fieldError.getDefaultMessage());
            errors.add(error);
        });

        BasedError<List<Map<String, Object>>> basedError = BasedError.<List<Map<String, Object>>>builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .description(errors)
                .build();

        return BasedErrorResponse.<List<Map<String, Object>>>builder()
                .error(basedError)
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasedErrorResponse<String> handleNullPointerException() {
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .description("Field cannot be null")
                .build();

        return BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BasedErrorResponse<String> handleAllExceptions() {
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .description("An unexpected error occurred: you might input the wrong field/value or please checking you syntax.")
                .build();

        return BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();
    }
}
