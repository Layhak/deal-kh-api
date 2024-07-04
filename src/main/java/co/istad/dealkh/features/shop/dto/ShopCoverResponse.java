package co.istad.dealkh.features.shop.dto;

import co.istad.dealkh.domain.json.Image;

import java.util.List;

public record ShopCoverResponse(
        List<Image> covers
) {
}
