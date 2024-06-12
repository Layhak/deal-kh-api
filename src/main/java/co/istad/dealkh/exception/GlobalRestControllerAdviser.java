package co.istad.dealkh.exception;

import co.istad.dealkh.base.BasedError;
import co.istad.dealkh.base.BasedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalRestControllerAdviser {

    @ExceptionHandler(CustomAuthException.class)
    public ResponseEntity<BasedErrorResponse<List<Map<String, Object>>>> handleCustomAuthException(CustomAuthException ex) {
        BasedError<List<Map<String, Object>>> basedError = BasedError.<List<Map<String, Object>>>builder()
                .code(ex.getStatusCode().toString())
                .description(ex.getErrors())
                .build();

        BasedErrorResponse<List<Map<String, Object>>> errorResponse = BasedErrorResponse.<List<Map<String, Object>>>builder()
                .error(basedError)
                .build();

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleServiceErrors(ResponseStatusException ex) {

        BasedError<String> basedError = new BasedError<>();
        basedError.setCode(ex.getStatusCode().toString());
        basedError.setDescription(ex.getReason());

        BasedErrorResponse<String> basedErrorResponse = new BasedErrorResponse();
        basedErrorResponse.setError(basedError);

        return ResponseEntity.status(ex.getStatusCode())
                .body(basedErrorResponse);
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BasedErrorResponse<String> handleBadRequestException(Exception ex) {
        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .description(ex.getMessage())
                .build();

        return BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();
    }
}
