package co.istad.deal_kh.dealkhapi.domain;


import co.istad.deal_kh.dealkhapi.converter.ImageListConverter;
import co.istad.deal_kh.dealkhapi.domain.json.Image;
import co.istad.deal_kh.dealkhapi.utils.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dk_shop_reports")
public class ShopReport extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 250)
    private String description;

    @Convert(converter = ImageListConverter.class)
    @Column(name = "images", nullable = false)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
