package co.istad.dealkh.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Getter
public class CustomAuthException extends ResponseStatusException {
    private final List<Map<String, Object>> errors;

    public CustomAuthException(HttpStatus status, List<Map<String, Object>> errors) {
        super(status, null);
        this.errors = errors;
    }
}
