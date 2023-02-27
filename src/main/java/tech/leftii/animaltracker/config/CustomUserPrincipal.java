package tech.leftii.animaltracker.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.leftii.animaltracker.role.RoleEntity;
import tech.leftii.animaltracker.user.UserEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Custom User Principal class that creates UserDetails object based on the authenticated user.
 * This class is used in the creation of JWT tokens and mapping Roles to JWT Scopes
 */
public class CustomUserPrincipal implements UserDetails {
    private final UserEntity user;

    public CustomUserPrincipal(UserEntity user) {
        this.user = user;
    }

    /**
     * Returns Collection of GrantedAuthorities for use in mapping to JWT Scopes
     *
     * @return list of granted authorities (roles)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        List<RoleEntity> roles = user.getRoles().stream().toList();
        roles.forEach(role -> grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName())));
        return grantedAuthorityList;
    }

    /**
     * Get user's id UUID
     *
     * @return user ID
     */
    public UUID getId() {
        return user.getId();
    }

    /**
     * Get user's password
     *
     * @return encrypted string password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Get User's Username (email address)
     *
     * @return user's email address
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Get expiration status on account
     *
     * @return true if not expired, false if expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Get lock status of User account
     *
     * @return true if not locked, false if locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Get expiration status of credentials (password)
     *
     * @return true if not expired, false if expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Get expiration status of User account
     *
     * @return true if enabled, false if disabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
