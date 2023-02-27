package tech.leftii.animaltracker.animal;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/animals")
@AllArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    /**
     * Add new Animal to database
     * @param animalDto new AnimalDto provided by client
     * @return AnimalDto of newly saved entity
     */
    @PostMapping({"","/"})
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDto addNewAnimal(@RequestBody @Valid AnimalDto animalDto){
        return animalService.addAnimal(animalDto);
    }

    /**
     * Retrieve list of all Animals in db.
     * @return List of AnimalDto
     */
    @GetMapping({"","/"})
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalDto> getAllAnimals(@RequestParam(required = false) Optional<UUID> ownerId){
        if(ownerId.isPresent()){
            return animalService.getAllAnimalsByOwnerId(ownerId.get()); // Return by OwnerId if present in request
        }
        return animalService.getAllAnimals();                           // Return all animals if Owner not present
    }

    /**
     * Retrieve specific animal based on ID
     * @param id Animal object's ID
     * @return AnimalDto for the specified animal
     */
    @GetMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public AnimalDto getAnimalById(@PathVariable UUID id){
        return animalService.getAnimalById(id);
    }

    /**
     * Deletes the Animal Entity at the given ID
     * @param id ID of Animal to delete
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable UUID id){
        animalService.deleteAnimalById(id);
    }
}
