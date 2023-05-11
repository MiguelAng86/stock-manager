package com.inditex.stockmanager.service;

import com.inditex.stockmanager.controller.DBAccessException;
import com.inditex.stockmanager.model.Product;
import com.inditex.stockmanager.repository.IProductRepository;
import com.inditex.stockmanager.repository.ProductSpecification;
import com.inditex.stockmanager.utils.BuildUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockManagerServiceTest {

    @InjectMocks
    StockManagerServiceImpl stockManagerService;

    @Mock
    IProductRepository productRepository;

    @Mock
    ProductSpecification productSpecification;

    @Test
    public void givenAListOfProducts_whenRepositoryFindAllMethodIsCalled_thenReturnAPageableListOfProducts() {
        Page<Product> pagedProducts = new PageImpl<>(BuildUtils.buildProducts(5));
        ProductSpecification productSpecification1 = new ProductSpecification();
        when(productSpecification.queryProductsWithStock()).thenReturn(productSpecification1.queryProductsWithStock());
        when(productRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(pagedProducts);
        Page<Product> result = stockManagerService.findProductsWithStock(Pageable.unpaged());
        Assertions.assertEquals(pagedProducts, result);
    }

    @Test
    public void givenAException_whenRepositoryFindAllMethodIsCalled_thenReturnAPageableListOfProducts(){
        ProductSpecification productSpecification1 = new ProductSpecification();
        when(productSpecification.queryProductsWithStock()).thenReturn(productSpecification1.queryProductsWithStock());
        when(productRepository.findAll(any(Specification.class),any(Pageable.class))).thenThrow(new DataAccessException("DB connection failed"){});
        assertThrows(DBAccessException.class, () -> stockManagerService.findProductsWithStock(Pageable.unpaged()));
    }
}