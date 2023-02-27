package tech.leftii.animaltracker.role;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import tech.leftii.animaltracker.user.UserEntity;

import java.util.List;
import java.util.UUID;

/**
 * Entity used to map roles to users for authorization purposes
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Slf4j
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private UUID id;

    /**
     * Name value for role (USER, ADMIN)
     */
    @Column(name = "name")
    private String name;

    /**
     * Creates Many-to-Many table relationship so users can have more than one
     */
    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    @PrePersist
    protected void onCreate(){
        log.info("Attempting to save new RoleEntity");
    }

    @PostPersist
    protected void afterCreate(){
        log.info("Saved new RoleEntity with ID: %s".formatted(id));
    }

    @PreUpdate
    protected void onUpdate(){
        log.info("Attempting to update RoleEntity with ID: %s".formatted(id));
    }

    @PostUpdate
    protected void afterUpdate(){
        log.info("Updated RoleEntity with ID: %s".formatted(id));
    }

    @PreRemove
    protected void onDelete(){
        log.info("Attempting to delete RoleEntity with ID: %s".formatted(id));
    }

    @PostRemove
    protected void afterDelete(){
        log.info("RoleEntity deleted.");
    }

    @PostLoad
    protected void afterLoad(){
        log.info("RoleEntity Loaded with ID: %s".formatted(id));
    }
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
