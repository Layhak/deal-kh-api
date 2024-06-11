package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.converter.SocialListConverter;
import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.domain.json.SocialMedia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalTime;
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

    private String slug;
    private String location;
    private String address;

    @Column(length = 250)
    private String description;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Convert(converter = SocialListConverter.class)
    private List<SocialMedia> socialMedias;

    private Boolean isDeleted;
    private Boolean isDisabled;
    private LocalTime openAt;
    private LocalTime closeAt;
    @ManyToOne
    @JoinColumn(name = "shop_type_id", nullable = false)
    private ShopType shopType;

    @Convert(converter = ImageListConverter.class)
    private List<Image> images;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "dk_user_shops",
            joinColumns = @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;
}