package io.nuwe.FemHack_JavaFemCoders.controller;

import io.nuwe.FemHack_JavaFemCoders.model.dto.LoginRequest;
import io.nuwe.FemHack_JavaFemCoders.model.dto.RegisterRequest;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.EmailAlreadyExistsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.InvalidCodeException;
import io.nuwe.FemHack_JavaFemCoders.model.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {

        try {
            userService.register(request);
            return ResponseEntity.ok("Hello " + request.getName() + ", you're successfully registered!");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/auth/home")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {

        try {
            return ResponseEntity.ok(userService.loginUser(loginRequest));
        } catch (BadCredentialsException | InvalidCodeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}

