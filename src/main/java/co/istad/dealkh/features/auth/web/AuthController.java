package co.istad.dealkh.features.auth.web;

import co.istad.dealkh.base.BaseResponse;
import co.istad.dealkh.features.auth.AuthService;
import co.istad.dealkh.features.auth.dto.AuthRequest;
import co.istad.dealkh.features.auth.dto.AuthResponse;
import co.istad.dealkh.features.auth.dto.RefreshTokenRequest;
import co.istad.dealkh.features.resetpassword.ResetPasswordService;
import co.istad.dealkh.features.resetpassword.dto.ConfirmOtpCode;
import co.istad.dealkh.features.resetpassword.dto.ResetPasswordRequest;
import co.istad.dealkh.features.resetpassword.dto.SentOtpRequest;
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

/**
 * AuthController is a REST controller that provides endpoints for authentication and user registration.
 * It handles login, token refresh, and user registration requests.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link RestController} - Indicates that this class is a REST controller.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link RequestMapping} - Maps HTTP requests to handler methods of MVC and REST controllers.</li>
 * <li>{@link SecurityRequirements} - Disables security requirements for the endpoints in this controller.</li>
 * </ul>
 * </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@SecurityRequirements(value = {})
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final ResetPasswordService resetPasswordService;

    /**
     * Handles user login requests.
     *
     * @param request the authentication request containing email and password
     * @return a {@link BaseResponse} containing the authentication response with access and refresh tokens
     */
    @PostMapping("/login")
    public BaseResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        return BaseResponse.<AuthResponse>ok("Successfully login!")
                .setPayload(authService.login(request));
    }

    /**
     * Handles token refresh requests.
     *
     * @param request the refresh token request containing the refresh token
     * @return a {@link BaseResponse} containing the new access and refresh tokens
     */
    @PostMapping("/refresh")
    public BaseResponse<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return BaseResponse.<AuthResponse>ok("Successfully refresh token!")
                .setPayload(authService.refreshToken(request));
    }

    /**
     * Handles user registration requests.
     *
     * @param userRequest the user creation request containing user details
     * @return a {@link BaseResponse} containing the created user response
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = UserCreateRequest.class),
                            examples = @ExampleObject(value = """
                            {
                                 "firstName": "panda",
                                 "lastName": "panda",
                                 "username": "panda",
                                 "email": "panda@gmail.com",
                                 "password": "Panda@123",
                                 "confirmedPassword": "Panda@123",
                                 "gender": "male",
                                 "phoneNumber": "0987654321",
                                 "dob": "2001-01-01",
                                 "location": "phnom penh"
                            }
                        """)
                    )
            )
    )
    public BaseResponse<?> registerUser(
            @Valid @RequestBody UserCreateRequest userRequest) {
        userService.createUser(userRequest);
        return BaseResponse.createSuccess("Verification e-mail sent. Please check your inbox.");
    }

    @PostMapping("/send-otp")
    public BaseResponse<?> sendOtp(@Valid @RequestBody SentOtpRequest sentOtpRequest) {
        return resetPasswordService.sendOtp(sentOtpRequest);
    }

    @PostMapping("/confirm-otp")
    public BaseResponse<?> confirmOtp(@Valid @RequestBody ConfirmOtpCode confirmOtpCode) {
        return resetPasswordService.confirmOtp(confirmOtpCode);
    }

    @PostMapping("/reset-password")
    public BaseResponse<?> updatePassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return resetPasswordService.resetPassword(resetPasswordRequest);
    }

    @GetMapping("/verify")
    public BaseResponse<?> verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return BaseResponse.ok("Email verified successfully!");
    }
}
