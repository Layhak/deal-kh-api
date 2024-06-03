package co.istad.dealkh.features.product.dto;


public record ProductUpdateRequest(

        String name,
        double price,
        String description,
        Long shopId,
        Long discountId,
        Long categoryId

) {
}
