package tech.leftii.animaltracker.user;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.leftii.animaltracker.shared.DataMapper;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * Service class for User.
 * This class performs all business logic and invokes UserRepository for accessing or manipulating data in the
 * persistence layer.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final DataMapper mapper;
    private final PasswordEncoder encoder;

    /**
     * Convenience method for retrieving user that sent request using their Authentication object
     * @param auth Authentication object from user
     * @return ResponseUserDto
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    ResponseUserDto getSelf(Authentication auth){
        return userRepository.findByEmailIgnoreCase(auth.getName()).map(mapper::convert).orElseThrow(() -> new UserNotFoundException(auth.getName()));
    }

    /**
     * Add a new User entity to the database. Must not be logged in.
     * @param newUserDto DTO containing the information for the new user
     * @return ResponseUserDto representing the newly created user entity
     */
    @PreAuthorize("isAnonymous()")
    ResponseUserDto addUser(NewUserDto newUserDto) {
        log.info("Create new user: %s".formatted(newUserDto.email()));
        return mapper.convert(userRepository.save(mapper.convert(newUserDto, encoder)));
    }

    /**
     * Get list of all users from database, Requires SCOPE_USER
     * If user has SCOPE_ADMIN, return a list of all users, otherwise, only return the logged-in user
     * @return list of ResponseUserDto representing all user entities in database
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    List<ResponseUserDto> getAllUsers(Authentication auth) {
        // Check if user is an admin
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("SCOPE_ADMIN"))) {
            // If admin, return list of users
            log.info("SCOPE_ADMIN, retrieve all Users");
            return userRepository.findAll().stream().map(mapper::convert).toList();
        }
        // If not admin, return only logged-in user as a list
        log.info("SCOPE_USER, retrieve List containing only self");
        return userRepository.findByEmailIgnoreCase(auth.getName()).stream().map(mapper::convert).toList();
    }

    /**
     * Get specific user from database using id
     * @param id used to lookup user
     * @return ResponseUserDto representing user entity found
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    ResponseUserDto getUserById(UUID id) {
        log.info("Retrieve User by ID: %s".formatted(id));
        return userRepository.findById(id).map(mapper::convert).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    /**
     * Update user entity in database with values from DTO if they are new and not null
     * @param userDto Dto supplied by the client with updated values
     * @return ResponseUserDto that reflects the updated values
     * @apiNote Requires ADMIN permissions
     */
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    ResponseUserDto updateUserById(UUID targetId, UpdateUserDto userDto) {
        UserEntity user = userRepository.findById(targetId).orElseThrow(() -> new UserNotFoundException(targetId.toString()));
        user = mapper.convert(userDto, user, encoder);
        log.info("Update User at ID: %s".formatted(targetId));
        return mapper.convert(userRepository.save(user));
    }

    /**
     * Allows non-admin user to update their own user entity data
     * @param principal Principal object of logged-in user who sent the request
     * @param userDto   Dto based on the information the user would like to update to
     * @return ResponseUserDto representation of user entity with saved updates
     */
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    ResponseUserDto updateUserSelf(Principal principal, UpdateUserDto userDto, HttpServletResponse response) {
        UserEntity user = userRepository.findByEmailIgnoreCase(principal.getName()).orElseThrow(() ->
                new UserNotFoundException(principal.getName()));
        // Make sure password submitted matches prior to updating entity
        if (encoder.matches(userDto.currentPassword(), user.getPassword())) {
            log.info("Passwords match, proceeding with updateUserSelf");
            // Check if password or email are being updated
            // If they are, expire the jwt_token cookie so user has to reauthenticate
            // otherwise, the jwt token will no longer work properly for authorization on requests
            if (!encoder.matches(userDto.newPassword(), user.getPassword()) || !userDto.email().equals(user.getEmail())) {
                log.info("Password or Username changed, expiring JWT cookie");
                user = mapper.convert(userDto, user, encoder);
                Cookie cookie = new Cookie("jwt_token", null);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge(0);
                cookie.setPath("/api/v1");
                response.addCookie(cookie);
            }
            log.info("Updating User with ID: %s".formatted(user.getId()));
            return mapper.convert(userRepository.save(user));
        } else {
            throw new IncorrectPasswordException();
        }

    }

    /**
     * Deletes User from database, if not found, does nothing
     *
     * @param targetId Used to identify the User to be deleted
     */
    @PreAuthorize("hasAuthority('SCOPE__ADMIN')")
    void deleteUser(UUID targetId) {
        log.info("Delete User with ID: %s".formatted(targetId));
        userRepository.deleteById(targetId);
    }
}
