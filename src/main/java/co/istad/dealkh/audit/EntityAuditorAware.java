package co.istad.dealkh.audit;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityAuditorAware implements AuditorAware<String> {

    //* This method is used to get the current auditor of the entity
    //* When we have security we can get the current user from the security context or from the request

    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        //get the current user from the security context
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext.getAuthentication() != null) {
            return Optional.of(securityContext.getAuthentication().getName());
        }
        return Optional.of("Error");
    }
}
