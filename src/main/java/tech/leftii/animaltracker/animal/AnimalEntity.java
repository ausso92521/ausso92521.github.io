package tech.leftii.animaltracker.animal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import tech.leftii.animaltracker.note.NoteEntity;
import tech.leftii.animaltracker.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Slf4j
@Table(name = "animal")
public class AnimalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "animal_id")
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Animal name must not be blank.")
    @Size(min = 2, max = 60, message = "Animal name must be between 2 and 60 characters long.")
    private String name;

    @Column(name = "dob")
    @NotNull(message = "Date of birth must be specified.")
    private LocalDateTime dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    @NotNull(message = "Animal must have owner specified.")
    private UserEntity owner;

    @OneToMany(mappedBy = "animal")
    @ToString.Exclude
    private List<NoteEntity> notes;

    @PrePersist
    protected void onCreate(){
        log.info("Attempting to save new AnimalEntity");
    }

    @PostPersist
    protected void afterCreate(){
        log.info("Saved new AnimalEntity with ID: %s".formatted(id));
    }

    @PreUpdate
    protected void onUpdate(){
        log.info("Attempting to update AnimalEntity with ID: %s".formatted(id));
    }

    @PostUpdate
    protected void afterUpdate(){
        log.info("Updated AnimalEntity with ID: %s".formatted(id));
    }

    @PreRemove
    protected void onDelete(){
        log.info("Attempting to delete AnimalEntity with ID: %s".formatted(id));
    }

    @PostRemove
    protected void afterDelete(){
        log.info("AnimalEntity deleted.");
    }

    @PostLoad
    protected void afterLoad(){
        log.info("AnimalEntity Loaded with ID: %s".formatted(id));
    }
}
