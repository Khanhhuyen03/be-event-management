package com.example.myevent_be.configuration;

import com.example.myevent_be.entity.Role;
import com.example.myevent_be.entity.User;
import com.example.myevent_be.repository.RoleRepository;
import com.example.myevent_be.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            // Kiểm tra nếu người dùng với email 'admin123@gmail.com' chưa tồn tại
            if (userRepository.findByEmail("admin123@gmail.com").isEmpty()) {
                // Lấy vai trò ADMIN từ cơ sở dữ liệu
                Role adminRole = roleRepository.findByName("ADMIN")
                        .orElseGet(() -> {
                            // Tạo vai trò ADMIN nếu chưa tồn tại
                            Role newRole = new Role();
                            newRole.setName("ADMIN");
                            return roleRepository.save(newRole);
                        });

                // Tạo người dùng mới với vai trò ADMIN
                User user = User.builder()
                        .first_name("admin")
                        .last_name("admin")
                        .email("admin123@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(adminRole) // Gán vai trò ADMIN cho người dùng
                        .build();

                // Lưu người dùng vào cơ sở dữ liệu
                userRepository.save(user);

                log.warn("Admin user has been created with password: admin, please change it");
            }
        };
    }
}
