package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.json.Image;

import java.util.List;

public record UserCoverResponse(
        List<Image> covers
) {
}
