package co.istad.dealkh.base;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Accessors(chain = true)
@Data
public class BaseResponse<T> {
    private T payload;
    private String message;
    private int status;

    public static <T> BaseResponse<T> createSuccess(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.CREATED.value()).setMessage(message);
    }

    public static <T> BaseResponse<T> ok(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.OK.value()).setMessage(message);
    }

    public static <T> BaseResponse<T> notFound(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.NOT_FOUND.value()).setMessage(message);
    }


    public static <T> BaseResponse<T> updateSuccess() {
        return new BaseResponse<T>().setStatus(HttpStatus.OK.value()).setMessage("Successfully update the entry!");
    }

    public static <T> BaseResponse<T> deleteSuccess(String message) {
        return new BaseResponse<T>().setStatus(HttpStatus.OK.value()).setMessage(message);
    }

    public static BaseResponse badRequest(String message) {
        return new BaseResponse().setStatus(HttpStatus.BAD_REQUEST.value()).setMessage(message);
    }
}
