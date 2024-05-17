package co.istad.deal_kh.dealkhapi.domain;

import co.istad.deal_kh.dealkhapi.utils.Auditable;
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
    private LocalDate dateOfBirth;

    @Column(unique = true)
    private String location;

    private Boolean isDisabled;

    @ManyToOne
    @JoinColumn(name = "social_id")
    private SocialMedia socialMedia;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany(mappedBy = "users")
    private List<Shop> shops;
}