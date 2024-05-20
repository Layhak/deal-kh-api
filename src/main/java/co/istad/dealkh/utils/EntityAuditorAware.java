package co.istad.dealkh.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityAuditorAware implements AuditorAware<String> {

    //* This method is used to get the current auditor of the entity
    //* When we have security we can get the current user from the security context
    @NotNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Layhak");
    }
}
