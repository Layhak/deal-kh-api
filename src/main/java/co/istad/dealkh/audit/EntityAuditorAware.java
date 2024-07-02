package co.istad.dealkh.audit;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * EntityAuditorAware is a component that implements {@link AuditorAware} to provide
 * the current auditor (username) for auditing purposes. This class is used by
 * Spring Data JPA to automatically populate the `createdBy` and `lastModifiedBy`
 * fields of the {@link Auditable} entities.
 *
 * <p>This class retrieves the current user from the security context, which is typically
 * managed by Spring Security.</p>
 *
 * <p>The {@code getCurrentAuditor} method is overridden to return the current user's username
 * if available, otherwise it returns "Error".</p>
 *
 * <p>Annotations used:</p>
 * <ul>
 *   <li>{@link Component} - Indicates that this class is a Spring component.</li>
 *   <li>{@link NotNull} - Indicates that the return value of the method cannot be null.</li>
 * </ul>
 */
@Component
public class EntityAuditorAware implements AuditorAware<String> {

    /**
     * Retrieves the current auditor (username) from the security context.
     *
     * @return an {@link Optional} containing the username of the current auditor,
     * or "admin" if the username is not available.
     */
    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.ofNullable(authentication.getName());
        }
        return Optional.of("admin");
    }
}
