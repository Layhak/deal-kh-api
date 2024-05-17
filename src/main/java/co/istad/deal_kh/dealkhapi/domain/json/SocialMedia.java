package co.istad.deal_kh.dealkhapi.domain.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialMedia {
    private String socialName;
    private String socialLink;
    private String socialIcon;
}