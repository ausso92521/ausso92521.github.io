package tech.leftii.animaltracker.note;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Endpoints for Notes (/api/v1/notes)
 */
@RestController
@RequestMapping("api/v1/notes")
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    /**
     * Adds new note to the database
     *
     * @param noteDto New noteDto received from client
     * @return NoteDto representation of the newly saved Note from DB
     */
    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto addNote(@RequestBody @Valid NoteDto noteDto){
        return noteService.addNote(noteDto);
    }

    /**
     * Returns Note with provided ID
     *
     * @param id ID of Note provided by client
     * @return NoteDto
     */
    @GetMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public NoteDto getNote(@PathVariable UUID id){
        return noteService.getNoteById(id);
    }

    /**
     * Returns all Notes By AnimalID if supplied
     * Otherwise returns all Notes in database
     * @param animalId Option ID of animal to retrieve notes for, if null will return all notes
     * @param sortDirection Optional string parameter, should be either "ASC" or "DESC", causes list to return in either
     *                      ascending or descending order
     * @return List of Notes
     */
    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public List<NoteDto> getNotes(@RequestParam(required = false) Optional<UUID> animalId,
                                  @RequestParam(required = false) Optional<String> sortDirection){
        if(sortDirection.isPresent()){
            return noteService.getNotesSortedByDate(animalId, sortDirection.get());
        }
        if(animalId.isPresent()){
            return noteService.getAllNotesByAnimalId(animalId.get());
        }
        return noteService.getAllNotes();
    }

    /**
     * Allows user to update a specific Note that already exists in the DB
     *
     * @param id ID of the note to update
     * @param noteDto NoteDto provided by client with new values
     * @return NoteDto representation of the newly updated Note in the DB
     */
    @PatchMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public NoteDto updateNote(@PathVariable UUID id, @RequestBody @Valid NoteDto noteDto){
        return noteService.updateNote(noteDto, id);
    }

    /**
     * Deletes a Note by ID if it exists, otherwise does nothing
     *
     * @param id ID of Note to delete
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable UUID id){
        noteService.deleteNote(id);
    }
}
