package co.istad.deal_kh.dealkhapi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "dk_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String gender;
    private String password;
    private String profileImage;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String location;
    private Boolean isBlocked;
    private Boolean isDisabled;

    @ManyToOne
    @JoinColumn(name = "social_id")
    private SocialMedia socialMedia;

    private LocalDate createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}