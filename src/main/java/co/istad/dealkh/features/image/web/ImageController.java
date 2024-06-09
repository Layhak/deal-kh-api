package co.istad.dealkh.features.image.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.image.ImageService;
import co.istad.dealkh.features.image.dto.ImageUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ImageController is a controller for managing images.
 * It handles uploading, downloading, and deleting images.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images/")
public class ImageController {
    private final ImageService imageService;

    /**
     * Uploads an image to the server.
     *
     * @param file    the file to upload
     * @param request the request containing the file
     * @return a {@link ImageUploadResponse} containing the uploaded image details
     */
    @PostMapping(value = "", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<ImageUploadResponse> uploadSingleFile(
            @RequestPart("file") MultipartFile file, HttpServletRequest request
    ) {
        return BaseResponse
                .<ImageUploadResponse>createSuccess("Successfully uploaded image!")
                .setPayload(imageService.uploadSingleImage(file, request));
    }

    /**
     * Uploads multiple images to the server.
     *
     * @param images
     * @param request
     * @return
     */
    @PostMapping(value = "/multiple", consumes = "multipart/form-data")
    public BaseResponse<List<ImageUploadResponse>> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> images, HttpServletRequest request) {
        return BaseResponse
                .<List<ImageUploadResponse>>createSuccess("Successfully uploaded images!")
                .setPayload(imageService.uploadMultipleImages(images, request));
    }

    /**
     * Serves an image from the server.
     *
     * @param fileName the name of the image to serve
     * @param request  the request containing the image
     * @return a {@link ResponseEntity} containing the image
     */
    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return imageService.serveFile(fileName, request);
    }

    /**
     * Deletes an image from the server.
     *
     * @param fileName
     * @return
     */
    @DeleteMapping("{fileName}")
    public BaseResponse<String> deleteFile(@PathVariable String fileName) {
        imageService.deleteFile(fileName);
        return BaseResponse.<String>ok("File is deleted successfully!");
    }

}
