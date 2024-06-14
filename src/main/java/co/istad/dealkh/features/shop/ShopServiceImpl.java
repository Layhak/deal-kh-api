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

    private void validatePageAndSize(int page, int size, String field, String order) {
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
    }

    @Override
    public PageResponse<ShopResponse> getAllShop(int page, int size, String field, String order) {
        validatePageAndSize(page, size, field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ShopResponse> shops = shopRepository.findAll(pageable).map(shopMapper::toShopResponse);

        return new PageResponse<>(shops);
    }

    @Override
    public PageResponse<ShopResponse> getAllOwnerShop(int page, int size, String field, String order, String username) {
        validatePageAndSize(page, size, field, order);
        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Page<Shop> shops = shopRepository.findByUsersContains(user, pageable);
        return new PageResponse<>(shops.map(shopMapper::toShopResponse));
    }

    @Override
    public ShopResponse getOwnerShopBySlug(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!shop.getUsers().contains(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this shop");
        }
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse getShopBySlug(String slug) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse createShop(ShopCreateRequest shopRequest, List<String> usernames) {
//        if (shopRepository.existsByEmail(shopRequest.email())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
//        }
//
        if (shopRepository.existsByPhoneNumber(shopRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Phone number %s already exists",
                            shopRequest.phoneNumber()));
        }

        Shop shop = shopMapper.toShop(shopRequest);

        List<User> users = usernames.stream()
                .map(username -> userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                .toList();

        shop.setUsers(users);
        // Generate a unique slug for the shop
        // If the slug already exists, append a unique suffix to the slug
        if (shopRequest.slug() != null && !shopRequest.slug().isEmpty() && !shopRequest.slug().isBlank()) {
            String slug = SlugFormatter.formatSlug(shopRequest.slug());
            if (shopRepository.existsBySlug(slug)) {
                slug = String.format("%s-%s", SlugFormatter.formatSlug(shopRequest.slug()), shop.getAddress());
            }
            shop.setSlug(slug);
        } else {
            // If the slug is not provided, generate a random slug
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
    public ShopResponse updateShop(String slug, ShopUpdateRequest shopRequest, String username) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        if (shop.getUsers().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this shop");
        }
        shopMapper.mapUpdateShopToShop(shop, shopRequest);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public void deleteShop(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        if (shop.getUsers().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this shop");
        }
        shopRepository.delete(shop);
    }

    @Override
    public ShopResponse disableShop(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        if (shop.getUsers().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to disable this shop");
        }
        shop.setIsDisabled(true);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public ShopResponse enableShop(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        if (shop.getUsers().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to enable this shop");
        }
        shop.setIsDisabled(false);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public List<ShopResponse> getShopByShopType(String shopType) {
        ShopType shopType1 = shopTypeRepository.findByName(shopType)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop type not found"));
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
    public boolean isShopOwner(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return shop.getUsers().contains(user);
    }


}
