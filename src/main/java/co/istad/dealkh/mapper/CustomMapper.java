package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final RoleRepository roleRepository;

    @Named("stringToRole")
    public Role mapRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }

    @Named("userToString")
    public List<String> mapUser(List<User> users) {
        return users.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    @Named("shopTypeToString")
    public String mapShopType(ShopType shopType) {
        return shopType.getName();
    }
}