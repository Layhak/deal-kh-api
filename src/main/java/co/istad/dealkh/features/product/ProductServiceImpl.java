package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.features.category.CategoryRepository;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.features.productrating.ProductRatingRepository;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.mapper.ProductMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.specification.filter.PageFilter;
import co.istad.dealkh.specification.filter.ProductFilter;
import co.istad.dealkh.specification.filter.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;
    private final ProductRatingRepository productRatingRepository;


    @Override
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {

        Product newProduct = productMapper.mapProductRequestToProduct(productCreateRequest);

        Discount discount = discountRepository.findById(productCreateRequest.discountId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Discount with id %d not found! ", productCreateRequest.discountId())));

        Category category = categoryRepository.findById(productCreateRequest.categoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Category with id %d not found! ", productCreateRequest.categoryId())
                ));

        Shop shop = shopRepository.findById(productCreateRequest.shopId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with id %d not found! ", productCreateRequest.shopId())
                ));

        newProduct.setDiscount(discount);
        newProduct.setCategory(category);
        newProduct.setShop(shop);
//        newProduct.setRatingAvg(getProductRatingAvg(newProduct.getId()));
        productRepository.save(newProduct);

        return productMapper.mapProductToProductResponseDetail(newProduct);
    }

    @Override
    public Optional<ProductResponse> getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with id %d not found! ", id)
                ));
        product.setRatingAvg(getProductRatingAvg(id));
        ProductResponse productResponseDetail = productMapper.mapProductToProductResponseDetail(product);

        return Optional.of(productResponseDetail);
    }


    @Override
    public PageResponse<ProductResponse> filterProduct(Map<String, String> params) {

        ProductFilter productFilter = new ProductFilter();
        if (params.containsKey("name")) {
            String name = params.get("name");
            productFilter.setName(name);
        }

        if (params.containsKey("discountPercentage")) {
            String discountPercentage = params.get("discountPercentage");
            productFilter.setDiscountPercentage(Double.parseDouble(discountPercentage));
        }
        if (params.containsKey("category")) {
            String category = params.get("category");
            productFilter.setCategory(category);
        }

        int pageLimit = PageFilter.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageFilter.PAGE_LIMIT)) {
            pageLimit = Integer.parseInt(params.get(PageFilter.PAGE_LIMIT));
        }

        int pageNumber = PageFilter.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageFilter.PAGE_NUMBER)) {
            pageNumber = Integer.parseInt(params.get(PageFilter.PAGE_NUMBER));
        }

        ProductSpecification specification = new ProductSpecification(productFilter);

        Pageable pageable = PageFilter.getPageable(pageNumber, pageLimit);

        Page<ProductResponse> page = productRepository.findAll(specification, pageable)
                .map(productMapper::mapProductToProductResponseDetail);
        return new PageResponse<>(page);
    }

    @Override
    public ProductResponse updateProductById(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with id %d not found! ", id)
                ));

        product.setUpdatedAt(LocalDateTime.now());

        productMapper.mapProductToUpdateRequest(product, productUpdateRequest);

        productRepository.save(product);

        return productMapper.mapProductToProductResponseDetail(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with id %d not found! ", id)
                ));

        productRepository.delete(product);
    }
    public Double getProductRatingAvg(Long id) {
        Double totalRating = productRatingRepository.findRatingByProductId(id);
        Long ratingCount = productRatingRepository.countByProductId(id);

        if (ratingCount == 0) {
            return 0.0; // or throw an exception if you prefer
        }

        return totalRating / ratingCount;
    }

}
