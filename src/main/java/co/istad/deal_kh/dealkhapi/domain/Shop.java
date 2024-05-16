package co.istad.deal_kh.dealkhapi.domain;


import co.istad.deal_kh.dealkhapi.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "dk_shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = StringListConverter.class)
    @Column(name = "images", nullable = false)
    private List<String> images = new ArrayList<>();

    private String location;
    private String description;
    private String phoneNumber;
    private String email;
    private Boolean isDeleted;
    private Boolean isDisabled;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private LocalDate createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;


    @ManyToOne
    @JoinColumn(name = "shop_type_id")
    private ShopType shopType;
}