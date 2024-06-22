package co.istad.dealkh.specification.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface PageFilter {
    int DEFAULT_PAGE_LIMIT = 25;
    int DEFAULT_PAGE_NUMBER = 1;
    String PAGE_LIMIT = "size";
    String PAGE_NUMBER = "page";

    static Pageable getPageable(int pageNumber, int pageSize) {
        if (pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_LIMIT;
        }
        return PageRequest.of(pageNumber - 1, pageSize);
    }
}
