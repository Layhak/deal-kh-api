package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_coupons")
public class Coupon extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @Column(nullable = false)
    private BigDecimal value;

    private LocalDate expiredAt;
    private Boolean isExpired;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Shop shop;

    @ManyToMany(mappedBy = "coupons")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> users;
}
