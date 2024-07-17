package co.istad.dealkh.specification.filter;

import co.istad.dealkh.domain.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public record ProductSpecification(ProductFilter productFilter) implements Specification<Product> {

    @Override
    public Predicate toPredicate(Root<Product> product, CriteriaQuery<?> query, CriteriaBuilder criteria) {
        List<Predicate> predicates = new ArrayList<>();

        if (productFilter.getName() != null) {
            Predicate name = criteria.like(criteria.upper(product.get("name")), "%" + productFilter.getName().toUpperCase() + "%");
            predicates.add(name);
        }

        if (productFilter.getCategorySlug() != null) {
            Predicate category = criteria.like(criteria.upper(product.join("category", JoinType.LEFT).get("slug")), "%" + productFilter.getCategorySlug().toUpperCase() + "%");
            predicates.add(category);
        }

        if (productFilter.getDiscountValue() > 0) {
            Predicate discountValue = criteria.equal(product.join("discount", JoinType.LEFT).get("discountValue"), productFilter.getDiscountValue());
            predicates.add(discountValue);
        }


        if (productFilter.getRatingAvg() >= 0 && productFilter.getRatingAvg() <= 5) {
            Predicate ratingAvg = criteria.equal(product.get("ratingAvg"), productFilter.getRatingAvg());
            predicates.add(ratingAvg);
        }

        if (productFilter.getDiscountType() != null) {
            Predicate discountType = criteria.equal(criteria.upper(product.join("discount", JoinType.LEFT).get("discountType").get("name")), productFilter.getDiscountType().toUpperCase());
            predicates.add(discountType);
        }

        if (productFilter.getDiscountTypeSlug() != null) {
            Predicate discountTypeSlug = criteria.equal(criteria.upper(product.join("discount", JoinType.LEFT).get("discountTypeSlug").get("name")), productFilter.getDiscountTypeSlug().toUpperCase());
            predicates.add(discountTypeSlug);
        }


        if (productFilter.getShop() != null) {
            Predicate shop = criteria.like(criteria.upper(product.join("shop", JoinType.LEFT).get("name")), "%" + productFilter.getShop().toUpperCase() + "%");
            predicates.add(shop);
        }

        return criteria.and(predicates.toArray(Predicate[]::new));
    }
}
