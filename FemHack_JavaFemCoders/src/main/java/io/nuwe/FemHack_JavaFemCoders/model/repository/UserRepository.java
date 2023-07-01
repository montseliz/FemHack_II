package io.nuwe.FemHack_JavaFemCoders.model.repository;

import io.nuwe.FemHack_JavaFemCoders.model.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
