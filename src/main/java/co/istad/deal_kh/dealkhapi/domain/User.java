package co.istad.deal_kh.dealkhapi.domain;

import co.istad.deal_kh.dealkhapi.utils.Auditable;
import co.istad.deal_kh.dealkhapi.utils.JsonListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


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

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

    @Column(nullable = false)
    private String password;

    private String profileImage;
    private String phoneNumber;
    private LocalDate dob;

    @Column(unique = true)
    private String location;

    private Boolean isDisabled;

    @Convert(converter = JsonListConverter.class)
    @Column(name = "socialMedia", nullable = false)
    private List<SocialMedia> socialMedias;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(mappedBy = "users")
    private List<Shop> shops;
}