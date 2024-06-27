package co.istad.dealkh.specification.filter;

import co.istad.dealkh.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public record UserSpecification(UserFilter userFilter) implements Specification<User> {
    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        addPredicateIfNotNull(predicates, userFilter.getUsername(), value ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("username")), "%" + value.toUpperCase() + "%"));

        addPredicateIfNotNull(predicates, userFilter.getEmail(), value ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + value.toUpperCase() + "%"));

        addPredicateIfNotNull(predicates, userFilter.getPhone(), value ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("phone")), "%" + value.toUpperCase() + "%"));

        addPredicateIfNotNull(predicates, userFilter.getGender(), value ->
                criteriaBuilder.equal(root.get("gender"), value));

        addStatusPredicate(predicates, userFilter.getStatus(), root, criteriaBuilder);
        addRolePredicate(predicates, userFilter.getRole(), root, criteriaBuilder);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void addPredicateIfNotNull(List<Predicate> predicates, String value, Function<String, Predicate> predicateFunction) {
        if (value != null && !value.trim().isEmpty()) {
            predicates.add(predicateFunction.apply(value));
        }
    }

    private void addStatusPredicate(List<Predicate> predicates, String status, Root<User> root, CriteriaBuilder criteriaBuilder) {
        if (status != null) {
            if (status.equalsIgnoreCase("enabled") || status.equalsIgnoreCase("enable")) {
                predicates.add(criteriaBuilder.isFalse(root.get("isDisabled")));
            } else if (status.equalsIgnoreCase("disabled") || status.equalsIgnoreCase("disable")) {
                predicates.add(criteriaBuilder.isTrue(root.get("isDisabled")));
            }
        }
    }

    private void addRolePredicate(List<Predicate> predicates, String role, Root<User> root, CriteriaBuilder criteriaBuilder) {
        if (role != null && !role.trim().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.join("roles").get("name"), role));
        }
    }
}
