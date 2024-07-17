package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.domain.enumType.ShopVerify;
import co.istad.dealkh.features.category.CategoryRepository;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.features.productfeedback.ProductFeedbackRepository;
import co.istad.dealkh.features.productrating.ProductRatingRepository;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.features.wishlist.WishListRepository;
import co.istad.dealkh.mapper.ProductMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.specification.filter.PageFilter;
import co.istad.dealkh.specification.filter.ProductFilter;
import co.istad.dealkh.specification.filter.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ProductServiceImpl is a service implementation of {@link ProductService} that handles product-related operations.
 * It includes methods for creating, retrieving, updating, and deleting products.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;
    private final ProductRatingRepository productRatingRepository;
    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ProductFeedbackRepository productFeedbackRepository;

    /**
     * Creates a new product based on the provided request.
     *
     * @param productCreateRequest the request containing the details for the new product
     * @return
     */
    @Override
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {

        Product newProduct = productMapper.mapProductRequestToProduct(productCreateRequest);

        Shop shop = shopRepository.findBySlug(productCreateRequest.shopSlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with name %s not found! ", productCreateRequest.shopSlug())));

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        Discount discount = discountRepository.findByUuid(productCreateRequest.discountUuid())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Discount with uuid %s not found! ", productCreateRequest.discountUuid())
                ));

        if (discountRepository.findByUuid(productCreateRequest.discountUuid()).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You have not create this discount yet!");
        }

        Category category = categoryRepository.findBySlug(productCreateRequest.categorySlug())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Category with slug %s not found! ", productCreateRequest.categorySlug())));


        newProduct.setDiscount(discount);
        newProduct.setCategory(category);
        newProduct.setShop(shop);

        productRepository.save(newProduct);


        return productMapper.mapProductToProductResponseDetail(newProduct);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param slug
     * @return
     */
    @Override
    public Optional<ProductResponse> getProductBySlug(String slug) {

        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with slug %s not found! ", slug)
                ));

        ProductResponse productResponseDetail = productMapper.mapProductToProductResponseDetail(product);

        return Optional.of(productResponseDetail);
    }

    /**
     * Retrieves all products.
     *
     * @param page
     * @param size
     * @param field
     * @param order
     * @param params
     * @return
     */
    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size, String field, String order, Map<String, String> params) {
        ProductFilter productFilter = new ProductFilter();
        if (params.containsKey("name")) {
            String name = params.get("name");
            productFilter.setName(name);
        }

        if (params.containsKey("discountValue")) {
            String discountValue = params.get("discountValue");
            productFilter.setDiscountValue(Double.parseDouble(discountValue));
        }

        if (params.containsKey("discountType")) {
            String discountType = params.get("discountType");
            productFilter.setDiscountType(discountType);
        }

        if (params.containsKey("discountTypeSlug")) {
            String discountTypeSlug = params.get("discountTypeSlug");
            productFilter.setDiscountType(discountTypeSlug);
        }

        if (params.containsKey("categorySlug")) {
            String category = params.get("categorySlug");
            productFilter.setCategorySlug(category);
        }

        if (params.containsKey("shop")) {
            String shop = params.get("shop");
            productFilter.setShop(shop);
        }

        if (params.containsKey("ratingAvg")) {
            String ratingAvg = params.get("ratingAvg");
            productFilter.setRatingAvg(Double.parseDouble(ratingAvg));
        }

        List<String> validFields = List.of("name", "price", "ratingAvg", "discountPrice", "shop", "discountValue", "category", "createdAt", "updatedAt", "createdBy", "updateBy");

        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be name, price, discountPrice, shop, discountValue, category, createdAt, updatedAt, createdBy, updateBy, ratingAvg");
        }

        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }

        size = PageFilter.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageFilter.PAGE_LIMIT)) {
            size = Integer.parseInt(params.get(PageFilter.PAGE_LIMIT));
        }

        page = PageFilter.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageFilter.PAGE_NUMBER)) {
            page = Integer.parseInt(params.get(PageFilter.PAGE_NUMBER));
        }

        ProductSpecification specification = new ProductSpecification(productFilter);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<ProductResponse> products = productRepository.findAll(specification, pageable).map(productMapper::mapProductToProductResponseDetail);
        return new PageResponse<>(products);
    }


    /**
     * Updates a product based on its ID.
     *
     * @param slug
     * @param productUpdateRequest
     * @return
     */
    @Override
    public ProductResponse updateProductBySlug(String username, String slug, ProductUpdateRequest productUpdateRequest) {

        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product with slug %s not found!", slug)));

        boolean isUserAssociatedWithShop = product.getShop().getUsers().stream()
                .anyMatch(user -> user.getUsername().equals(username));
        if (!isUserAssociatedWithShop) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        // Set update metadata
        product.setUpdatedAt(LocalDateTime.now());
        product.setUpdatedBy(username);

        // Map basic fields using the mapper
        productMapper.mapProductToUpdateRequest(product, productUpdateRequest);

        // Fetch and set the category if categorySlug is provided
        if (productUpdateRequest.categorySlug() != null) {
            Category category = categoryRepository.findBySlug(productUpdateRequest.categorySlug())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
            product.setCategory(category);
        }
        if (productUpdateRequest.discountUuid() != null) {
            Discount discount = discountRepository.findByUuid(productUpdateRequest.discountUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount not found"));
            product.setDiscount(discount);
        }
        productRepository.save(product);

        return productMapper.mapProductToProductResponseDetail(product);
    }

    /**
     * Deletes a product based on its ID.
     *
     * @param slug
     */
    @Override
    @Transactional
    public void deleteProduct(String username, String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Product with slug %s not found! ", slug)));

        boolean isUserAssociatedWithShop = product.getShop().getUsers().stream()
                .anyMatch(user -> user.getUsername().equals(username));
        if (!isUserAssociatedWithShop) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You're not this resource owner!");
        }

        // Remove wishlists that reference this product
        List<WishList> wishLists = wishListRepository.findByProduct(product);
        wishListRepository.deleteAll(wishLists);

        // Remove product ratings that reference this product
        List<ProductRating> productRatings = productRatingRepository.findByProduct(product);
        productRatingRepository.deleteAll(productRatings);

        // Remove product feedbacks that reference this product
        List<ProductFeedback> productFeedbacks = productFeedbackRepository.findByProduct(product);
        productFeedbackRepository.deleteAll(productFeedbacks);

        productRepository.delete(product);
    }

    /**
     * Retrieves a product rating average based on its ID.
     *
     * @param id
     * @return
     */
    @Override
    public Double getProductRatingAvg(Long id) {
        Double totalRating = productRatingRepository.findRatingValueByProductId(id);
        Long ratingCount = productRatingRepository.countByProductId(id);

        if (ratingCount == 0) {
            return 0.0;
        }
        return totalRating / ratingCount;
    }

    @Override
    public PageResponse<ProductResponse> getProductShopOwner(int page, int size, String field, String order, String slug) {

        // Here is validate pagination
        if (page < 0) {
            page = PageFilter.DEFAULT_PAGE_NUMBER;
        }
        if (size < 0) {
            size = PageFilter.DEFAULT_PAGE_LIMIT;
        }

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        Page<ProductResponse> productResponses = productRepository.findAllProductByShopSlug(slug, pageable).map(productMapper::mapProductToProductResponseDetail);
        return new PageResponse<>(productResponses);
    }

    @Override
    public PageResponse<ProductResponse> getAllProductByShopOwner(String username, int page, int size, String field, String order, Map<String, String> params) {

        // Initialize filter parameters
        String name = params.get("name");
        Double discountValue = params.containsKey("discountValue") ? Double.parseDouble(params.get("discountValue")) : null;
        String discountType = params.get("discountType");
        String category = params.get("category");
        String shop = params.get("shop");

        List<String> validFields = List.of("name", "price", "discountPrice", "description", "shop", "discountValue", "category", "createdAt", "updatedAt", "createdBy", "updateBy");
        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be one of: name, price, discountPrice, description, shop, discountValue, category, createdAt, updatedAt, createdBy, updateBy");
        }

        size = params.containsKey(PageFilter.PAGE_LIMIT) ? Integer.parseInt(params.get(PageFilter.PAGE_LIMIT)) : PageFilter.DEFAULT_PAGE_LIMIT;
        page = params.containsKey(PageFilter.PAGE_NUMBER) ? Integer.parseInt(params.get(PageFilter.PAGE_NUMBER)) : PageFilter.DEFAULT_PAGE_NUMBER;

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));

        // Fetch products by user control over the shop
        Page<Product> productsPage = productRepository.findAllByUserControl(username, name, discountValue, discountType, category, shop, pageable);
        List<ProductResponse> productResponses = productsPage.getContent().stream()
                .map(productMapper::mapProductToProductResponseDetail)
                .collect(Collectors.toList());

        return new PageResponse<>(new PageImpl<>(productResponses, pageable, productsPage.getTotalElements()));
    }



    @Override
    public Long getTotalRatingsBySlug(String productSlug) {
        return productRatingRepository.countByProductSlug(productSlug);
    }

}
