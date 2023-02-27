package tech.leftii.animaltracker.user;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
 * Repository exposes database access methods to the service layer
 * */
@Repository
public interface UserRepository extends ListCrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmailIgnoreCase(String email);
}