package co.istad.dealkh.features.user;

import co.istad.dealkh.features.user.dto.UserProfileResponse;
import co.istad.dealkh.features.user.dto.UserRequest;
import co.istad.dealkh.features.user.dto.UserResponse;
import co.istad.dealkh.paging.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserResponse getById(Long id);

    PageResponse<UserResponse> getAllUsers(int page, int size, String field, String order, Map<String, String> params);

    UserProfileResponse getUserProfile(Long id);

    UserResponse createUser(UserRequest userRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);

    UserResponse createProfileImage(Long id, String imageUrl, String description);


    boolean existsById(Long id);

    UserResponse disableUser(Long id);

    UserResponse enableUser(Long id);

    //getAllEnabledUsers
    List<UserResponse> getAllUsersByStatus(String status);

    UserResponse uploadMultipleImages(Long id, List<MultipartFile> files, List<String> descriptions, HttpServletRequest request);
}
