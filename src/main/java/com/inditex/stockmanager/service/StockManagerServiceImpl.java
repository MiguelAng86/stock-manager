package com.inditex.stockmanager.service;

import com.inditex.stockmanager.controller.DBAccessException;
import com.inditex.stockmanager.model.Product;
import com.inditex.stockmanager.repository.IProductRepository;
import com.inditex.stockmanager.repository.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * StockManagerServiceImpl implements the methods of the IStockManagerService interface to manage the necessary calls to the DB
 */
@Service
public class StockManagerServiceImpl implements IStockManagerService {
    private final IProductRepository productRepository;

    private final ProductSpecification productSpecification;

    @Autowired
    public StockManagerServiceImpl(IProductRepository productRepository, ProductSpecification productSpecification){
        this.productRepository = productRepository;
        this.productSpecification = productSpecification;
    }

    /**
     * findAll search all products in DB
     * @param pageable - Contains the pagination parameters
     * @return - The paged list of all products recorded in DB
     */
    @Override
    public Page<Product> findAll(Pageable pageable) {
        try {
            return productRepository.findAll(pageable);
        } catch (DataAccessException e){
            throw new DBAccessException(e.getMessage());
        }
    }

    /**
     * findProductsWithStock search all products with in DB
     * @param pageable - Contains the pagination parameters
     * @return - The paged list of all products with stock recorded in DB
     */

    @Override
    public Page<Product> findProductsWithStock(Pageable pageable) {
        try {
            return productRepository.findAll(productSpecification.queryProductsWithStock(), pageable);
        } catch (DataAccessException e){
            throw new DBAccessException(e.getMessage());
        }
    }
}
