package io.nuwe.FemHack_JavaFemCoders.controller;

import io.nuwe.FemHack_JavaFemCoders.model.dto.LoginRequest;
import io.nuwe.FemHack_JavaFemCoders.model.dto.RegisterRequest;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.EmailAlreadyExistsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.InvalidCodeException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.UserNotFoundException;
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

    private final AuthService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a user.", description = "Register a user in the application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hello user, you're successfully registered!"),
            @ApiResponse(responseCode = "409", description = "This email is already registered."),
            @ApiResponse(responseCode = "404", description = "User email not found.")
    })
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {

        try {
            userService.register(request);
            return ResponseEntity.ok("Hello " + request.getName() + ", you're successfully registered!");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/home")
    @Operation(summary = "Login a user.", description = "Login a user in the application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Welcome back user! This is your token: token"),
            @ApiResponse(responseCode = "401", description = "Sorry, your credentials are not correct."),
            @ApiResponse(responseCode = "500", description = "Unable to obtain the current HTTP request. Please check the execution context."),
            @ApiResponse(responseCode = "400", description = "Invalid verification code.")
    })
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {

        try {
            return ResponseEntity.ok(userService.loginUser(loginRequest));
        } catch (BadCredentialsException | AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (InvalidCodeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

