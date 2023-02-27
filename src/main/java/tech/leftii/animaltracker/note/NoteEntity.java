package tech.leftii.animaltracker.note;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import tech.leftii.animaltracker.animal.AnimalEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity class for Notes
 * This creates all the parameters and relationships in the 'note' table
 * Lombok used for conciseness
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Table(name = "note")
@Slf4j
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "note_id")
    @NotNull
    private UUID id;

    @Column(name = "content")
    @NotBlank(message = "Note content cannot be left blank.")
    @Size(min = 1, max = 3000)
    private String content;

    @Column(name = "date_added")
    @CreatedDate
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    @NotNull(message = "Note must be assigned to an animal.")
    private AnimalEntity animal;

    @Column(name = "note_date")
    private LocalDateTime noteDate;

    @PrePersist
    protected void onCreate(){
        setCreationDate(LocalDateTime.now());
        log.info("Attempting to save new NoteEntity");
    }

    @PostPersist
    protected void afterCreate(){
        log.info("Saved new NoteEntity with ID: %s".formatted(id));
    }

    @PreUpdate
    protected void onUpdate(){
        log.info("Attempting to update NoteEntity with ID: %s".formatted(id));
    }

    @PostUpdate
    protected void afterUpdate(){
        log.info("Updated NoteEntity with ID: %s".formatted(id));
    }

    @PreRemove
    protected void onDelete(){
        log.info("Attempting to delete NoteEntity with ID: %s".formatted(id));
    }

    @PostRemove
    protected void afterDelete(){
        log.info("NoteEntity deleted.");
    }

    @PostLoad
    protected void afterLoad(){
        log.info("NoteEntity Loaded with ID: %s".formatted(id));
    }
}
