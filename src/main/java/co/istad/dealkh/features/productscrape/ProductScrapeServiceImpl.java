package co.istad.dealkh.features.productscrape;

import co.istad.dealkh.domain.ProductScrape;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeRequest;
import co.istad.dealkh.features.productscrape.dto.ProductScrapeResponse;
import co.istad.dealkh.mapper.ProductScrapeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductScrapeServiceImpl implements ProductScrapeService{
    private final ProductScrapeRepository productScrapeRepository;
    private final ProductScrapeMapper productScrapeMapper;
    @Override
    public List<ProductScrapeResponse> getProductScrapes() {
        return productScrapeRepository.findAll().stream()
                .map(productScrapeMapper::toProductScrapeResponse)
                .toList();
    }



    @Override
    public ProductScrapeResponse postProductScrape(ProductScrapeRequest productScrapeRequest) {
        ProductScrape product = new ProductScrape();
        product.setName(productScrapeRequest.name());
        product.setDescription(productScrapeRequest.description());
        product.setPrice(productScrapeRequest.price());
        product.setImage(productScrapeRequest.image());
        product.setDiscountPercentage(productScrapeRequest.discountPercentage());
        product.setRating(productScrapeRequest.rating()) ;
        product.setUrl(productScrapeRequest.url());
        productScrapeRepository.save(product);
        return productScrapeMapper.toProductScrapeResponse(product);
    }
}
