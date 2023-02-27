package tech.leftii.animaltracker.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.leftii.animaltracker.user.UserRepository;

/**
 * Implementation of UserDetailsService used by SecurityConfig to handle authorizations
 */
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Finds User in database corresponding to the email provided in login form
     *
     * @param email the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException Cannot find the email in the DB or does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserPrincipal(this.userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException(email)));
    }
}
