package co.istad.deal_kh.dealkhapi.converter;

import co.istad.deal_kh.dealkhapi.domain.json.SocialMedia;
import jakarta.persistence.Converter;

@Converter
public class SocialListConverter extends JsonListConverter<SocialMedia> {
    public SocialListConverter() {
        super(SocialMedia.class);
    }
}
