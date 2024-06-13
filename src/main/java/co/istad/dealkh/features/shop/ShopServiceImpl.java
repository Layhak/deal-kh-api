package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.dto.ShopCreateRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shop.dto.ShopUpdateRequest;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ShopMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.validator.category.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopTypeRepository shopTypeRepository;
    private final UserRepository userRepository;
    private final ShopMapper shopMapper;
    private final RoleRepository roleRepository;

    @Override
    public PageResponse<ShopResponse> getAllShop(int page, int size, String field, String order) {
        if (page < 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page and size must be greater than 0");
        }

        List<String> validFields = Arrays.asList("name", "email");

        if (field == null || field.isEmpty() || !validFields.contains(field)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field must be name or email");
        }

        if (order != null && !order.equals("asc") && !order.equals("desc")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order must be asc or desc");
        }

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ShopResponse> shops = shopRepository.findAll(pageable).map(shopMapper::toShopResponse);

        return new PageResponse<>(shops);
    }

    @Override
    public ShopResponse getShopById(String slug) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse createShop(ShopCreateRequest shopRequest) {
        if (shopRepository.existsByEmail(shopRequest.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        if (shopRepository.existsByPhoneNumber(shopRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        }

        Shop shop = shopMapper.toShop(shopRequest);

        List<User> users = shopRequest.usernames().stream()
                .map(username -> userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .toList();

        shop.setUsers(users);

        if (shopRepository.existsByName(shop.getName())) {
            shop.setSlug(String.format("%s-%s", SlugFormatter.formatSlug(shopRequest.name()), shop.getAddress()));
        } else {
            shop.setSlug(SlugFormatter.formatSlug(shopRequest.name()));
        }
        shop.setIsDeleted(false);
        shop.setIsDisabled(false);

        Shop savedShop = shopRepository.save(shop);

        users.stream().filter(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("BUYER"))).forEach(user -> {
            user.getRoles()
                    .add(roleRepository.findByName("SELLER")
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found")));
            userRepository.save(user);
        });

        return shopMapper.toShopResponse(savedShop);
    }

    @Override
    public ShopResponse updateShop(String slug, ShopUpdateRequest shopRequest) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        shopMapper.mapUpdateShopToShop(shop, shopRequest);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public void deleteShop(String slug) {
        Shop shop = shopRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        shopRepository.delete(shop);
    }

    @Override
    public ShopResponse disableShop(String slug) {
        Shop shop = shopRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        shop.setIsDisabled(true);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public ShopResponse enableShop(String slug) {
        Shop shop = shopRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        shop.setIsDisabled(false);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public List<ShopResponse> getShopByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<Shop> shops = user.getShops();
        return shops.stream().map(shopMapper::toShopResponse).toList();
    }

    @Override
    public List<ShopResponse> getShopByShopType(String shopType) {
        ShopType shopType1 = shopTypeRepository.findByName(shopType)
                .orElseThrow(() -> new RuntimeException("Shop type not found"));
        List<Shop> shops = shopRepository.findByShopType(shopType1);
        return shops.stream().map(shopMapper::toShopResponse).toList();
    }

    @Override
    public List<ShopResponse> getNearbyShop(double latitude, double longitude) {
        double radiusInKilometers = 1;
        final int R = 6371; // Radius of the earth in km

        List<Shop> allShops = shopRepository.findAll();
        List<Shop> nearbyShops = new ArrayList<>();

        for (Shop shop : allShops) {
            String shopLocation = shop.getLocation();
            String[] latLng = shopLocation.split(",");
            double shopLatitude = Double.parseDouble(latLng[0]);
            double shopLongitude = Double.parseDouble(latLng[1]);
            double latDistance = Math.toRadians(shopLatitude - latitude);
            double lonDistance = Math.toRadians(shopLongitude - longitude);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(shopLatitude))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c; // convert to kilometers

            if (distance <= radiusInKilometers) {
                nearbyShops.add(shop);
            }
        }
        List<ShopResponse> nearbyShopResponses = new ArrayList<>();
        for (Shop shop : nearbyShops) {
            nearbyShopResponses.add(shopMapper.toShopResponse(shop));
        }
        return nearbyShopResponses;
    }

    @Override
    public List<ShopResponse> getShopByName(String name) {
        List<Shop> shops = shopRepository.findByName(name);
        return shops.stream().map(shopMapper::toShopResponse).toList();
    }
}
