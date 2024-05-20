package co.istad.dealkh.init;

import co.istad.dealkh.domain.*;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.domain.json.SocialMedia;
import co.istad.dealkh.feature.Authorities.AuthorityRepository;
import co.istad.dealkh.feature.roles.RoleRepository;
import co.istad.dealkh.feature.shop.ShopRepository;
import co.istad.dealkh.feature.shoptype.ShopTypeRepository;
import co.istad.dealkh.feature.users.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    @PostConstruct
    void initData() {
        try {
            initAuthorities();
            initRoles();
            initShopTypes();
            initShops();
            initUsers();
            logger.info("Data initialized successfully");
        } catch (Exception e) {
            logger.severe("Error in data initialization");
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
                    roleObj.setAuthorities(allAuth.stream()
                            .filter(authority -> authority.getName().equals("READ"))
                            .collect(Collectors.toSet()));
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
            List<SocialMedia> social = new ArrayList<>();

            // Example user
            User user1 = new User();
            user1.setFirstName("Heng");
            user1.setLastName("Layhak");
            user1.setUsername("layhak");
            user1.setEmail("layhak@gmail.com");
            user1.setGender("Male");
            user1.setPassword("123456");
            user1.setProfileImage("https://example.com/profile.jpg");
            social.add(new SocialMedia("Facebook", "www.facebook.com", "facebook_icon.jpg"));
            social.add(new SocialMedia("Telegram", "www.telegram.com", "telegram_icon.jpg"));
            user1.setSocialMedias(social);
            user1.setPhoneNumber("123456789");
            user1.setDob(LocalDate.of(2002, 11, 27));
            user1.setLocation("Phnom Penh");
            user1.setIsDisabled(false);
            user1.setCreatedAt(LocalDateTime.now());
            user1.setCreatedAt(LocalDateTime.now());
            user1.setCreatedBy("Admin");
            user1.setUpdatedBy("Admin");
            user1.setRole(roles.get(1));
            users.add(user1);
            userRepository.saveAll(users);
        }
    }

    //
    private void initShopTypes() {
        List<String> shopTypes = List.of("Type 1", "Type 2", "Type 3");
        if (shopTypeRepository.findAll().isEmpty()) {
            shopTypes.forEach(type -> {
                ShopType shopType = new ShopType();
                shopType.setName(type);
                shopTypeRepository.save(shopType);
            });
        }
    }


    private void initShops() {
        if (shopRepository.findAll().isEmpty()) {
            List<Shop> shops = new ArrayList<>();

            // Get an example shop type
            ShopType shopType = shopTypeRepository.findAll().get(0);

            // Example shop with images
            Shop shop1 = new Shop();
            shop1.setName("Shop 1");
            shop1.setLocation("Location 1");
            shop1.setDescription("Description 1");
            shop1.setPhoneNumber("123456789");
            shop1.setEmail("shop1@example.com");
            shop1.setIsDeleted(false);
            shop1.setIsDisabled(false);
            shop1.setOpenAt(LocalDateTime.of(2023, 1, 1, 9, 0));
            shop1.setCloseAt(LocalDateTime.of(2023, 1, 1, 18, 0));
            shop1.setCreatedAt(LocalDateTime.now());
            shop1.setUpdatedAt(LocalDateTime.now());
            shop1.setCreatedBy("Admin");
            shop1.setUpdatedBy("Admin");

            List<Image> images1 = new ArrayList<>();
            images1.add(new Image("https://example.com/image1.jpg", "Image 1"));
            images1.add(new Image("https://example.com/image2.jpg", "Image 2"));
            shop1.setImages(images1);

            // Set the shop type
            shop1.setShopType(shopType);

            shops.add(shop1);

            // Add more shops as needed

            shopRepository.saveAll(shops);
        }
    }
}