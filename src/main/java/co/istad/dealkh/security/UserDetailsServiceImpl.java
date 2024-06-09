package co.istad.dealkh.security;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl is a service that provides user details for authentication purposes.
 * It implements the {@link UserDetailsService} interface and overrides the method to load user details by username or email.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service and a candidate for component scanning and dependency injection.</li>
 * </ul>
 * </p>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserDetailsServiceImpl with the specified user repository.
     *
     * @param userRepository the user repository to use for retrieving user details
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads the user details by username. This method is currently not implemented and will throw a {@link UsernameNotFoundException}.
     *
     * @param username the username of the user to load
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    /**
     * Loads the user details by email.
     *
     * @param email the email of the user to load
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new CustomUserDetails(user);
    }
}
