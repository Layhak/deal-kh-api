package co.istad.dealkh.features.product;

import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponse;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest productCreateRequest);

    Optional<ProductResponse> getProductById(Long id);

    PageResponse<ProductResponse> filterProduct(Map<String, String> params);

    ProductResponse updateProductById(Long id, ProductUpdateRequest productUpdateRequest);

    void deleteProduct(Long id);

}
