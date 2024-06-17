package co.istad.dealkh.paging;

import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {
    private List<?> list;
    private PaginationResponse pagination;

    public PageResponse(Page<?> page) {
        this.list = page.getContent();
        this.pagination = PaginationResponse.builder()
                .empty(page.isEmpty())
                .first(page.isFirst())
                .last(page.isLast())
                .pageSize(page.getPageable().getPageSize())
                .pageNumber(page.getPageable().getPageNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .build();
    }

    public PageResponse(List<WishListResponse> wishListResponses, int number, int size, long totalElements, int totalPages, boolean b, boolean b1) {

        this.list = wishListResponses;
        this.pagination = PaginationResponse.builder()
                .empty(false)
                .first(b)
                .last(b1)
                .pageSize(size)
                .pageNumber(number)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .numberOfElements(wishListResponses.size())
                .build();
    }
}
