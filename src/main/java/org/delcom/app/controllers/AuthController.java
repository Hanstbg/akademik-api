package org.delcom.app.controllers;

import jakarta.validation.Valid;
import org.delcom.app.configs.ApiResponse;
import org.delcom.app.dto.LoginForm;
import org.delcom.app.dto.RegisterForm;
import org.delcom.app.entities.User;
import org.delcom.app.services.UserService;
import org.delcom.app.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterForm form) {
        try {
            User user = userService.register(
                form.getName(), form.getEmail(), form.getPassword(), form.getRole());
            Map<String, String> data = new HashMap<>();
            data.put("id", user.getId().toString());
            data.put("name", user.getName());
            data.put("email", user.getEmail());
            data.put("role", user.getRole().name());
            return ResponseEntity.ok(ApiResponse.ok("Registrasi berhasil", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginForm form) {
        Optional<User> userOpt = userService.findByEmail(form.getEmail());
        if (userOpt.isEmpty() || !userService.checkPassword(userOpt.get(), form.getPassword())) {
            return ResponseEntity.status(401).body(ApiResponse.error("Email atau password salah"));
        }
        User user = userOpt.get();
        String token = JwtUtil.generateToken(user.getId(), user.getRole().name());

        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("role", user.getRole().name());
        return ResponseEntity.ok(ApiResponse.ok("Login berhasil", data));
    }
}
