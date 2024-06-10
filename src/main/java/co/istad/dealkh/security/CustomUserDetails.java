package co.istad.dealkh.security;

import co.istad.dealkh.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CustomUserDetails is a custom implementation of {@link UserDetails} that represents the user details for authentication.
 * It includes additional fields and methods to accommodate the application's specific requirements.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Getter} - Generates getters for all fields.</li>
 * <li>{@link Setter} - Generates setters for all fields.</li>
 * <li>{@link NoArgsConstructor} - Generates a no-argument constructor.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;
    private Long userId;  // Add this field

    /**
     * Constructs a new CustomUserDetails with the specified user.
     * Initializes the userId field from the User object.
     *
     * @param user the user object containing user details
     */
    public CustomUserDetails(User user) {
        this.user = user;
        this.userId = user.getId();  // Initialize the userId here
    }

    /**
     * Returns the authorities granted to the user. Each authority is a privilege granted to the user.
     *
     * @return the authorities granted to the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(role::getAuthority);
            role.getAuthorities().forEach(authority ->
            {
                authorities.add(authority::getName);
            });
        });
        return authorities;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return true if the user's account is non-expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return !user.isAccountExpired();
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return true if the user is not locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return !user.isAccountLocked();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     *
     * @return true if the user's credentials are non-expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isCredentialsExpired();
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return !user.isBlocked();
    }


}
