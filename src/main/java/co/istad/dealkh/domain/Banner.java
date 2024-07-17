package co.istad.dealkh.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false, updatable = false)
    private String uuid;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String image;
    private String shopLink;
    private LocalDate expiredAt;
    private Boolean isExpired;
    private String bannerType;

    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }

}
