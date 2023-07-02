package io.nuwe.FemHack_JavaFemCoders.controller;

import io.nuwe.FemHack_JavaFemCoders.model.dto.LoginRequest;
import io.nuwe.FemHack_JavaFemCoders.model.dto.RegisterRequest;
import io.nuwe.FemHack_JavaFemCoders.model.dto.VerificationRequest;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.EmailAlreadyExistsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.InvalidCodeException;
import io.nuwe.FemHack_JavaFemCoders.model.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a user.", description = "Register a user in the application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hello user, you're successfully registered!"),
            @ApiResponse(responseCode = "409", description = "This email is already registered.")
    })
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {

        try {
            authService.register(request);
            return ResponseEntity.ok("Hello " + request.getName() + ", you're successfully registered!");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/home/login")
    @Operation(summary = "Part one of login a user.", description = "First authentication factor of login a" +
            " user in the application with MFA.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A verification code will be sent to your email address."),
            @ApiResponse(responseCode = "401", description = "Invalid username or password.")
    })
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {

        try {
            authService.loginUserWithMFA(request);
            return ResponseEntity.ok("A verification code will be sent to your email address.");
        } catch (BadCredentialsException | AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/home/verify")
    @Operation(summary = "Part two of login a user.", description = "Second authentication factor of login a" +
            " user in the application with MFA.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Welcome back user! This is your token: token"),
            @ApiResponse(responseCode = "401", description = "User email not found."),
            @ApiResponse(responseCode = "500", description = "Unable to obtain the current HTTP request. Please check the execution context."),
            @ApiResponse(responseCode = "400", description = "Invalid verification code.")
    })
    public ResponseEntity<String> verifyCode(@RequestBody VerificationRequest request) {

        try {
            String response = authService.verifyCode(request.getEmail(), request.getVerificationCode());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (InvalidCodeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

