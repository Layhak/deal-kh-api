package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ShopMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
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
    public ShopResponse getShopById(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse createShop(ShopRequest shopRequest) {
        if (shopRepository.existsByName(shopRequest.name())) {
            throw new RuntimeException("Shop already exists");
        }
        if (shopRepository.existsByEmail(shopRequest.email())) {
            throw new RuntimeException("Email already exists");
        }

        Shop shop = shopMapper.toShop(shopRequest);

        List<User> users = shopRequest.userIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .toList();
        shop.setUsers(users);

        ShopType shopType = shopTypeRepository.findById(shopRequest.shopTypeId())
                .orElseThrow(() -> new RuntimeException("Shop type not found"));
        shop.setShopType(shopType);

        shop.setIsDeleted(false);
        shop.setIsDisabled(false);

        Shop savedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(savedShop);
    }


    @Override
    public ShopResponse updateShop(Long id, ShopRequest shopRequest) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        shop.setName(shopRequest.name());
        shop.setAddress(shopRequest.address());
        shop.setDescription(shopRequest.description());
        shop.setPhoneNumber(shopRequest.phoneNumber());
        shop.setEmail(shopRequest.email());
        shop.setOpenAt(shopRequest.openAt());
        shop.setCloseAt(shopRequest.closeAt());
//        shop.setImages(shopRequest.images());
        shop.setLocation(shopRequest.location());
        List<User> users = shopRequest.userIds().stream().map(userId -> userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"))).toList();
        shop.setUsers(users);
        ShopType shopType = shopTypeRepository.findById(shopRequest.shopTypeId())
                .orElseThrow(() -> new RuntimeException("Shop type not found"));
        shop.setShopType(shopType);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public void deleteShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        shopRepository.delete(shop);
    }

    @Override
    public ShopResponse disableShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        shop.setIsDisabled(true);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public ShopResponse enableShop(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found"));
        shop.setIsDisabled(false);
        Shop updatedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(updatedShop);
    }

    @Override
    public List<ShopResponse> getShopByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
        double radiusInKilometers = 0.5;
        final int R = 6371; // Radius of the earth in km

        List<Shop> allShops = shopRepository.findAll();
        List<Shop> nearbyShops = new ArrayList<>();

        for (Shop shop : allShops) {
            String shopAddress = shop.getAddress();
            String[] latLng = shopAddress.split(",");
            double shopLatitude = Double.parseDouble(latLng[0]);
            double shopLongitude = Double.parseDouble(latLng[1]);
            double latDistance = Math.toRadians(shopLatitude - latitude);
            double lonDistance = Math.toRadians(shopLongitude - longitude);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(shopLatitude))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c; // convert to kilometers

            System.out.println("Shop: " + shop.getName() + ", Distance: " + distance + " km");

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
