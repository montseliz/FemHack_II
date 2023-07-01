package io.nuwe.FemHack_JavaFemCoders.model.service;

import io.nuwe.FemHack_JavaFemCoders.model.domain.Role;
import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import io.nuwe.FemHack_JavaFemCoders.model.dto.JwtResponse;
import io.nuwe.FemHack_JavaFemCoders.model.dto.LoginRequest;
import io.nuwe.FemHack_JavaFemCoders.model.dto.RegisterRequest;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.EmailAlreadyExistsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.InvalidCodeException;
import io.nuwe.FemHack_JavaFemCoders.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final String BAD_CREDENTIALS = "Invalid username or password.";
    private final MfaService mfaService;

    /**
     * Method to register a user in the database. Used in the AuthController layer.
     */
    public void register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            throw new EmailAlreadyExistsException("This email is already registered.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        mfaService.obtainVerificationCode(user.getEmail());
    }

    /**
     * Method to login a user in the database. Used in the AuthController layer.
     */
    public String loginUser(LoginRequest request) {
        String token = authenticate(request).getToken();
        String name = getNameFromLoginRequest(request);

        boolean isCodeValid = mfaService.verifyCode(request.getEmail(), request.getVerificationCode());
        if (isCodeValid) {
            return "Welcome back " + name + "! This is your token: " + token;
        } else {
            throw new InvalidCodeException("Invalid verification code.");
        }
    }

    /**
     * Method to obtain the count of users stored in the database. Used in the AuthController layer.
     */
    public int numberOfUsers() {
        return (int) userRepository.count();
    }

    /**
     * Private method to obtain the token. Used in loginUser method.
     */
    private JwtResponse authenticate(LoginRequest request) throws AuthenticationException {
        User user = null;
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException(BAD_CREDENTIALS);
            }
        } else {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(BAD_CREDENTIALS);
        }

        String jwtToken = jwtService.generateToken(user);

        return JwtResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Private method to obtain the Name of the user registered. Used in loginUser method.
     */
    private String getNameFromLoginRequest(LoginRequest request) {

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            return userOptional.get().getName();
        } else {
            throw new BadCredentialsException("Sorry, your credentials are not correct.");
        }
    }


}

