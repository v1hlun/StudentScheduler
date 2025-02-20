package org.example.Controller;

import org.example.model.User;
import org.example.Repository.UserRepository;
import org.example.DTO.LoginRequest;
import org.example.DTO.JwtResponse;
import org.example.Util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();


            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                return new JwtResponse(token);
            }
        }
        throw new RuntimeException("Invalid credentials");

        /*if (userOptional.isPresent()) {
            User user = userOptional.get();

            System.out.println("Хеш пароля в БД: " + user.getPassword()); // Вывод хеша пароля
            System.out.println("Введенный пароль: " + request.getPassword());
            System.out.println("Хеш введенного пароля: " + passwordEncoder.encode(request.getPassword()));

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername());
                System.out.println("Пароль совпадает, токен выдан!");
                return new JwtResponse(token);
            } else {
                System.out.println("Пароль НЕ совпадает!");
            }
        } else {
            System.out.println("Пользователь не найден!");
        }
        throw new RuntimeException("Invalid credentials");*/
    }

}
