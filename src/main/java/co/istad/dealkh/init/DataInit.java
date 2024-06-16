package co.istad.dealkh.init;

import co.istad.dealkh.domain.*;
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
            initShopTypes();
            initUsers();
            initShops();
            initDiscountTypes();
            initDiscounts();
            initDiscounts();
            initCategories();
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
            user1.setFirstName("Heng");
            user1.setLastName("Layhak");
            user1.setUsername("layhak");
            user1.setEmail("layhakheng@gmail.com");
            user1.setGender("Male");
            user1.setPassword(passwordEncoder.encode("Layhak@123"));
            images.add(new Image("https://example.com/image1.jpg"));
            images.add(new Image("https://example.com/image2.jpg"));
            user1.setImages(images);

            user1.setPhoneNumber("123456789");
            user1.setDob(LocalDate.of(2002, 11, 27));
            user1.setLocation("Phnom Penh");
            user1.setIsDisabled(false);
            user1.setCreatedAt(LocalDateTime.now());
            user1.setRoles(Set.of(roles.get(0)));
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
            shop.setName("Layhak Shop");
            shop.setAddress("Phnom Penh");
            shop.setDescription("Shop description");
            shop.setPhoneNumber("0123456789");
            shop.setEmail("layhakshop@gmail.com");
            shop.setSlug(SlugFormatter.formatSlug(shop.getName()));
            shop.setOpenAt(LocalTime.of(8, 0, 0));
            shop.setCloseAt(LocalTime.of(17, 30, 0));
            shop.setIsDeleted(false);
            shop.setIsDisabled(false);
            shop.setShopType(shopTypeRepository.findAll().get(0));
//            shop.setUsers(List.of(userRepository.findByUsername("layhak").get()));
            shopRepository.save(shop);
        } else {
            logger.severe("User with email 'layhak@gmail.com' not found");
        }
    }

//    private void initUserShops() {
//        if (userRepository.findByUsername("layhak").isPresent()) {
//            User user = userRepository.findByUsername("layhak").get();
//            List<Shop> shops = shopRepository.findAll();
//            shops.forEach(shop -> {
//                user.getShops().add(shop);
//                userRepository.save(user);
//            });
//        }
//    }

    private void initCategories() {
        List<String> categories = List.of("Electronics", "Fashion", "Health", "Books", "Food");
        if (categoryRepository.findAll().isEmpty()) {
            categories.forEach(category -> {
                Category category1 = new Category();
                category1.setName(category);
                category1.setIcon("icon.jpg");
                category1.setSlug(category.toLowerCase().replace(" ", "-"));
                category1.setCreatedAt(LocalDateTime.now());
                category1.setUpdatedAt(LocalDateTime.now());
                category1.setCreatedBy("Admin");
                categoryRepository.save(category1);
            });
        }
    }

    private void initDiscountTypes() {
        List<String> discountTypes = List.of("No Discount", "Coupon", "Promotion", "Sale");
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

            discountTypes.forEach(discountType -> {
                Discount discount = new Discount();
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
                discount.setExpiredAt(LocalDate.now().plusDays(30));
                discount.setDiscountType(discountType);
                discountRepository.save(discount);
            });
        }
    }
}
