package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.domain.enumType.ShopVerify;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.domain.json.SocialMedia;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.mail.MailService;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.productrating.ProductRatingRepository;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.dto.*;
import co.istad.dealkh.features.shoprating.ShopRatingRepository;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.features.user.VerificationService;
import co.istad.dealkh.mapper.ShopMapper;
import co.istad.dealkh.paging.PageResponse;
import co.istad.dealkh.paging.Pagination;
import co.istad.dealkh.validator.formatter.SlugFormatter;
import co.istad.dealkh.validator.page.ValidatePagination;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;
    private final ProductRatingRepository productRatingRepository;
    private final ShopMapper shopMapper;
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;
    private final VerificationService verificationService;
    private final MailService mailService;
    @Value("${app.frontend.verify-url}")
    private String verifyUrl;
    private final ShopRatingRepository shopRatingRepository;

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

        boolean isApproved = shops.stream().anyMatch(shop -> shop.getIsVerified().equals(ShopVerify.APPROVED));

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        return shopMapper.toShopResponse(shop);
    }

    @Override
    public ShopResponse getShopBySlug(String slug) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        return shopMapper.toShopResponse(shop);
    }


    @Override
    public ShopResponse createShop(ShopCreateRequest shopRequest, List<String> usernames) {

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

        if (shopRepository.existsByEmail(shopRequest.email())) {
            shop.setEmail(shopRequest.email());
        }

        shop.setProfile(shopRequest.profile());
        shop.setIsDeleted(false);
        shop.setIsDisabled(false);
        shop.setIsVerified(ShopVerify.REQUESTING);

        // Send verification email
        mailService.sendEmail(shop.getEmail(), "Waiting for approval", shop.getName(), "requesting");
        Shop savedShop = shopRepository.save(shop);
        return shopMapper.toShopResponse(savedShop);
    }


    @Override
    public ShopResponse updateShop(String slug, ShopUpdateRequest shopRequest, String username) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));
        if (shop.getUsers().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this shop");
        }

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);
        shopMapper.mapUpdateShopToShop(shop, shopRequest);
        shopRepository.save(shop);
        return shopMapper.toShopResponse(shop);
    }


    @Override
    @Transactional
    public void deleteShop(String slug, String username) {
        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));

        // Check if the user has permission to delete the shop
        if (shop.getUsers().stream().noneMatch(user -> user.getUsername().equals(username))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this shop");
        }


        // Fetch related products and delete them
        List<Product> products = productRepository.findByShop(shop);
        for (Product product : products) {
            // Fetch and delete related product ratings
            List<ProductRating> ratings = productRatingRepository.findByProduct(product);
            productRatingRepository.deleteAll(ratings);
            productRepository.delete(product);
        }

        // Fetch related discounts and set their shop reference to null
        List<Discount> discounts = discountRepository.findByShop(shop);
        for (Discount discount : discounts) {
            discount.setShop(null);
            discountRepository.save(discount);
        }

        // Now delete the shop
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

        boolean isApproved = shops.stream().anyMatch(shop -> shop.getIsVerified().equals(ShopVerify.APPROVED));

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        //if that user don't have the seller role yet then add it
        if (user.getRoles().stream().noneMatch(role -> role.getName().equals("SELLER"))) {
            user.getRoles().add(roleRepository.findByName("SELLER")
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!")));
            userRepository.save(user);
        }

        mailService.sendEmail(shop.getEmail(), "Shop owner added", "https://dealkh.istad.co/login", "congrats");

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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
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
        // Ensure the user has permission to modify the shop
        userRepository.findAllByShopsSlug(slug)
                .stream()
                .filter(user -> user.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "You are not the owner of this shop"));

        // Fetch the shop and ensure it exists
        Shop shop = shopRepository.findBySlugAndCreatedBy(slug, username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Shop not found!"));

        // Initialize the covers list if it's null
        List<Image> existingImages = shop.getCovers();
        if (existingImages == null) {
            existingImages = new ArrayList<>();
        }

        // Check if the cover request is valid
        if (shopCoverRequest.cover().isEmpty() || shopCoverRequest.cover().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cover is required");
        }

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        // Create a new image and add it to the covers list
        Image newImage = new Image(shopCoverRequest.cover());
        existingImages.add(newImage);

        // Update the shop entity with the new cover
        shop.setCovers(existingImages);
        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);

        // Save the updated shop entity
        shopRepository.save(shop);

        // Return the response
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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

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

        boolean isApproved = shop.getIsVerified().equals(ShopVerify.APPROVED);

        if (!isApproved) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not approved yet");
        }

        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);
        shop.setProfile(shopProfileRequest.profile());
        shopRepository.save(shop);
        return shopMapper.mapToShopProfileResponse(shop);
    }

    @Override
    public PageResponse<ShopResponse> getAllShopRequest(int page, int size, String field, String order) {

        ValidatePagination.validatePageAndSize(page, size, field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ShopResponse> shops = shopRepository.findAllByIsVerified(ShopVerify.REQUESTING, pageable).map(shopMapper::toShopResponse);

        return new PageResponse<>(shops);
    }

    @Override
    public PageResponse<ShopResponse> getAllShopApproved(int page, int size, String field, String order) {

        ValidatePagination.validatePageAndSize(page, size, field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ShopResponse> shops = shopRepository.findAllByIsVerified(ShopVerify.APPROVED, pageable).map(shopMapper::toShopResponse);

        return new PageResponse<>(shops);
    }

    @Override
    public PageResponse<ShopResponse> getAllShopRejected(int page, int size, String field, String order) {

        ValidatePagination.validatePageAndSize(page, size, field, order);

        Pageable pageable = Pagination.getPageable(page, size, Sort.by(Sort.Direction.fromString(order), field));
        Page<ShopResponse> shops = shopRepository.findAllByIsVerified(ShopVerify.REJECTED, pageable).map(shopMapper::toShopResponse);

        return new PageResponse<>(shops);
    }

    @Override
    public void verifyShop(String slug, String username, Boolean isApproved) {

        String dealkh = "https://dealkh.istad.co/login";

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if the user has the ADMIN or SUPER_ADMIN role
        boolean hasPermission = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN") || role.getName().equals("SUPER_ADMIN"));

        if (!hasPermission) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to verify shops");
        }

        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));

        if (isApproved) {
            shop.setIsVerified(ShopVerify.APPROVED);
            // Add SELLER role to users
            shop.getUsers().forEach(userInShop -> {
                if (userInShop.getRoles().stream().noneMatch(role -> role.getName().equals("SELLER"))) {
                    userInShop.getRoles().add(roleRepository.findByName("SELLER")
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found")));
                    userRepository.save(userInShop);
                }
            });
            mailService.sendEmail(shop.getEmail(), "shop approved", dealkh, "congrats");

        } else {
            shop.setIsVerified(ShopVerify.REJECTED);
            mailService.sendEmail(shop.getEmail(), "Shop Rejected", "Your shop has been rejected", "reject");
        }

        shopRepository.save(shop);
    }

    @Override
    public void uploadSocialMedia(String username, String slug, ShopSocialMediaRequest shopSocialMediaRequest) {

        userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Shop shop = shopRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found"));

        List<SocialMedia> socialMedias = shop.getSocialMedias();

        if (shop.getSocialMedias().isEmpty()) {
            shop.setSocialMedias(new ArrayList<>());
        }
        if (shopSocialMediaRequest.link().isEmpty() || shopSocialMediaRequest.link().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Social media is required");
        }
        SocialMedia newSocialMedia = new SocialMedia(shopSocialMediaRequest.name(), shopSocialMediaRequest.link());

        socialMedias.add(newSocialMedia);
        shop.setSocialMedias(socialMedias);
        shop.setUpdatedAt(LocalDateTime.now());
        shop.setUpdatedBy(username);
        shopRepository.save(shop);
    }

    @Override
    public Double getShopRatingAverage(String slug) {

        return shopRatingRepository.calculateAverageRatingByShopSlug(slug);
    }

}
