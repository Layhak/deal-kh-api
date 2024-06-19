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

/**
 * ImageServiceImpl is a service implementation of {@link ImageService} that handles image upload, retrieval, and deletion operations.
 * It supports single and multiple image uploads and serves images as resources.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link Value} - Injects values from application properties.</li>
 * </ul>
 * </p>
 */
@Service("UserImages")
public class ImageServiceImpl implements ImageService {
    @Value("${file.storage-dir}")
    private String fileStorageDir;

    private static final Set<String> SUPPORTED_IMAGE_TYPES = Set.of(MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE);

    /**
     * Generates a full URL for the uploaded image.
     *
     * @param request  the HTTP servlet request
     * @param filename the filename of the uploaded image
     * @return the full URL of the uploaded image
     */
    private String generateImageUrl(HttpServletRequest request, String filename) {
        return String.format("%s://%s:%d/images/%s", request.getScheme(), request.getServerName(), request.getServerPort(), filename);
    }

    /**
     * Generates a download URL for the uploaded image.
     *
     * @param request  the HTTP servlet request
     * @param filename the filename of the uploaded image
     * @return the download URL of the uploaded image
     */
    private String generateDownloadImageUrl(HttpServletRequest request, String filename) {
        return String.format("%s://%s:%d/api/v1/images/download/%s", request.getScheme(), request.getServerName(), request.getServerPort(), filename);
    }

    /**
     * Uploads an image and returns the filename.
     *
     * @param file the image file to upload
     * @return the filename of the uploaded image
     * @throws ResponseStatusException if the file type is not supported or if the file upload fails
     */
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

    /**
     * This method uploads a single image and returns an {@link ImageUploadResponse} object.
     * It uses the {@link #uploadImage(MultipartFile)} method to upload the image.
     *
     * @param image
     * @param request
     * @return
     */
    @Override
    public ImageUploadResponse uploadSingleImage(MultipartFile image, HttpServletRequest request) {
        String filename = uploadImage(image);
        String fullImageUrl = generateImageUrl(request, filename);
        return ImageUploadResponse.builder().downloadUrl(generateDownloadImageUrl(request, filename)).fileType(image.getContentType()).size((float) image.getSize() / 1024) // in KB
                .filename(filename).fullUrl(fullImageUrl).build();
    }

    /**
     * This method uploads multiple images and returns a list of {@link ImageUploadResponse} objects.
     * It uses the {@link #uploadImage(MultipartFile)} method to upload each image.
     *
     * @param images
     * @param request
     * @return
     */
    @Override
    public List<ImageUploadResponse> uploadMultipleImages(List<MultipartFile> images, HttpServletRequest request) {
        List<ImageUploadResponse> fileResponses = new ArrayList<>();
        for (var file : images) {
            ImageUploadResponse fileResponse = uploadSingleImage(file, request);
            fileResponses.add(fileResponse);
        }
        return fileResponses;
    }

    /**
     * This method serves the file with the given filename from the server.
     *
     * @param filename
     * @param request
     * @return
     */
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

    /**
     * This method deletes the file with the given filename from the server.
     * It uses the {@link #generateImageUrl(HttpServletRequest, String)} method to generate the full URL of the file.
     *
     * @param filename
     * @throws ResponseStatusException if the file is not found or if the file deletion fails
     */
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
