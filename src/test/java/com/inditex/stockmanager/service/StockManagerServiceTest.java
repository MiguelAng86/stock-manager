package com.inditex.stockmanager.service;

import com.inditex.stockmanager.controller.DBAccessException;
import com.inditex.stockmanager.model.Product;
import com.inditex.stockmanager.repository.IProductRepository;
import com.inditex.stockmanager.utils.BuildUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockManagerServiceTest {

    @InjectMocks
    StockManagerServiceImpl stockManagerService;

    @Mock
    IProductRepository productRepository;


    @Test
    public void givenAListOfProducts_whenRepositoryFindAllMethodIsCalled_thenReturnAPageableListOfProducts() {
        when(productRepository.findAll()).thenReturn(BuildUtils.buildProducts(10));
        Page<Product> result = stockManagerService.findAll(Pageable.ofSize(10));
        Assertions.assertEquals(10, result.getTotalElements());
    }

    @Test
    public void givenAListOfProducts_whenRepositoryFindAllMethodIsCalled_thenReturnAPageableListOfProductsWithStock() {
        when(productRepository.findAll()).thenReturn(BuildUtils.buildProducts(10));
        Page<Product> result = stockManagerService.findProductsWithStock(Pageable.ofSize(10));
        Assertions.assertEquals(8, result.getTotalElements());
    }

    @Test
    public void givenAException_whenRepositoryFindAllMethodIsCalled_thenReturnAPageableListOfProducts(){
        when(productRepository.findAll()).thenThrow(new DataAccessException("DB connection failed"){});
        assertThrows(DBAccessException.class, () -> stockManagerService.findProductsWithStock(Pageable.unpaged()));
    }
}