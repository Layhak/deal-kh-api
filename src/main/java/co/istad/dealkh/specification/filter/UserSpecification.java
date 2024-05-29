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

@Data
public class UserSpecification implements Specification<User> {
    private final UserFilter userFilter;
    List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (userFilter.getUsername() != null) {
            Predicate username = criteriaBuilder.like(criteriaBuilder.upper(root.get("username")), "%" + userFilter.getUsername().toUpperCase() + "%");
            predicates.add(username);
        }
        if (userFilter.getEmail() != null) {
            Predicate email = criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + userFilter.getEmail().toUpperCase() + "%");
            predicates.add(email);
        }
        if (userFilter.getPhone() != null) {
            Predicate phone = criteriaBuilder.like(criteriaBuilder.upper(root.get("phone")), "%" + userFilter.getPhone().toUpperCase() + "%");
            predicates.add(phone);
        }
        if (userFilter.getRole() != null) {
            Predicate role = criteriaBuilder.like(criteriaBuilder.upper(root.get("role").get("name")), "%" + userFilter.getRole().toUpperCase() + "%");
            predicates.add(role);
        }
        if (userFilter.getGender() != null) {
            Predicate gender = criteriaBuilder.like(criteriaBuilder.upper(root.get("gender").get("name")), "%" + userFilter.getGender().toUpperCase() + "%");
        }
        if (userFilter.getStatus() != null) {
            Predicate status = criteriaBuilder.like(criteriaBuilder.upper(root.get("status").get("name")), "%" + userFilter.getStatus().toUpperCase() + "%");
            predicates.add(status);
        }
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
