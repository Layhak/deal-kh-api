package co.istad.dealkh.exception;


import co.istad.dealkh.base.BaseResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class is a global exception handler for the REST API. It catches various types of exceptions that can occur during
 * the execution of the application and returns a custom response to the client.
 * <p>
 * It uses the @RestControllerAdvice annotation, which is a convenience annotation that combines
 *
 * @ControllerAdvice and @ResponseBody.
 * <p>
 * The class defines several methods, each annotated with @ExceptionHandler and a specific exception type. These methods
 * are invoked when an exception to the corresponding type is thrown anywhere within the application.
 * <p>
 * The handleNoSuchElementException method handles NoSuchElementException and returns a response with a status of "Not Found".
 * <p>
 * The handleValidationExceptions method handles MethodArgumentNotValidException, which is thrown when validation on an argument annotated with @Valid fails.
 * <p>
 * The handleResponseStatusException method handles ResponseStatusException and returns the reason and status contained within the exception.
 * <p>
 * The handlePSQLException method handles DataIntegrityViolationException, which is typically thrown when there is a violation of an integrity constraint in the database.
 * <p>
 * The handleAllExceptions method is a generic handler that catches all other exceptions. It returns a response with a status of 500 (Internal Server Error), the type of the exception, and the exception message.
 */
@RestControllerAdvice
public class GlobalRestControllerAdviser {

    /**
     * Handles NoSuchElementException which is thrown when an element which does not exist is being retrieved.
     *
     * @param ex The exception that is caught when NoSuchElementException is thrown.
     * @return A BaseResponse with a status of "Not Found" and a message of "Resource not found".
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<?> handleNoSuchElementException(NoSuchElementException ex) {
        return BaseResponse
                .notFound("Resource not found");
    }

    /**
     * Handles MethodArgumentNotValidException which is thrown when validation on an argument annotated with @Valid fails.
     *
     * @param ex      The exception that is caught when MethodArgumentNotValidException is thrown.
     * @param request The current web request that resulted in this method being called.
     * @return A ResponseEntity with a status of "Bad Request", a list of validation error messages, and an empty payload.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());

        // Get all validation errors
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((x1, x2) -> x1 + ", " + x2)
                .orElse(ex.getMessage());

        body.put("message", errors);
        body.put("payload", new ArrayList<>());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ResponseStatusException which is thrown when a specific HTTP status code is required.
     *
     * @param e The exception that is caught when ResponseStatusException is thrown.
     * @return A ResponseEntity with the status and reason contained within the exception.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e) {
        return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }

    /**
     * Handles DataIntegrityViolationException which is typically thrown when there is a violation of an integrity constraint in the database.
     *
     * @param ex The exception that is caught when DataIntegrityViolationException is thrown.
     * @return A BaseResponse with a status of "Bad Request" and the original error message from the database.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<?> handlePSQLException(DataIntegrityViolationException ex) {
        String errorMessage = ex.getMostSpecificCause().getMessage();
        return BaseResponse.badRequest(errorMessage);
    }

    /**
     * A generic handler that catches all other exceptions.
     *
     * @param ex The exception that is caught.
     * @return A ResponseEntity with a status of 500 (Internal Server Error), the type of the exception, and the exception message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", ex.getClass().getSimpleName());
        body.put("message", ex.getMessage());
        body.put("payload", new ArrayList<>());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
//
}
