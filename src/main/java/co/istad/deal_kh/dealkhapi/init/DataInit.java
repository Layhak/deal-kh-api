package co.istad.deal_kh.dealkhapi.init;

import co.istad.deal_kh.dealkhapi.domain.Authority;
import co.istad.deal_kh.dealkhapi.domain.Image;
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

    @PostConstruct
    void initData() {
        try {
            initAuthorities();
            initRoles();
            initShops();
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

    private void initShops() {
        if (shopRepository.findAll().isEmpty()) {
            List<Shop> shops = new ArrayList<>();

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
            shop1.setCreatedAt(LocalDate.now());
            shop1.setUpdatedAt(LocalDateTime.now());
            shop1.setCreatedBy("Admin");
            shop1.setUpdatedBy("Admin");

            List<Image> images1 = new ArrayList<>();
            images1.add(new Image("https://example.com/image1.jpg", "Image 1"));
            images1.add(new Image("https://example.com/image2.jpg", "Image 2"));
            shop1.setImages(images1);

            shops.add(shop1);

            // Add more shops as needed

            shopRepository.saveAll(shops);
        }
    }
}