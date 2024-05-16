package co.istad.deal_kh.dealkhapi.domain;


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
@Table(name = "dk_product_feedback")
public class ProductFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @Convert(converter = JsonListConverter.class)
    @Column(name = "images", nullable = false)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
