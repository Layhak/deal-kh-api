package co.istad.deal_kh.dealkhapi.init;


import co.istad.deal_kh.dealkhapi.domain.Authority;
import co.istad.deal_kh.dealkhapi.domain.Role;
import co.istad.deal_kh.dealkhapi.domain.Shop;
import co.istad.deal_kh.dealkhapi.feature.Authorities.AuthorityRepository;
import co.istad.deal_kh.dealkhapi.feature.roles.RoleRepository;
import co.istad.deal_kh.dealkhapi.feature.shop.ShopRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// populate database ( role with some data )
@Component
@RequiredArgsConstructor
public class DataInit {
    private static final Logger logger = Logger.getLogger(DataInit.class.getName());
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final ShopRepository shopRepository;
//    private final UserRepository userRepository;

    @PostConstruct
    void initData() {
        try {
            initAuthorities();
            initRoles();
            initShops();
            logger.info("Data initialized successfully");
        } catch (Exception e) {
            logger.info("error in data initialization");
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

    //init shop with array of image
    private void initShops() {
        if (shopRepository.findAll().isEmpty()) {
            logger.info("Initializing shops...");
            for (int i = 1; i <= 4; ++i) {
                Shop shop = new Shop();
                shop.setName("Shop" + i);
                if (i % 2 == 0) {
                    shop.setImages(List.of("image1", "image2", "image3"));
                } else {
                    shop.setImages(List.of("image1"));
                }
                shop.setLocation("Location" + i);
                shop.setDescription("Description" + i);
                shop.setPhoneNumber("0123456789");
                shop.setEmail("shop" + i + "@gmail.com");
                shop.setOpenAt(LocalDateTime.now());
                shop.setCloseAt(LocalDateTime.now());
                shop.setCreatedAt(LocalDate.now());
                shop.setUpdatedAt(LocalDateTime.now());
                shop.setCreatedBy("Admin");
                shop.setUpdatedBy("Admin");
//                shop.setShopType(shopTypeRepository.findAll().get(i));
                shopRepository.save(shop);
            }
        }
    }

//    private void initUsers() {
//        List<Role> roles = roleRepository.findAll();
//
//
//        if (userRepository.findAll().isEmpty()) {
//            logger.info("Initializing users...");
//            for (int i = 0; i < 4; ++i) {
//                User user = new User();
//                user.setFirstName("User" + i);
//                user.setLastName("User" + i);
//                user.setEmail("user" + i + "@gmail.com");
//                user.setGender(User.Gender.MALE);
//                user.setPassword("123");
//                user.setRoles(new HashSet<>(List.of(roles.get(i))));
//                userRepository.save(user);
//                logger.info("Saved user: User{}"+ i);
//            }
//        }
//    }
}



