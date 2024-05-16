package co.istad.deal_kh.dealkhapi.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 250)
    private String description;

    @Column(nullable = false)
    private double discountPercentage;

    private LocalDate expiredAt;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
