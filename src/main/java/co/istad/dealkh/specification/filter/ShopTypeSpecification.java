package co.istad.dealkh.specification.filter;

import co.istad.dealkh.domain.ShopType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ShopTypeSpecification(ShopTypeFilter shopTypeFilter) implements Specification<ShopType> {

    @Override
    public Predicate toPredicate(Root<ShopType> shopType, CriteriaQuery<?> query, CriteriaBuilder criteria) {
        List<Predicate> predicates = new ArrayList<>();

        if (shopTypeFilter.getName() != null && !shopTypeFilter.getName().isEmpty()) {
            Predicate name = criteria.like(criteria.upper(shopType.get("name")), "%" + shopTypeFilter.getName().toUpperCase() + "%");
            predicates.add(name);
        }

        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
