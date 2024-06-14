package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_discounts")
public class Discount extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String description;

    @Column(nullable = false)
    private BigDecimal discountValue;

    private LocalDate expiredAt;
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_type_id", nullable = false)
    private DiscountType discountType;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}