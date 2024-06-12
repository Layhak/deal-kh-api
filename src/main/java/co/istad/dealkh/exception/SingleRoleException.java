package co.istad.dealkh.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SingleRoleException extends ResponseStatusException {
    public SingleRoleException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }
}
