package co.istad.dealkh.validator.page;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

public class ValidatePagination {
    public static void validatePageAndSize(int page, int size, String field, String order) {
        if (page < 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page and size must be greater than 0");
        }

        List<String> validFields = Arrays.asList("name", "email");

        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be name or email");
        }

        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }
    }
}
