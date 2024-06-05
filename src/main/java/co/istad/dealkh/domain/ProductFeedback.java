package co.istad.dealkh.domain;


import co.istad.dealkh.audit.Auditable;
import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.domain.json.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_product_feedback")
public class ProductFeedback extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String description;

    @Convert(converter = ImageListConverter.class)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
