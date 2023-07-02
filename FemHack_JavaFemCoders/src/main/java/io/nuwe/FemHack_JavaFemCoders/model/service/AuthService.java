package io.nuwe.FemHack_JavaFemCoders.model.service;

import io.nuwe.FemHack_JavaFemCoders.model.domain.Role;
import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import io.nuwe.FemHack_JavaFemCoders.model.dto.LoginRequest;
import io.nuwe.FemHack_JavaFemCoders.model.dto.RegisterRequest;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.EmailAlreadyExistsException;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.InvalidCodeException;
import io.nuwe.FemHack_JavaFemCoders.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String BAD_CREDENTIALS = "Invalid username or password.";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MfaService mfaService;
    private final UserConnectionService userConnectionService;

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
    }

    /**
     * Method to login a user in the database. Used in /home/login endpoint in the AuthController layer.
     */
    public void loginUserWithMFA(LoginRequest request) {
        User user = authenticateAndValidateUser(request);

        String code = mfaService.generateVerificationCode();
        user.setVerificationCode(code);
        userRepository.save(user);

        mfaService.sendVerificationCode(user.getEmail(), code);
    }

    /**
     * Method to login a user in the database. Used in /home/verify endpoint in the AuthController layer.
     */
    public String verifyCode(String email, String code) {

        String jwtToken = jwtService.generateToken(getUserFromEmail(email));

        if(mfaService.isValidCode(email, code)) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(servletRequestAttributes != null) {
                HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
                userConnectionService.logConnection(servletRequest, email);
            } else {
                throw new NullPointerException("Unable to obtain the current HTTP request. Please check the execution context.");
            }
            return "Welcome back " + getNameFromEmail(email) + "! This is your token: " + jwtToken;

        } else {
            throw new InvalidCodeException("Invalid verification code");
        }
    }

    /**
     * Private method to validate and authenticate a LoginRequest and return a user.
     * Used in loginUserWithMFA method.
     */
    private User authenticateAndValidateUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(request.getPassword(), u.getPassword()))
                .orElseThrow(() -> new BadCredentialsException(BAD_CREDENTIALS));

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

        return user;
    }

    /**
     * Private method to obtain the User by its email. Used in verifyCode method.
     */
    private User getUserFromEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new BadCredentialsException("Sorry, your credentials are not correct.");
        }
    }

    /**
     * Private method to obtain the name of the user registered. Used in verifyCode method.
     */
    private String getNameFromEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get().getName();
        } else {
            throw new BadCredentialsException("Sorry, your credentials are not correct.");
        }
    }
}

