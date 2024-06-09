package co.istad.dealkh.features.auth;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.auth.dto.AuthRequest;
import co.istad.dealkh.features.auth.dto.AuthResponse;
import co.istad.dealkh.features.auth.dto.RefreshTokenRequest;
import co.istad.dealkh.features.user.UserService;
import co.istad.dealkh.features.user.dto.UserCreateRequest;
import co.istad.dealkh.features.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@SecurityRequirements(value = {})
public class AuthRestController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        return BaseResponse.<AuthResponse>ok("Successfully login!")
                .setPayload(authService.login(request));
    }

    @PostMapping("/refresh")
    public BaseResponse<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return BaseResponse.<AuthResponse>ok("Successfully refresh token!")
                .setPayload(authService.refreshToken(request));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UserCreateRequest.class),
                            examples = @ExampleObject(value = """
                                            {
                                                "firstName": "Hom",
                                                "lastName": "Pheakakvotey",
                                                "username": "Votey",
                                                "email": "votey@gmail.com",
                                                "password": "votey",
                                                "gender": "gender",
                                                "phoneNumber": "0987654321",
                                                "dob": "2001-07-01",
                                                "location": "phnom penh",
                                                "roles": [
                                                  "BUYER"
                                                ]
                                              }
                                    """)
                    )
            )
    )
    public BaseResponse<UserResponse> registerUser(
            @Valid @RequestBody UserCreateRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess("Successfully create new user!")
                .setPayload(userService.createUser(userRequest));
    }
}
