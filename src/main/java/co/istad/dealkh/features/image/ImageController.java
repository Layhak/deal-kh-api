package co.istad.dealkh.features.image;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.image.dto.ImageUploadResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images/")
public class ImageController {
    private final ImageService imageService;


    @PostMapping(value = "", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<ImageUploadResponse> uploadSingleFile(
            @RequestPart("file") MultipartFile file, HttpServletRequest request
    ) {
        return BaseResponse
                .<ImageUploadResponse>createSuccess("Successfully uploaded image!")
                .setPayload(imageService.uploadSingleImage(file, request));
    }

    @PostMapping(value = "/multiple", consumes = "multipart/form-data")
    public BaseResponse<List<ImageUploadResponse>> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> images, HttpServletRequest request) {
        return BaseResponse
                .<List<ImageUploadResponse>>createSuccess("Successfully uploaded images!")
                .setPayload(imageService.uploadMultipleImages(images, request));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return imageService.serveFile(fileName, request);
    }

    @DeleteMapping("{fileName}")
    public BaseResponse<String> deleteFile(@PathVariable String fileName) {
        imageService.deleteFile(fileName);
        return BaseResponse.<String>deleteSuccess("File is deleted successfully!");
    }

}
