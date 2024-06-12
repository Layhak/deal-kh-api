package co.istad.dealkh.base;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

/**
 * BaseResponse is a generic class used to standardize API responses. It includes a payload,
 * a message, and a status code. This class provides a fluent API for setting its properties
 * and includes static factory methods for creating responses with common HTTP status codes.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Data} from Lombok to generate getter, setter, toString, equals, and hashCode methods.</li>
 * <li>{@link Accessors} from Lombok to enable fluent setters with chain=true.</li>
 * </ul>
 * </p>
 *
 * @param <T> the type of the payload
 */
@Accessors(chain = true)
@Data
public class BaseResponse<T> {
    /**
     * The payload of the response.
     */
    private T payload;

    /**
     * The message of the response.
     */
    private String message;

    /**
     * The HTTP status code of the response.
     */
    private int status;

    /**
     * Creates a successful response with a CREATED status (201).
     *
     * @param message the message to be included in the response
     * @param <T>     the type of the payload
     * @return a BaseResponse object with the status set to 201 (CREATED) and the given message
     */
    public static <T> BaseResponse<T> createSuccess(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.CREATED.value()).setMessage(message);
    }

    /**
     * Creates a successful response with an OK status (200).
     *
     * @param message the message to be included in the response
     * @param <T>     the type of the payload
     * @return a BaseResponse object with the status set to 200 (OK) and the given message
     */
    public static <T> BaseResponse<T> ok(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.OK.value()).setMessage(message);
    }

    /**
     * Creates a response indicating that a resource was not found (404).
     *
     * @param message the message to be included in the response
     * @param <T>     the type of the payload
     * @return a BaseResponse object with the status set to 404 (NOT FOUND) and the given message
     */
    public static <T> BaseResponse<T> notFound(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.NOT_FOUND.value()).setMessage(message);
    }

    /**
     * Creates a response indicating a bad request (400).
     *
     * @param message the message to be included in the response
     * @param <T>     the type of the payload
     * @return a BaseResponse object with the status set to 400 (BAD REQUEST) and the given message
     */
    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.BAD_REQUEST.value()).setMessage(message);
    }

    public static BaseResponse<?> internalServerError(String message) {
        return new BaseResponse<>().setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(message);
    }
}
