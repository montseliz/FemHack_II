package io.nuwe.FemHack_JavaFemCoders.model.service;

import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import io.nuwe.FemHack_JavaFemCoders.model.domain.UserConnection;
import io.nuwe.FemHack_JavaFemCoders.model.dto.UserConnectionDTO;
import io.nuwe.FemHack_JavaFemCoders.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserConnectionService {

    @Autowired
    UserRepository userRepository;

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
     * Private method to convert a UserConnection to UserConnectionDTO. Used in getConnections method.
     */
    private UserConnectionDTO convertToDTO(User user, UserConnection connection) {
        return UserConnectionDTO.builder()
                .email(user.getEmail())
                .ipAddress(connection.getIpAddress())
                .connectionTime(connection.getConnectionTime())
                .endpoint(connection.getEndpoint()).build();
    }
}

