package co.istad.dealkh.features.image;

import co.istad.dealkh.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
