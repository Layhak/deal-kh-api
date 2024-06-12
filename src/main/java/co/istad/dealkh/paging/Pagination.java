package co.istad.dealkh.paging;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface Pagination {
    int page_limit = 25;
    int page_number = 1;
    Sort DEFAULT_SORT = Sort.by(Sort.Order.asc("id"));

    static Pageable getPageable(int pageNumber, int pageSize, Sort sort) {
        if (pageNumber < page_number) {
            pageNumber = page_number;
        }
        if (pageSize < 1) {
            pageSize = page_limit;
        }
        if (sort == null) {
            sort = DEFAULT_SORT;
        }
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }
}
