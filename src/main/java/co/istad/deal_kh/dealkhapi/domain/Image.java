package co.istad.deal_kh.dealkhapi.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String url;
    private String description;
    // Add any other fields you need for the image
}