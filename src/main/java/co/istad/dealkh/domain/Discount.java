package co.istad.dealkh.domain;

import co.istad.dealkh.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_discount")
public class Discount extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 250)
    private String description;

    @Column(nullable = false)
    private double discountPercentage;

    private LocalDateTime expiredAt;


}
