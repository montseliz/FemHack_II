package io.nuwe.FemHack_JavaFemCoders.model.service;

import io.nuwe.FemHack_JavaFemCoders.model.domain.Role;
import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import io.nuwe.FemHack_JavaFemCoders.model.domain.UserConnection;
import io.nuwe.FemHack_JavaFemCoders.model.dto.AdminDTO;
import io.nuwe.FemHack_JavaFemCoders.model.dto.UserConnectionDTO;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserConnectionService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserConnectionDTO> getConnections() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .flatMap(user -> user.getUserConnections().stream()
                        .map(connection -> convertToDTO(user, connection)))
                .toList();
    }

    /**
     * Method to demonstrate that it can't be access without a token.
     * Used in getUsersCount method in the UserConnectionController layer.
     */
    public int numberOfUsers() {
        return (int) userRepository.count();
    }

    public void logConnection(HttpServletRequest request, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            UserConnection userConnection = UserConnection.builder()
                    .ipAddress(request.getRemoteAddr())
                    .connectionTime(LocalDateTime.now())
                    .endpoint(request.getRequestURI())
                    .build();

            user.addUserConnections(userConnection);
            userRepository.save(user);
        }
    }

    /**
     * Method to check if the user authenticated is an Admin. Used in the getUsersConnection method
     * from the UserConnectionController layer.
     */
    public boolean isUserAdmin(AdminDTO adminDTO) {
        User user = validateUserAdminPassword(adminDTO);
        return user.getRole() == Role.ADMIN;
    }

    /**
     * Private method to convert a UserConnection to UserConnectionDTO. Used in getConnections method.
     */
    private UserConnectionDTO convertToDTO(User user, UserConnection connection) {
        return UserConnectionDTO.builder()
                .email(user.getEmail())
                .ipAddress(connection.getIpAddress())
                .connectionTime(connection.getConnectionTime())
                .endpoint(connection.getEndpoint()).build();
    }

    /**
     * Private method to validate the Admin password. Used in isUserAdmin method.
     */
    private User validateUserAdminPassword(AdminDTO adminDTO) {
        String email = adminDTO.getEmail();
        String password = adminDTO.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Incorrect credentials, try again."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Incorrect credentials, try again.");
        }

        return user;
    }
}

