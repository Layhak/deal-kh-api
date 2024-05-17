package co.istad.deal_kh.dealkhapi.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityAuditorAware implements AuditorAware<String> {

    //* This method is used to get the current auditor of the entity
    //* When we have security we can get the current user from the security context
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Layhak");
    }
}
