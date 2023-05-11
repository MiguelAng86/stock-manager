package com.inditex.stockmanager.repository;

import com.inditex.stockmanager.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * ProductSpecification contains the Specification methods with the different queries executed to the DB
 */
@Component
public class ProductSpecification {

    /**
     * queryProductsWithStock method build the query to filter the products with stocks
     * @return - The specification to filter the products with stocks
     */
    public Specification<Product> queryProductsWithStock(){
        return (root, query, criteriaBuilder) ->
            query.where(
                criteriaBuilder.or(
                    criteriaBuilder.equal(root.get("sizes").get("backsoon"), true),
                    criteriaBuilder.and(
                            criteriaBuilder.and(criteriaBuilder.isTrue(root.get("sizes").get("special")), criteriaBuilder.greaterThan(root.get("sizes").get("stock").get("quantity"), 0)),
                            criteriaBuilder.and(criteriaBuilder.isFalse(root.get("sizes").get("special")), criteriaBuilder.greaterThan(root.get("sizes").get("stock").get("quantity"), 0))
                    )
                )
            )
            .groupBy(root.get("id"))
            .having(criteriaBuilder.equal(criteriaBuilder.count(root.get("id")),2L))
            .orderBy(criteriaBuilder.asc(root.get("sequence"))).getRestriction();
    };
}
