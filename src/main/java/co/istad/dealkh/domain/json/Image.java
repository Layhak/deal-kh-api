package co.istad.dealkh.domain.json;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private String url;
    private String description;
    // Add any other fields you need for the image
}