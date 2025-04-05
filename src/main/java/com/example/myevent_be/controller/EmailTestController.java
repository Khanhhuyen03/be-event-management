package com.example.myevent_be.controller;

import com.example.myevent_be.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-email")
@RequiredArgsConstructor
public class EmailTestController {
    private final EmailService emailService;

    @GetMapping
    public String sendTestEmail(@RequestParam String email) {
        try {
            String otp = "123456"; // Test với mã cố định
            emailService.sendOtpEmail(email, otp);
            return "Đã gửi email xác nhận đến " + email;
        } catch (MessagingException e) {
            return "Lỗi gửi email: " + e.getMessage();
        }
    }
}
