package co.istad.dealkh.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface Pagination {
    int DEFAULT_PAGE_LIMIT = 5;
    int DEFAULT_PAGE_NUMBER = 1;
    Sort DEFAULT_SORT = Sort.by(Sort.Order.asc("id"));

    static Pageable getPageable(int pageNumber, int pageSize, Sort sort) {
        if (pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if (pageSize <= 1) {
            pageSize = DEFAULT_PAGE_LIMIT;
        }
        if (sort == null) {
            sort = DEFAULT_SORT;
        }
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }
}
