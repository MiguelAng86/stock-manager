package com.inditex.stockmanager.service;

import com.inditex.stockmanager.controller.DBAccessException;
import com.inditex.stockmanager.model.Product;
import com.inditex.stockmanager.model.Size;
import com.inditex.stockmanager.model.Stock;
import com.inditex.stockmanager.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * StockManagerServiceImpl implements the methods of the IStockManagerService interface to manage the necessary calls to the DB
 */
@Service
public class StockManagerServiceImpl implements IStockManagerService {
    private final IProductRepository productRepository;

    @Autowired
    public StockManagerServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * findAll search all products in DB
     *
     * @param pageable - Contains the pagination parameters
     * @return - The paged list of all products recorded in DB
     */
    @Override
    public Page<Product> findAll(Pageable pageable) {
        try {
            List<Product> products = productRepository.findAll();
            return pagedListOfProducts(products, pageable);
        } catch (DataAccessException e) {
            throw new DBAccessException(e.getMessage());
        }
    }

    /**
     * findProductsWithStock search all products with stock in DB
     *
     * @param pageable - Contains the pagination parameters
     * @return - The paged list of all products with stock recorded in DB
     */

    @Override
    public Page<Product> findProductsWithStock(Pageable pageable) {
        try {
            Predicate<Size> sizeIsBackSoon = Size::isBacksoon;
            Predicate<Size> sizeIsSpecialAndStockGreaterThanZero = (size) -> size.isSpecial() && Optional.ofNullable(size.getStock()).orElse(new Stock()).getQuantity() > 0;
            Predicate<Size> sizeIsNotSpecialAndStockGreaterThanZero = (size) -> !size.isSpecial() && Optional.ofNullable(size.getStock()).orElse(new Stock()).getQuantity() > 0;
            Predicate<Size> sizesWithStock = sizeIsBackSoon.or(sizeIsSpecialAndStockGreaterThanZero.or(sizeIsNotSpecialAndStockGreaterThanZero));
            var productsWithStock = productRepository.findAll().stream()
                    .filter(product -> product.getSizes().stream().filter(sizesWithStock).count() >= 2)
                    .sorted(Comparator.comparing(Product::getSequence))
                    .collect(Collectors.toList());
            return pagedListOfProducts(productsWithStock, pageable);
        } catch (DataAccessException e) {
            throw new DBAccessException(e.getMessage());
        }
    }

    /**
     * pagedListOfProducts applies the PageImpl to page a list of products
     * @param products
     * @param pageable
     * @return - The paged list of all products with stock
     */
    private Page<Product> pagedListOfProducts(List<Product> products, Pageable pageable){
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), products.size());
        return new PageImpl<>(products.subList(start, end), pageable, products.size());
    }
}
