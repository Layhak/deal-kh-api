package co.istad.dealkh.features.image;

import co.istad.dealkh.features.image.dto.ImageUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service("UserImages")
public class ImageServiceImpl implements ImageService {
    @Value("${file.storage-dir}")
    private String fileStorageDir;

    private static final Set<String> SUPPORTED_IMAGE_TYPES = Set.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE);

    private String generateImageUrl(HttpServletRequest request, String filename) {
        return String.format("%s://%s:%d/images/%s", request.getScheme(), request.getServerName(), request.getServerPort(), filename);
    }

    private String generateDownloadImageUrl(HttpServletRequest request, String filename) {
        return String.format("%s://%s:%d/api/v1/files/download/%s", request.getScheme(), request.getServerName(), request.getServerPort(), filename);
    }

    private String uploadImage(MultipartFile file) {
        String contentType = file.getContentType();
        if (!SUPPORTED_IMAGE_TYPES.contains(contentType)) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, contentType + " not allowed!!");
        }
        try {
            Path fileStoragePath = Path.of(fileStorageDir);
            if (!Files.exists(fileStoragePath)) {
                Files.createDirectories(fileStoragePath);
            }
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID() + "." + extension;

            Files.copy(file.getInputStream(), fileStoragePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed", ex);
        }
    }

    @Override
    public ImageUploadResponse uploadSingleImage(MultipartFile image, HttpServletRequest request) {
        String filename = uploadImage(image);
        String fullImageUrl = generateImageUrl(request, filename);
        return ImageUploadResponse.builder().downloadUrl(generateDownloadImageUrl(request, filename)).fileType(image.getContentType()).size((float) image.getSize() / 1024) // in KB
                .filename(filename).fullUrl(fullImageUrl).build();
    }

    @Override
    public List<ImageUploadResponse> uploadMultipleImages(List<MultipartFile> images, HttpServletRequest request) {
        List<ImageUploadResponse> fileResponses = new ArrayList<>();
        for (var file : images) {
            ImageUploadResponse fileResponse = uploadSingleImage(file, request);
            fileResponses.add(fileResponse);
        }
        return fileResponses;
    }

    @Override
    public ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request) {
        try {
            Path imagePath = Path.of(fileStorageDir).resolve(filename);
            Resource resourceUrl = new UrlResource(imagePath.toUri());
            if (resourceUrl.exists()) {
                return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg")).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resourceUrl.getFilename() + "\"").body(resourceUrl);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
            }
        } catch (MalformedURLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Malformed URL", ex);
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Path imagePath = Path.of(fileStorageDir).resolve(filename);
            Files.deleteIfExists(imagePath);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File deletion failed", ex);
        }
    }
}
