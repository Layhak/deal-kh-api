package co.istad.dealkh.features.image;

import co.istad.dealkh.features.image.dto.ImageUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    ImageUploadResponse uploadSingleImage(MultipartFile image, HttpServletRequest request);

    List<ImageUploadResponse> uploadMultipleImages(List<MultipartFile> images, HttpServletRequest request);

    ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request);

    void deleteFile(String filename);
}
