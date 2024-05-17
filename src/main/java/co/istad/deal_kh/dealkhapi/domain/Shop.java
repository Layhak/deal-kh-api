package co.istad.deal_kh.dealkhapi.domain;

import co.istad.deal_kh.dealkhapi.utils.Auditable;
import co.istad.deal_kh.dealkhapi.utils.JsonListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_shops")
public class Shop extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String location;

    @Column(length = 250)
    private String description;

    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean isDeleted;
    private Boolean isDisabled;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;

    @ManyToOne
    @JoinColumn(name = "shop_type_id", nullable = false)
    private ShopType shopType;
    @Convert(converter = JsonListConverter.class)
    @Column(name = "images", nullable = false)
    private List<Image> images;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "dk_user_shops",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;
}