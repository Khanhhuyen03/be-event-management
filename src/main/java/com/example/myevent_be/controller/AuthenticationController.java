package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.*;
import com.example.myevent_be.dto.response.ApiResponse;
import com.example.myevent_be.dto.response.AuthenticationResponse;
import com.example.myevent_be.dto.response.IntrospectResponse;
import com.example.myevent_be.dto.response.UserResponse;
import com.example.myevent_be.entity.User;
import com.example.myevent_be.mapper.UserMapper;
import com.example.myevent_be.repository.UserRepository;
import com.example.myevent_be.service.AuthenticationService;
import com.example.myevent_be.service.PasswordResetService;
import com.example.myevent_be.service.UserService;
import com.example.myevent_be.service.UserVerificationService;
import com.nimbusds.jose.JOSEException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    PasswordResetService passwordResetService;
    UserVerificationService userVerificationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticated(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/forgot-password")
    ApiResponse<Void> forgotPassword(@Valid @RequestBody ForgetPasswordRequest request)
            throws MessagingException {
        passwordResetService.sendResetPasswordToken(request);
        return ApiResponse.<Void>builder()
                .message("Mã xác thực đã được gửi đến email của bạn")
                .build();
    }

    @PostMapping("/verify-pass-code")
    public ResponseEntity<String> verifyCode(@RequestBody ForgetPasswordRequest request) {
        boolean isValid = passwordResetService.verifyCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(isValid ? "Mã đúng" : "Mã sai");
    }

    @PostMapping("/reset-password")
    ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getNewPassword());
        return ApiResponse.<Void>builder()
                .message("Đặt lại mật khẩu thành công")
                .build();
    }

    @GetMapping("/verify-code")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> verifyEmail(@RequestParam String code) {
        if (userVerificationService.verifyCode(code)) {
            // Giả sử bạn có thể lấy user qua email liên quan tới code,
            // hoặc hiện tại bạn chưa cần trả token/user sau khi xác thực.
            return ResponseEntity.ok(ApiResponse.<AuthenticationResponse>builder()
                    .message("Xác thực thành công")
                    .build());
        }

        return ResponseEntity.badRequest()
                .body(ApiResponse.<AuthenticationResponse>builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Mã xác thực không hợp lệ hoặc đã hết hạn")
                        .build());
    }
}
