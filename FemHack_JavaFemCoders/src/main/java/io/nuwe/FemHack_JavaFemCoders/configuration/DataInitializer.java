package io.nuwe.FemHack_JavaFemCoders.configuration;

import io.nuwe.FemHack_JavaFemCoders.model.domain.Role;
import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import io.nuwe.FemHack_JavaFemCoders.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("javafemcoders@gmail.com");
            admin.setPassword("qgcdgwerknjqgavl");
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }
}


