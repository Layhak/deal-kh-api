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
@Table(name = "dk_products")
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private double ratingAvg;
    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double discountPrice;

    @Column(length = 250)
    private String description;

    @Convert(converter = ImageListConverter.class)
    private List<Image> images;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}
