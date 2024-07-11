package co.istad.dealkh.domain;

import co.istad.dealkh.audit.Auditable;
import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.domain.json.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_products")
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "slug", unique = true, nullable = false, updatable = false)
    private String slug;

    private double ratingAvg;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double discountPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ImageListConverter.class)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @PrePersist
    protected void onCreate() {
        if (slug == null) {
            slug = UUID.randomUUID().toString();
        }
    }

}
