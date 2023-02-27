package tech.leftii.animaltracker.note;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository used to interact with 'note' table in DB
 */
@Repository
public interface NoteRepository extends ListCrudRepository<NoteEntity, UUID> {
    List<NoteEntity> findAllByAnimal_Id(UUID id);
}
