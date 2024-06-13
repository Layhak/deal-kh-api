package co.istad.dealkh.domain;

import co.istad.dealkh.converter.ImageListConverter;
import co.istad.dealkh.domain.json.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_product_scrape")
public class ProductScrape {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    @Column(length = 250)
    private String description;


    private double price;


    private double discountPrice;


    private String image;

    private BigDecimal discountPercentage;

    private Double rating;

    private String url;

}
