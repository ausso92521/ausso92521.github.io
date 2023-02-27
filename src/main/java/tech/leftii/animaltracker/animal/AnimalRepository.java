package tech.leftii.animaltracker.animal;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for interactions with the Animal table in the DB
 */
@Repository
public interface AnimalRepository extends ListCrudRepository<AnimalEntity, UUID> {
    Optional<AnimalEntity> findByNameIgnoreCase(String name);
    List<AnimalEntity> findAllByOwner_Id(UUID id);
}
