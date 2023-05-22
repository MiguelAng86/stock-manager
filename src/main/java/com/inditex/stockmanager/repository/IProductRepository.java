package com.inditex.stockmanager.repository;

import com.inditex.stockmanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Contains all method that extends from JPARespository and JpaSpecificationExecutor to execute queries to the DB
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
}
