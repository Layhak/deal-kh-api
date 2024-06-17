package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import co.istad.dealkh.domain.enumType.GrantStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_wishlists")
public class WishList extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    Double discountPercentage;


    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "discountType_id", nullable = false)
    private DiscountType discountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private GrantStatus isGranted = GrantStatus.REQUESTING;

    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }


}