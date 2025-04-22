package com.example.myevent_be.service;

import com.example.myevent_be.dto.request.ResetPasswordRequest2;
import com.example.myevent_be.dto.request.UserCreateRequest;
import com.example.myevent_be.dto.request.UserUpdateRequest;
import com.example.myevent_be.dto.response.UserResponse;
import com.example.myevent_be.entity.Role;
import com.example.myevent_be.entity.User;
import com.example.myevent_be.enums.VerificationType;
import com.example.myevent_be.exception.AppException;
import com.example.myevent_be.exception.ErrorCode;
import com.example.myevent_be.mapper.UserMapper;
import com.example.myevent_be.repository.RoleRepository;
import com.example.myevent_be.repository.UserRepository;
import com.example.myevent_be.repository.PasswordResetTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserVerificationService userVerificationService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Kiểm tra xem role "USER" đã tồn tại chưa
        Role role = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });

        user.setRole(role);

        // Lưu user trước
        user = userRepository.save(user);

        // Gửi email xác nhận sau khi lưu user
        userVerificationService.sendVerificationEmail(user.getEmail());

        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String id) {
        log.info("Fetching user with ID:  {}", id);
        return userMapper.toUserResponse(userRepository
                .findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

//    @Transactional
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Không tìm thấy người dùng."
                ));

        userMapper.updateUser(user, request);
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse resetPassword(String id, ResetPasswordRequest2 request2){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy người dùng."
                ));

        userMapper.resetPssword(user, request2);
        if (request2.getOldPassword() != null) {
            user.setPassword(passwordEncoder.encode(request2.getConfirmPassword()));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        // Delete associated password reset tokens first
        passwordResetTokenRepository.deleteByUser(user);
        
        // Now delete the user
        userRepository.delete(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse updateUserRole(String userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        user.setRole(role);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Transactional
    public void verifyEmail(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Không tìm thấy người dùng."
                ));
        
        if (userVerificationService.verifyCode(code)) {
            log.info("Email verified successfully for user: {}", email);
        } else {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Mã xác nhận không hợp lệ hoặc đã hết hạn."
            );
        }
    }
}
