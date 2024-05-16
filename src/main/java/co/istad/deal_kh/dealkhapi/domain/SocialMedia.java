package co.istad.deal_kh.dealkhapi.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dk_social_medias")
@Data
public class SocialMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String socialName;
    private String socialLink;
    private String socialIcon;
}