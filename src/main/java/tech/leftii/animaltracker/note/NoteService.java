package tech.leftii.animaltracker.note;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tech.leftii.animaltracker.shared.DataMapper;
import tech.leftii.animaltracker.shared.RadixSort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for Notes, handles business logic and invoking persistence layer based on requests from NoteController
 */
@AllArgsConstructor
@Slf4j
@Service
public class NoteService {
    private final DataMapper mapper;
    private final NoteRepository noteRepository;

    /**
     * Adds new Note to DB
     *
     * @param newNote Dto received from client
     * @return NoteDto representation of the newly created NoteEntity
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    NoteDto addNote(@Valid NoteDto newNote){
        log.info("Adding new note: \n%s".formatted(newNote));
       return mapper.convert(noteRepository.save(mapper.convert(newNote)));
    }

    /**
     * Retrieve list of all notes in DB
     *
     * @return List of NoteDto for all Notes in DB
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    List<NoteDto> getAllNotes(){
        log.info("Retrieve all notes.");
        return noteRepository.findAll().stream().map(mapper::convert).toList();
    }

    /**
     * Retrieve list of all Notes associated with a specific Animal by AnimalId
     *
     * @param animalId ID of animal provided by client
     * @return List of AnimalDt associated with Animal
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    List<NoteDto> getAllNotesByAnimalId(UUID animalId){
        log.info("Retrieve all Notes by Animal ID: %s".formatted(animalId));
        return noteRepository.findAllByAnimal_Id(animalId).stream().map(mapper::convert).toList();
    }

    /**
     * Retrieve single Note with using its ID
     *
     * @param id ID of Note to retrieve supplied by client
     * @return NoteDto of Note if found, otherwise throws NoteNotFoundException
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    NoteDto getNoteById(UUID id){
        log.info("Retrieve Note by ID: %s".formatted(id));
        return noteRepository.findById(id).map(mapper::convert).orElseThrow(() -> new NoteNotFoundException(id));
    }

    /**
     * Updates Note that already exists in DB
     *
     * @param noteDto NoteDto with updated values
     * @param targetId ID of NoteEntity to update with new values
     * @return NoteDto representation of teh updated NoteEntity
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    NoteDto updateNote(@Valid NoteDto noteDto, UUID targetId){
        NoteEntity targetNote = noteRepository.findById(targetId).orElseThrow(() -> new NoteNotFoundException(targetId));
        log.info("Update Note with ID: %s".formatted(targetId));
        return mapper.convert(noteRepository.save(mapper.convert(noteDto, targetNote)));
    }

    /**
     * Deletes NoteEntity from DB using the supplied ID
     *
     * @param targetId ID of NoteEntity to delete
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    void deleteNote(UUID targetId){
        log.info("Delete Note with ID: %s".formatted(targetId));
        noteRepository.deleteById(targetId);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<NoteDto> getNotesSortedByDate(Optional<UUID> animalId, String sortDirection){
        return animalId.map(uuid -> RadixSort.sortNotes(getAllNotesByAnimalId(uuid),
                sortDirection.equalsIgnoreCase("ASC")))
                .orElseGet(() -> RadixSort.sortNotes(getAllNotes(), sortDirection.equalsIgnoreCase("ASC")));
    }

}
