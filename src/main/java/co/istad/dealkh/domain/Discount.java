package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private double discountPercentage;

    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(name = "discount_type_id", nullable = false)
    private DiscountType discountType;
}