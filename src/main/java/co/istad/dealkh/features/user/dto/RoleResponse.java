package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.entity.Authority;

import java.util.Set;

public record RoleResponse(
        String name,
        Set<Authority> authorities
) {
}
