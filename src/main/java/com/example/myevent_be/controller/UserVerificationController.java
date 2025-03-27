package com.example.myevent_be.controller;

import com.example.myevent_be.entity.User;
import com.example.myevent_be.entity.UserVerificationRequest;
import com.example.myevent_be.repository.UserRepository;
import com.example.myevent_be.repository.UserVerificationRequestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/verification")
public class UserVerificationController {
    //    UserVerificationService userVerificationService;
    UserVerificationRequestRepository verificationRequestRepository;
    UserRepository userRepository;
//
//    @GetMapping("/verify")
//    public ResponseEntity<?> verify(@RequestParam String email, @RequestParam String code) {
//        UserVerificationRequest request = userVerificationService.verify(email, code);
//        return ResponseEntity.ok(request);
//    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, @RequestParam String code) {
        Optional<UserVerificationRequest> requestOpt =
                verificationRequestRepository.findByEmailAndCode(email, code);

        if (requestOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Mã xác nhận không hợp lệ hoặc đã được sử dụng.");
        }

        UserVerificationRequest request = requestOpt.get();

        // Kiểm tra mã có hết hạn không
        if (request.getExpiration_time().before(new Date())) {
            return ResponseEntity.badRequest().body("Mã xác nhận đã hết hạn.");
        }

        // Cập nhật trạng thái tài khoản
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
//            user.setActive(true);
            userRepository.save(user);
        }

        // Đánh dấu mã đã sử dụng
//        request.setUsed(true);
        verificationRequestRepository.save(request);

        return ResponseEntity.ok("Tài khoản đã được xác nhận thành công.");
    }
}
