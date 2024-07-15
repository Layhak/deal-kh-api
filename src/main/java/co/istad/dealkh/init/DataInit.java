package co.istad.dealkh.init;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.domain.enumType.ShopVerify;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.features.authority.AuthorityRepository;
import co.istad.dealkh.features.category.CategoryRepository;
import co.istad.dealkh.features.discount.DiscountRepository;
import co.istad.dealkh.features.discounttype.DiscountTypeRepository;
import co.istad.dealkh.features.product.ProductRepository;
import co.istad.dealkh.features.role.RoleRepository;
import co.istad.dealkh.features.shop.ShopRepository;
import co.istad.dealkh.features.shoptype.ShopTypeRepository;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.features.wishlist.WishListRepository;
import co.istad.dealkh.validator.formatter.SlugFormatter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInit {
    private static final Logger logger = Logger.getLogger(DataInit.class.getName());
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final ShopRepository shopRepository;
    private final ShopTypeRepository shopTypeRepository;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountTypeRepository discountTypeRepository;
    private final WishListRepository wishListRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @PostConstruct
    void initData() {
        try {
            initAuthorities();
            initRoles();
            initUsers();
            initShopTypes();
            initCategories();
//            initDiscountTypes();
//            initDiscounts();
//            initShops();
            logger.info("Data initialized successfully");
        } catch (Exception e) {
            logger.severe("Error in data initialization: " + e.getMessage());
            e.printStackTrace(); // This will print the full stack trace to the console
            throw new RuntimeException(e);
        }
    }

    private void initAuthorities() {
        List<String> authorities = List.of("READ", "WRITE", "DELETE");
        if (authorityRepository.findAll().isEmpty()) {
            authorities.forEach(auth -> {
                Authority authority = new Authority();
                authority.setName(auth);
                authorityRepository.save(authority);
            });
        }
    }

    private void initRoles() {
        List<String> roles = List.of("SUPER_ADMIN", "ADMIN", "BUYER", "SELLER");
        if (roleRepository.findAll().isEmpty()) {
            HashSet<Authority> allAuth = new HashSet<>(authorityRepository.findAll());
            for (String role : roles) {
                Role roleObj = new Role();
                if (role.equals("SUPER_ADMIN") || role.equals("ADMIN") || role.equals("SELLER")) {
                    roleObj.setAuthorities(new HashSet<>(allAuth));
                } else if (role.equals("BUYER")) {
                    roleObj.setAuthorities(allAuth.stream().filter(authority -> authority.getName().equals("READ")).collect(Collectors.toSet()));
                }
                roleObj.setName(role);
                roleRepository.save(roleObj);
            }
        }
    }

    private void initUsers() {
        if (userRepository.findAll().isEmpty()) {
            List<User> users = new ArrayList<>();
            List<Role> roles = roleRepository.findAll();
            List<Image> images = new ArrayList<>();
            // Example user
            User user1 = new User();
            user1.setFirstName("admin");
            user1.setLastName("admin");
            user1.setUsername("admin");
            user1.setEmail("admin@gmail.com");
            user1.setGender("Male");
            user1.setPassword(passwordEncoder.encode("Admin@100$"));
            images.add(new Image("https://example.com/image1.jpg"));
            images.add(new Image("https://example.com/image2.jpg"));
            user1.setCovers(images);
            user1.setProfile("https://dealkh-api.istad.co/images/3cc9b0d1-8794-4f77-946a-c4a5dd662130.png");
//            user1.setTokenExpiryDate(LocalTime.now().plusHours(24)); // Token valid for 24 hours
            user1.setPhoneNumber("0987654321");
            user1.setDob(LocalDate.of(2001, 1, 31));
            user1.setLocation("Phnom Penh");
            user1.setIsDisabled(false);
            user1.setIsVerified(true);
            user1.setCreatedAt(LocalDateTime.now());
            user1.setRoles(Set.of(roles.get(0), roles.get(1), roles.get(2), roles.get(3)));
            users.add(user1);
            userRepository.saveAll(users);
        }
    }

    private void initShopTypes() {
        List<String> shopTypes = List.of("Technology", "Electronic", "Restaurant", "Clothing", "Bookstore", "Pharmacy");
        if (shopTypeRepository.findAll().isEmpty()) {
            shopTypes.forEach(type -> {
                ShopType shopType = new ShopType();
                shopType.setName(type);
                shopType.setSlug(SlugFormatter.formatSlug(type));
                shopTypeRepository.save(shopType);
            });
        }
    }

    private void initShops() {
        if (shopRepository.findAll().isEmpty()) {
            Shop shop = new Shop();
            shop.setName("Layhak Online Shop");
            shop.setAddress("Phnom Penh");
            shop.setDescription("At Radiant Glow Skincare, we believe that everyone deserves to have radiant, healthy skin.");
            shop.setPhoneNumber("0123456789");
            shop.setEmail("layhakshop@gmail.com");
            shop.setSlug(SlugFormatter.formatSlug(shop.getName()));
            shop.setOpenAt(LocalTime.of(8, 0, 0));
            shop.setCloseAt(LocalTime.of(17, 30, 0));
            shop.setIsDeleted(false);
            shop.setIsDisabled(false);
            shop.setIsVerified(ShopVerify.APPROVED);
            shop.setShopType(shopTypeRepository.findAll().get(0));
            shop.setUsers(List.of(userRepository.findByUsername("admin").get()));
            shopRepository.save(shop);
        } else {
            logger.severe("User with email 'admin@gmail.com' not found");
        }
    }

    private void initCategories() {
        List<String> categories = List.of("Food", "Drink", "Accessories", "Clothes", "Skin Care", "Shoes", "Electronic");
        List<String> icons = List.of(
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg",
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg",
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg",
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg",
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg",
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg",
                "http://dealkh-api.istad.co:80/images/67b22426-a4f1-44bd-a165-95a4e206a065.jpg"
        );

        if (categoryRepository.findAll().isEmpty()) {
            for (int i = 0; i < categories.size(); i++) {
                Category category = new Category();
                category.setName(categories.get(i));
                category.setIcon(icons.get(i));
                category.setSlug(categories.get(i).toLowerCase().replace(" ", "-"));
                category.setCreatedAt(LocalDateTime.now());
                category.setUpdatedAt(LocalDateTime.now());
                category.setCreatedBy("admin");
                category.setUpdatedBy("admin");
                categoryRepository.save(category);
            }
        }
    }

    private void initDiscountTypes() {
        List<String> discountTypes = List.of("No Discount", "Discount Off", "Shop Coupons", "Event", "Buy More Get More", "Clearance Sales", "Flash Sales", "Top Sales");
        if (discountTypeRepository.findAll().isEmpty()) {
            discountTypes.forEach(type -> {
                DiscountType discountType = new DiscountType();
                discountType.setSlug(SlugFormatter.formatSlug(type));
                discountType.setName(type);
                discountTypeRepository.save(discountType);
            });
        }
    }

    private void initDiscounts() {
        if (discountRepository.findAll().isEmpty()) {
            List<DiscountType> discountTypes = discountTypeRepository.findAll();
            List<Shop> shops = shopRepository.findAll();
            Discount discount = new Discount();

            discountTypes.forEach(discountType -> {

                discount.setDescription("Discount Description");
                // Random 0-100 except for No Discount
                if (!discountType.getName().equals("No Discount")) {
                    //random 0-100

                    BigDecimal random = BigDecimal.valueOf(Math.random() * 100);
                    int intValue = random.intValue();
                    discount.setDiscountValue(BigDecimal.valueOf(intValue));
                } else {
                    discount.setDiscountValue(BigDecimal.valueOf(0));
                }
                discount.setIsPercentage(true);
                discount.setExpiredAt(LocalDate.now().plusDays(30));
                discount.setIsExpired(false);
                discount.setDiscountType(discountType);
                discountRepository.save(discount);
            });
            shops.forEach(shop -> {
                discount.setShop(shop);
                discountRepository.save(discount);
            });
        }
    }
}
