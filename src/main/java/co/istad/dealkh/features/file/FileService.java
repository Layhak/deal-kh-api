package co.istad.dealkh.features.file;

import co.istad.dealkh.features.file.dto.FileResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileResponse uploadSingleFile(MultipartFile file, HttpServletRequest request);

    List<FileResponse> uploadMultipleFiles(MultipartFile[] files, HttpServletRequest request);


    ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request);

    void deleteFile(String filename);
}
