package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.User;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.dto.*;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.mapper.ShopMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.validator.formatter.SlugFormatter;
import co.istad.dealkh.validator.page.ValidatePagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        ValidatePagination.validatePageAndSize(page, size, field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ShopResponse> shops = shopRepository.findAll(pageable).map(shopMapper::toShopResponse);

        return new PageResponse<>(shops);
    }

    @Override
    public PageResponse<ShopResponse> getAllOwnerShop(int page, int size, String field, String order, String username) {
        ValidatePagination.validatePageAndSize(page, size, field, order);
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

        if (shopRepository.existsByEmail(shopRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already exists. Please use another email address.");
        }

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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Slug is already taken!");
            }
            shop.setSlug(slug);
        } else {
            // If the slug is not provided, generate a random slug
            String randomSlug = UUID.randomUUID().toString();
            shop.setSlug(randomSlug);
        }

        shop.setProfile(shopRequest.profile());
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

        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);
        shopMapper.mapUpdateShopToShop(shop, shopRequest);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }

    @Override
    public void deleteShop(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
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

        shop.setUpdatedBy(username);
        shop.setUpdatedAt(LocalDateTime.now());
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

        shop.setUpdatedBy(username);
        shop.setUpdatedAt(LocalDateTime.now());
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
    public ShopResponse addOwnerToShop(String slug, String username, String owner) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop with slug %s not found! ", slug)));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with username %s not found! ", username)));
        shop.getUsers().stream().filter(usr -> usr.getUsername().equals(owner)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this shop"));

        // Check if the user is already a member of the shop
        if (shop.getUsers().stream().anyMatch(usr -> usr.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are already a member of this shop");
        }

        //if that user don't have the seller role yet then add it
        if (user.getRoles().stream().noneMatch(role -> role.getName().equals("SELLER"))) {
            user.getRoles().add(roleRepository.findByName("SELLER")
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!")));
            userRepository.save(user);
        }

        shop.setUpdatedBy(username);
        shop.setUpdatedAt(LocalDateTime.now());
        shop.getUsers().add(user);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);

    }

    @Override
    public ShopResponse removeOwnerFromShop(String slug, String username, String owner) {

        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Shop with slug %s not found! ", slug)));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with username %s not found! ", username)));
        shop.getUsers().stream().filter(usr -> usr.getUsername().equals(owner)).findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this shop"));
        //owner can't remove himself from the shop
        if (username.equals(owner)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't remove yourself from the shop");
        }
        if (shop.getUsers().stream().noneMatch(usr -> usr.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this shop");
        }

        shop.setUpdatedBy(username);
        shop.setUpdatedAt(LocalDateTime.now());
        shop.getUsers().remove(user);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }


    @Override
    public ShopCoverResponse getAllShopCover(String slug) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "cover not found!"));
        return shopMapper.mapToShopCoverResponse(shop);
    }

    @Override
    public void deleteShopCover(String username, String slug, ShopCoverRequest shopCoverRequest) {

        userRepository.findAllByShopsSlug(slug)
                .stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "You are not the owner of this shop"));

        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "shop not found!"));

        // Check if the cover exists
        boolean coverExists = shop.getCovers().stream()
                .anyMatch(img -> img.getUrl().equals(shopCoverRequest.cover()));

        // If the cover does not exist, throw a BAD_REQUEST exception
        if (!coverExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover not found!");
        }

        // Filter out the cover to be deleted
        List<Image> filteredImages = shop.getCovers().stream()
                .filter(img -> !img.getUrl().equals(shopCoverRequest.cover()))
                .collect(Collectors.toList());

        // Set the filtered covers back to the user
        shop.setCovers(filteredImages);

        // Save the updated user
        shopRepository.save(shop);

    }

    @Override
    public ShopCoverResponse uploadShopCover(String username, String slug, ShopCoverRequest shopCoverRequest) {

        userRepository.findAllByShopsSlug(slug)
                .stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "You are not the owner of this shop"));


        Shop shop = shopRepository.findBySlugAndCreatedBy(slug, username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "shop not found!"));

        List<Image> existingImages = shop.getCovers();

        if (shop.getCovers().isEmpty()) {
            shop.setCovers(new ArrayList<>());
        }
        if (shopCoverRequest.cover().isEmpty() || shopCoverRequest.cover().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover is required");
        }
        Image newImage = new Image(shopCoverRequest.cover());

        existingImages.add(newImage);
        shop.setCovers(existingImages);
        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);

        shopRepository.save(shop);

        return shopMapper.mapToShopCoverResponse(shop);
    }

    @Override
    public ShopProfileResponse getShopProfile(String slug) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found!"));
        return shopMapper.mapToShopProfileResponse(shop);
    }

    @Override
    public void deleteShopProfile(String username, String slug, String profile) {

        userRepository.findAllByShopsSlug(slug)
                .stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "You are not the owner of this shop"));

        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found!"));

        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);
        shop.setProfile(null);
        shopRepository.save(shop);
    }

    @Override
    public ShopProfileResponse uploadShopProfile(String username, String slug, ShopProfileRequest shopProfileRequest) {

        userRepository.findAllByShopsSlug(slug)
                .stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "You are not the owner of this shop"));

        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found!"));


        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);
        shop.setProfile(shopProfileRequest.profile());
        shopRepository.save(shop);
        return shopMapper.mapToShopProfileResponse(shop);
    }
}
