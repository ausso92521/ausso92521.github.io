package tech.leftii.animaltracker.role;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller class containing mapped endpoints for Roles CRUD operations
 */
@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    /**
     * Add Role to Database
     *
     * @param roleDto Contains details of Role to persist
     * @return RoleDto representation of newly saved RoleEntity
     */
    @PostMapping({"", "/"})
    public RoleDto addRole(@RequestBody @Valid RoleDto roleDto){
        return roleService.addRole(roleDto);
    }

    /**
     * Gets Role using supplied ID
     *
     * @param id ID of Role to retrieve from DB
     * @return RoleDto representation of Role retrieved
     */
    @GetMapping({"/{id}","/{id}/"})
    public RoleDto getRoleById(@PathVariable UUID id){
        return roleService.getRoleById(id);
    }

    /**
     * Retrieve list of all Roles from DB
     *
     * @return List of RoleDto
     */
    @GetMapping({"","/"})
    public List<RoleDto> getAllRoles(){
        return roleService.getAllRoles();
    }

    /**
     * Delete Role from the DB
     *
     * @param id ID of the Role to delete supplied by the client
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    public void deleteRole(@PathVariable UUID id){
        roleService.deleteRole(id);
    }
}
