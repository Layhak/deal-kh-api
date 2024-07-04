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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String gender;
    private String profile;

    private String username;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ImageListConverter.class)
    private List<Image> covers;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private LocalDate dob;

    private String location;

    private Boolean isDisabled;

    private Boolean isVerified;
    private String verificationToken;
    private LocalTime tokenExpiryDate;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = SocialListConverter.class)
    private List<SocialMedia> socialMedias;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dk_users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "dk_users_coupons",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "id"))
    private List<Coupon> coupons;

    @ManyToMany(mappedBy = "users")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Shop> shops;

    // for security
    private boolean isAccountExpired;
    private boolean isAccountLocked;
    private boolean isCredentialsExpired;
    private boolean isDeleted;  // for statistic usage!
    private boolean isBlocked; // disable
}