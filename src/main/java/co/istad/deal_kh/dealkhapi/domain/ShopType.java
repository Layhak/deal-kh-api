package co.istad.deal_kh.dealkhapi.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "dk_shop_types")
public class ShopType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String icon;
}