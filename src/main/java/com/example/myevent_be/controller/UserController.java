package com.example.myevent_be.controller;

import com.example.myevent_be.dto.request.*;
import com.example.myevent_be.dto.response.ApiResponse;
import com.example.myevent_be.dto.response.EventResponse;
import com.example.myevent_be.dto.response.UserResponse;
import com.example.myevent_be.exception.AppException;
import com.example.myevent_be.exception.ErrorCode;
import com.example.myevent_be.repository.IStorageService;
import com.example.myevent_be.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final IStorageService storageService;

    @PostMapping("/signing-up")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PatchMapping(value = "/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    ApiResponse<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestPart(value = "avatar", required = false) MultipartFile file,
            @RequestPart(value = "data", required = false) String data) {
        
        UserUpdateRequest request = new UserUpdateRequest();
        if (data != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                request = objectMapper.readValue(data, UserUpdateRequest.class);
                log.info("Parsed update data: {}", data);
            } catch (Exception e) {
                log.error("Error parsing update data", e);
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dữ liệu không hợp lệ: " + e.getMessage());
            }
        }

        try {
            if (file != null && !file.isEmpty()) {
                // Kiểm tra loại file
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chỉ hỗ trợ file ảnh (jpg, png, ...)");
                }

                // Kiểm tra kích thước
                if (file.getSize() > 5 * 1024 * 1024) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File ảnh quá lớn, tối đa 5MB");
                }

                // Store the uploaded file
                String fileName = storageService.storeFile(file);
                log.info("File stored successfully with name: {}", fileName);

                // Set the image path in the request - chỉ lưu tên file
                request.setAvatar(fileName);
            }

            UserResponse response = userService.updateUser(userId, request);
            log.info("User updated successfully: {}", response);
            return ApiResponse.<UserResponse>builder()
                    .result(response)
                    .build();
        } catch (Exception e) {
            log.error("Error updating user: ", e);
            if (e instanceof AppException) {
                throw e;
            }
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @PutMapping("/update-role/{userId}")
    public UserResponse updateUserRole(@PathVariable String userId, @RequestBody UpdateUserRoleRequest request) {
        return userService.updateUserRole(userId, request.getRole());
    }

    @PutMapping("/update-password/{userId}")
    public UserResponse resetPassword(@PathVariable String userId, @RequestBody ResetPasswordRequest2 request){
        return userService.resetPassword(userId, request);
    }
}
