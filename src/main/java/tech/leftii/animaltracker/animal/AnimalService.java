package tech.leftii.animaltracker.animal;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tech.leftii.animaltracker.shared.DataMapper;

import java.util.List;
import java.util.UUID;

/**
 * Class handles business logic and using repository to retrieve/manipulate data from persistence layer.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final DataMapper mapper;

    /**
     * Add new Animal to database
     * @param animalDto new animal information
     * @return AnimalDto of newly created entity
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    AnimalDto addAnimal(AnimalDto animalDto){
        log.info("Adding animal \n%s".formatted(animalDto));
        return mapper.convert(animalRepository.save(mapper.convert(animalDto)));
    }
    /**
     * Retrieve all Animals as a list
     * @return List of AnimalDto
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    List<AnimalDto> getAllAnimals(){
        log.info("Retrieving List of all Animals.");
        return animalRepository.findAll().stream().map(mapper::convert).toList();
    }

    /**
     * Retrieve a single Animal using ID
     * @param targetId ID of animal to retrieve
     * @return AnimalDto
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    AnimalDto getAnimalById(UUID targetId) throws AnimalNotFoundException{
        log.info("Retrieving Animal with ID: %s".formatted(targetId));
        return animalRepository.findById(targetId).map(mapper::convert).orElseThrow(() ->
                new AnimalNotFoundException(targetId.toString()));
    }

    /**
     * Retrieve list of all animals by owner's ID
     * @param ownerId User ID of owner
     * @return List of AnimalDto
     */
    @PreAuthorize("hasAnyAuthority('SCOPE_USER')")
    List<AnimalDto> getAllAnimalsByOwnerId(UUID ownerId){
        log.info("Retrieving Animals using Owner ID: %s".formatted(ownerId));
        return animalRepository.findAllByOwner_Id(ownerId).stream().map(mapper::convert).toList();
    }

    /**
     * Delete animal from database
     * @param id ID of animal to delete
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    void deleteAnimalById(UUID id){
        log.info("Deleting Animal with ID: %s".formatted(id));
        animalRepository.deleteById(id);
    }
}
