package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shop.dto.ShopUpdateRequest;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ShopMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.validator.category.NameFormatter;
import co.istad.dealkh.validator.category.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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

//        List<Shop> shops = shopRepository.findAll();
//        return shops.stream().map(shopMapper::toShopResponse).toList();
    }

    @Override
    public ShopResponse getShopByName(String name) {

        // Check format name request
        String nameRequest = SlugFormatter.formatSlug(name);

        Shop shop = shopRepository.findBySlug(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop with name %s not found! ", nameRequest)));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse createShop(ShopRequest shopRequest) {

        String slug = "";

        // Handle null, empty, or blank slug
        if (shopRequest.slug() == null || shopRequest.slug().isEmpty() || shopRequest.slug().isBlank()) {
            slug = SlugFormatter.formatSlug(shopRequest.name() + " - " + shopRequest.address());
        } else {
            slug = shopRequest.slug();
        }

        if (shopRepository.existsBySlug(shopRequest.slug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Slug %s is taken", shopRequest.slug()));
        }

        if (shopRepository.existsByName(shopRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Name %s already exists", shopRequest.name()));
        }

        if (shopRepository.existsByPhoneNumber(shopRequest.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Phone number %s already exists", shopRequest.phoneNumber()));
        }

        if (shopRepository.existsByEmail(shopRequest.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Email %s already exists", shopRequest.email()));
        }

        Shop shop = shopMapper.toShop(shopRequest);
        shop.setSlug(slug);

        List<User> users = shopRequest.userIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("User with id %d not found! ", userId)
                        ))
                )
                .toList();
        shop.setUsers(users);

        ShopType shopType = shopTypeRepository.findById(shopRequest.shopTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ShopType with id %d not found! ", shopRequest.shopTypeId())));
        shop.setShopType(shopType);

        shop.setIsDeleted(false);
        shop.setIsDisabled(false);



        users.stream().filter(user -> user.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("BUYER")))
                .forEach(user -> {
                    user.getRoles().add(roleRepository.findByName("SELLER")
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role with name %s not found! ", "SELLER"))));

            userRepository.save(user);
        });
        shopRepository.save(shop);

        return shopMapper.toShopResponse(shop);
    }


    @Override
    public ShopResponse updateShop(String name, ShopUpdateRequest shopUpdateRequest) {

        // Check format name request
        String nameRequest = SlugFormatter.formatSlug(name);

        Shop shop = shopRepository.findBySlug(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop with name %s not found! ", nameRequest)));

        shop.setUpdatedAt(LocalDateTime.now());

        shopMapper.mapShopToUpdateRequest(shop, shopUpdateRequest);

        shopRepository.save(shop);

        List<User> users = shopUpdateRequest.userIds().stream().map(userId -> userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %d not found! ", userId))
                ))
                .toList();

        shop.setUsers(users);

        ShopType shopType = shopTypeRepository.findById(shopUpdateRequest.shopTypeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("ShopType with id %d not found! ", shopUpdateRequest.shopTypeId())));

        shop.setShopType(shopType);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public void deleteShop(String name) {

        String nameRequest = SlugFormatter.formatSlug(name);

        Shop shop = shopRepository.findBySlug(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with name %s not found! ", nameRequest)));

        shopRepository.delete(shop);
    }

    @Override
    public ShopResponse disableShop(String name) {

        String nameRequest = SlugFormatter.formatSlug(name);

        Shop shop = shopRepository.findBySlug(nameRequest)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with name %s not found! ", nameRequest)));

        shop.setIsDisabled(true);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public ShopResponse enableShop(String name) {

        Shop shop = shopRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Shop with name %s not found! ", name)));

        shop.setIsDisabled(false);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public List<ShopResponse> getShopByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id %d not found! ", userId)));

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
        double radiusInKilometers =1;
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
    public List<ShopResponse> getAllShopByName(String name) {
        List<Shop> shops = shopRepository.findAllByName(name);
        return shops.stream().map(shopMapper::toShopResponse).toList();
    }
}
