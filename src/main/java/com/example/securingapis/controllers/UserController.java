package com.example.securingapis.controllers;

import com.example.securingapis.models.LoginRequest;
import com.example.securingapis.models.RegisterRequest;
import com.example.securingapis.models.User;
import com.example.securingapis.repositories.UserRepository;
import com.example.securingapis.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtTokenUtil.generateToken(request.getUsername());
            String message = String.format("Login successful. Username: %s, Password: %s, Role: USER, Token: %s",
                    request.getUsername(), request.getPassword(), token);
            return ResponseEntity.ok(Collections.singletonMap("response", message));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> adminOnly() {
        return ResponseEntity.ok("This is an admin-only endpoint!");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<?> userOnly() {
        return ResponseEntity.ok("This is a user-only endpoint!");
    }
}