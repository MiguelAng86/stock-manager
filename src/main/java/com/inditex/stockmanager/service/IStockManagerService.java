package com.inditex.stockmanager.service;

import com.inditex.stockmanager.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * IStockManagerService contains the methods that must be implemented for the calls to DB
 */
public interface IStockManagerService {

    Page<Product> findAll(Pageable pageable);
    Page<Product> findProductsWithStock(Pageable pageable);
}
