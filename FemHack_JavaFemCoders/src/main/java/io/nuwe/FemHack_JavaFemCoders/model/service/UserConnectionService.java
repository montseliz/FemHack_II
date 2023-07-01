package io.nuwe.FemHack_JavaFemCoders.model.service;

import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import io.nuwe.FemHack_JavaFemCoders.model.domain.UserConnection;
import io.nuwe.FemHack_JavaFemCoders.model.dto.UserConnectionDTO;
import io.nuwe.FemHack_JavaFemCoders.model.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserConnectionService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    //TODO: Fer DTO

    public List<UserConnectionDTO> getLast25Connections() {
        List<User> users = userRepository.findAll();
        List<UserConnectionDTO> allConnections = convertUsersToDto(users);
        int startIndex = Math.max(0, allConnections.size() - 25);
        return allConnections.subList(startIndex, allConnections.size());
    }


    public void logConnection(HttpServletRequest request, String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            UserConnection userConnection = UserConnection.builder()
                    .ipAddress(request.getRemoteAddr())
                    .connectionTime(LocalDateTime.now())
                    .requestMethod(HttpMethod.valueOf(request.getMethod()))
                    .endpoint(request.getRequestURI())
                    .build();

            user.setUserConnection(userConnection);
            userRepository.save(user);
        }
    }

    private UserConnectionDTO convertUserToDto(User user) {
        return modelMapper.map(user, UserConnectionDTO.class);
    }

    private List<UserConnectionDTO> convertUsersToDto(List<User> users) {
        return users.stream().map(this::convertUserToDto).collect(Collectors.toList());
    }
}

