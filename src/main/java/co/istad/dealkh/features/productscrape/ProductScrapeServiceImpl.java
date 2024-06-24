package co.istad.dealkh.features.productscrape;

import co.istad.dealkh.domain.ProductScrape;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import co.istad.dealkh.mapper.ProductScrapeMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.validator.page.ValidatePagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductScrapeServiceImpl implements ProductScrapeService {
    private final ProductScrapeRepository productScrapeRepository;
    private final ProductScrapeMapper productScrapeMapper;

    @Override
    public PageResponse<ProductScrapeResponse> getProductScrapes(int page, int size, String field, String order) {

        // Here is validate pagination
        ValidatePagination.validatePageAndSize(page, size, field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ProductScrapeResponse> productScrapeResponses = productScrapeRepository.findAll(pageable).map(productScrapeMapper::toProductScrapeResponse);

        return new PageResponse<>(productScrapeResponses);
    }


    @Override
    public ProductScrapeResponse postProductScrape(ProductScrapeRequest productScrapeRequest) {
        ProductScrape product = new ProductScrape();
        product.setName(productScrapeRequest.name());
        product.setDescription(productScrapeRequest.description());
        product.setPrice(productScrapeRequest.price());
        product.setImage(productScrapeRequest.image());
        product.setDiscountPercentage(productScrapeRequest.discountPercentage());
        product.setRating(productScrapeRequest.rating());
        product.setUrl(productScrapeRequest.url());
        productScrapeRepository.save(product);
        return productScrapeMapper.toProductScrapeResponse(product);
    }
}
