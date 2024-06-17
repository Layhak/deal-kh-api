package co.istad.dealkh.mapper;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomMapper {
    private final RoleRepository roleRepository;
    private final DiscountTypeRepository discountTypeRepository;
    private final ShopRepository shopRepository;
    private final ShopTypeRepository shopTypeRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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
    public String mapShopTypeSLug(ShopType shopType) {
        return shopType.getName();
    }

    @Named("discountTypeToLong")
    public DiscountType map(Long id) {
        return discountTypeRepository.findById(id).orElse(null);
    }

    @Named("mapShopSlugsToShops")
    public List<Shop> mapShopSlugsToShops(List<String> shopSlugs) {
        return shopSlugs.stream()
                .map(slug -> shopRepository.findBySlug(slug)
                        .orElseThrow(() -> new RuntimeException("Shop not found for slug: " + slug)))
                .collect(Collectors.toList());
    }

    @Named("stringToLocalTime")
    public LocalTime stringToLocalTime(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        return LocalTime.parse(time, TIME_FORMATTER);
    }

    @Named("stringToShopTypeSlug")
    public ShopType stringToShopType(String shopTypeSlug) {
        // Assuming you have a method in ShopTypeRepository to find by name
        return shopTypeRepository.findBySlug(shopTypeSlug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop type slug not found: " + shopTypeSlug));
    }
}