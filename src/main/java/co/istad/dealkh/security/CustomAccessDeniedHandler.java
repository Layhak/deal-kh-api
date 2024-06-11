package co.istad.dealkh.security;

import co.istad.dealkh.base.BasedError;
import co.istad.dealkh.base.BasedErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Throwable cause = accessDeniedException.getCause();
        if (cause instanceof ResponseStatusException) {
            // If the cause is ResponseStatusException, rethrow it
            throw (ResponseStatusException) cause;
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");

        BasedError<String> basedError = BasedError.<String>builder()
                .code(HttpStatus.FORBIDDEN.toString())
                .description("You do not have permission to access this resource")
                .build();

        BasedErrorResponse<String> basedErrorResponse = BasedErrorResponse.<String>builder()
                .error(basedError)
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(basedErrorResponse));
    }
}
