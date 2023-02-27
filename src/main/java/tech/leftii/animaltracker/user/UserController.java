package tech.leftii.animaltracker.user;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


/**
 * Controller class that exposes REST endpoints for operations on User Entity
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    // Injection of UserService to facilitate business logic of requests
    private final UserService userService;

    /**
     * Convenience endpoint - Allows client to send authorized request to retrieve their own user information
     * @param auth Authentication object
     * @return ResponseUserDto
     */
    @GetMapping({"/self", "/self/"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDto getUserSelf(Authentication auth){
        return userService.getSelf(auth);
    }

    /**
     * Rest Get endpoint for requesting a list of all users
     * Requester must have ADMIN role
     *
     * @return List of ResponseUserDto representing all Users in database
     */
    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseUserDto> getUserList(Authentication auth) {
        return userService.getAllUsers(auth);
    }

    /**
     * Rest Get endpoint for requesting a specific user from the database
     *
     * @param id User id to match in database
     * @return ResponseUserDto from matching User with id from database
     */
    @GetMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDto getUser(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    /**
     * Rest Post request for creating a new user in the database
     *
     * @param newUserDto JSON object in request body with NewUserDto variables
     * @return ResponseUserDto of the newly created entity in the database
     */
    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUserDto addUser(@RequestBody @Valid NewUserDto newUserDto) {
        return userService.addUser(newUserDto);
    }

    /**
     * Rest Patch request for updating some or all editable members of a user in the database. Requires SCOPE_ADMIN
     *
     * @param id            ID of the User entity to attempt to update
     * @param updateUserDto JSON object in request body with new values to update user entity with in database
     *                      Mapper ignores null parameters so only parameters with values update in the entity
     * @return ResponseUserDto representation of the entity after update
     */
    @PatchMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDto updateUserById(@PathVariable UUID id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        return userService.updateUserById(id, updateUserDto);
    }

    /**
     * Rest PATCH request to update user's own information. Requires SCOPE_USER
     *
     * @param principal     Gives us information of the logged-in user sending the request
     *                      Can be used to verify user is updating their own information
     * @param updateUserDto JSON object in request body with new values to update user entity with in database
     *                      All members are wrapped in Optional wrapper class and can be omitted if the value does not
     *                      need to be updated in the user entity of the database
     * @return ResponseUserDto representation of the entity after update
     */
    @PatchMapping({"/self", "/self/"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDto updateUserSelf(Principal principal, @RequestBody @Valid UpdateUserDto updateUserDto,
                                          HttpServletResponse response) {
        return userService.updateUserSelf(principal, updateUserDto, response);
    }

    /**
     * Rest DELETE request for removing a User entity from the database
     * If the User entity does not exist, this will do nothing
     *
     * @param id ID of the User entity ro delete from the database
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

}
