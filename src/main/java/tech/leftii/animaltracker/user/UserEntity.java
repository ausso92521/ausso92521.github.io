package tech.leftii.animaltracker.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import tech.leftii.animaltracker.animal.AnimalEntity;
import tech.leftii.animaltracker.role.RoleEntity;

import java.util.List;
import java.util.UUID;


/*
 * Stores user information and credentials in database
 * */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Table(name = "person")
public class UserEntity {
    @Id
    @NotNull(message = "ID cannot be null.")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "email", unique = true)
    @Email(message = "Must be a properly formatted email.")
    @NotBlank(message = "Email must no be blank.")
    private String email;

    @Column(name = "secret")
    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @Column(name = "first_name")
    @NotBlank(message = "First name cannot be blank.")
    @Size(min = 2, max = 60, message = "First name must be between 2 and 60 characters.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name cannot be blank.")
    @Size(min = 2, max = 60, message = "Last name must be between 2 and 60 characters.")
    private String lastName;

    @Column(name = "phone")
    @NotBlank(message = "Phone number cannot be blank.")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits.")
    private String phone;

    @Column(name = "is_enabled")
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("true")
    private Boolean isEnabled;

    @Column(name = "is_non_expired")
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("true")
    private Boolean isNonExpired;

    @Column(name = "is_non_locked")
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("true")
    private Boolean isNonLocked;

    @Column(name = "is_credentials_non_expired")
    @Generated(GenerationTime.INSERT)
    @ColumnDefault("true")
    private Boolean isCredentialsNonExpired;

    /*
     * Maps User and Role in Many-to-Many table for multiple roles
     * */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<AnimalEntity> animals;
    
    @PrePersist
    protected void onCreate(){
        log.info("Attempting to save new UserEntity");
    }
    
    @PostPersist
    protected void afterCreate(){
        log.info("Saved new UserEntity with ID: %s".formatted(id));
    }
    
    @PreUpdate
    protected void onUpdate(){
        log.info("Attempting to update UserEntity with ID: %s".formatted(id));
    }
    
    @PostUpdate
    protected void afterUpdate(){
        log.info("Updated UserEntity with ID: %s".formatted(id));
    }
    
    @PreRemove
    protected void onDelete(){
        log.info("Attempting to delete UserEntity with ID: %s".formatted(id));
    }
    
    @PostRemove
    protected void afterDelete(){
        log.info("UserEntity deleted.");
    }
    
    @PostLoad
    protected void afterLoad(){
        log.info("UserEntity Loaded with ID: %s".formatted(id));
    }
}
