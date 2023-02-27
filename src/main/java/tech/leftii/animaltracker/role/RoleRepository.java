package tech.leftii.animaltracker.role;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface that exposes database access methods to the service layer
 */
@Repository
public interface RoleRepository extends ListCrudRepository<RoleEntity, UUID> {
    RoleEntity findRoleByName(String name);
}
