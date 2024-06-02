package co.istad.dealkh.features.product.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.file.FileService;
import co.istad.dealkh.features.file.dto.FileResponse;
import co.istad.dealkh.features.product.ProductService;
import co.istad.dealkh.features.product.dto.ProductCreateRequest;
import co.istad.dealkh.features.product.dto.ProductResponseDetail;
import co.istad.dealkh.features.product.dto.ProductUpdateRequest;
import co.istad.dealkh.paging.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;

    @PostMapping("")
    BaseResponse<ProductResponseDetail> createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        return BaseResponse.<ProductResponseDetail>createSuccess("Successfully created product!")
                .setPayload(productService.createProduct(productCreateRequest));
    }

    @GetMapping("/{id}")
    BaseResponse<Optional<ProductResponseDetail>> getProductById(@PathVariable Long id) {
        return BaseResponse.<Optional<ProductResponseDetail>>ok("Successfully retrieved product!")
                .setPayload(productService.getProductById(id));
    }

    @GetMapping("")
    PageResponse<ProductResponseDetail> filterProduct(@RequestParam Map<String, String> params) {
        return productService.filterProduct(params);

    }

    @PutMapping("/{id}")
    BaseResponse<ProductResponseDetail> updateProductById(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest) {
        return BaseResponse.<ProductResponseDetail>updateSuccess("Update product successfully!")
                .setPayload(productService.updateProductById(id, productUpdateRequest));
    }

    @DeleteMapping("/{id}")
    BaseResponse<?> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return BaseResponse.deleteSuccess("Delete product successfully!");
    }

    @PostMapping(value = "/{id}/upload/multiple", consumes = "multipart/form-data")
    @Operation(summary = "Upload multiple files product")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<ProductResponseDetail> uploadProductImage(
            @PathVariable Long id,
            @RequestPart("files") MultipartFile[] files,
            @RequestParam("description") List<String> descriptions,
            HttpServletRequest request
    ) {

        if(!productService.existsById(id)) {
            return BaseResponse.<ProductResponseDetail>notFound(String.format("Product with id %d not found!", id));
        }

        List<FileResponse> fileResponses = fileService.uploadMultipleFiles(files, request);

        List<String> imageUrls = fileResponses.stream().map(FileResponse::fullUrl).toList();

        List<String> descriptionsList = descriptions.stream().toList();

        ProductResponseDetail productResponseDetail = productService.updateProductImage(id, imageUrls, descriptionsList);

        return BaseResponse.<ProductResponseDetail>createSuccess("Successfully uploaded product images!")
                .setPayload(productResponseDetail);
    }
}
