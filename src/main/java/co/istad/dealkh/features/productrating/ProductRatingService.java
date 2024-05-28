package co.istad.dealkh.features.productrating;

import co.istad.dealkh.domain.ProductRating;
import co.istad.dealkh.features.productrating.dto.ProductRatingRequest;
import co.istad.dealkh.features.productrating.dto.ProductRatingResponse;

import java.util.List;

public interface ProductRatingService {

    ProductRatingResponse rateProduct(ProductRatingRequest productRatingRequest);

    List<ProductRatingResponse> getAllProductRating();
}
