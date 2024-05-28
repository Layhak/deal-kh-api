package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.Category;
import co.istad.dealkh.domain.Discount;
import co.istad.dealkh.domain.Product;
import co.istad.dealkh.features.category.CategoryRepository;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponseDetail;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.mapper.ProductMapper;
import co.istad.dealkh.paging.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ProductResponseDetail createProduct(ProductCreateRequest productCreateRequest) {

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

        newProduct.setDiscount(discount);
        newProduct.setCategory(category);
        productRepository.save(newProduct);

        return productMapper.mapProductToProductResponseDetail(newProduct);
    }

    @Override
    public Optional<ProductResponseDetail> getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Product with id %d not found! ", id)
                ));

        ProductResponseDetail productResponseDetail = productMapper.mapProductToProductResponseDetail(product);

        return Optional.of(productResponseDetail);
    }


    @Override
    public PageResponse<ProductResponseDetail> filterProduct(Map<String, String> params) {


        return null;
    }

    @Override
    public ProductResponseDetail updateProductById(Long id, ProductUpdateRequest productUpdateRequest) {
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
}
