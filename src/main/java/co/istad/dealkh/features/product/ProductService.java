package co.istad.dealkh.features.product;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponseDetail;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

public interface ProductService {

    ProductResponseDetail createProduct(ProductCreateRequest productCreateRequest);

    Optional<ProductResponseDetail> getProductById(Long id);

    PageResponse<ProductResponseDetail> filterProduct(Map<String, String> params);

    ProductResponseDetail updateProductById(Long id, ProductUpdateRequest productUpdateRequest);

    void deleteProduct(Long id);

}
