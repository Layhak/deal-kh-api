package co.istad.dealkh.feature.shoptype.dto;

import lombok.Builder;
import lombok.Getter;


public record ShopTypeRequest(
        String name,
        String icon
) {

}

