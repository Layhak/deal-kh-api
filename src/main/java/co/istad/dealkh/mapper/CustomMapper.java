package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final RoleRepository roleRepository;
    private final DiscountTypeRepository discountTypeRepository;
    private final ShopRepository shopRepository;

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

    @Named("discountTypeToLong")
    public DiscountType map(Long id) {
        return discountTypeRepository.findById(id).orElse(null);
    }

    @Named("mapShopIdsToShops")
    public List<Shop> mapShopIdsToShops(List<Long> shopIds) {
        return shopIds.stream()
                .map(shopRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}