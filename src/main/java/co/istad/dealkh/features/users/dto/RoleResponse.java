package co.istad.dealkh.features.users.dto;

import co.istad.dealkh.domain.Authority;

import java.util.Set;

public record RoleResponse(
        String name,
        Set<Authority> authorities
) {
}
