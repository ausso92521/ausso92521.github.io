package tech.leftii.animaltracker.role;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import tech.leftii.animaltracker.shared.DataMapper;

import java.util.List;
import java.util.UUID;

/**
 * Service class that handles business logic for all operations called from endpoints
 * in RoleController
 */
@Service
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class RoleService {
    private final RoleRepository roleRepository;
    private final DataMapper mapper;

    /**
     * Creates and adds a new Role to the DB
     *
     * @param roleDto Role DTO received from the client
     * @return RoleDto representation of the new Role in the DB
     */
    RoleDto addRole(RoleDto roleDto){
        log.info("Adding new Role \n%s".formatted(roleDto));
        return mapper.convert(roleRepository.save(mapper.convert(roleDto)));
    }

    /**
     * Retrieves Role by ID
     *
     * @param id Role ID provided by client
     * @return RoleDto representation of Role Retrieved by ID
     */
    RoleDto getRoleById(UUID id){
        log.info("Retrieving Role with ID: %s".formatted(id));
        return roleRepository.findById(id).map(mapper::convert).orElseThrow(() -> new RoleNotFoundException(id));
    }

    /**
     * Retrieves list of all Roles
     *
     * @return List of RoleDto representing all Roles in DB
     */
    List<RoleDto> getAllRoles(){
        log.info("Retrieve list of all Roles");
        return roleRepository.findAll().stream().map(mapper::convert).toList();
    }

    /**
     * Deletes Role with supplied ID, otherwise does nothing
     *
     * @param id ID of Role to delete supplied by client
     */
    void deleteRole(UUID id){
        log.info("Delete Role with ID: %s".formatted(id));
        roleRepository.deleteById(id);
    }
}
