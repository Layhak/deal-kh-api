package co.istad.dealkh.entity;

import co.istad.dealkh.audit.Auditable;
import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.entity.json.Image;
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

    @Column(unique = true)
    private String address;

    @Column(length = 250)
    private String description;

    @Column(name = "phone_number", nullable = false, unique = true)
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

    @Convert(converter = ImageListConverter.class)
    @Column(name = "images", nullable = false)
    private List<Image> images;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "dk_user_shops",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;
}