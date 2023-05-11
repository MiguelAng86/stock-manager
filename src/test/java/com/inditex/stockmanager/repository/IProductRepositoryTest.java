package com.inditex.stockmanager.repository;

import com.inditex.stockmanager.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(ProductSpecification.class)
class IProductRepositoryTest {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ProductSpecification productSpecification;

    @Test
    public void givenFiveProductsRecordedInDB_whenRepositoryFindAllIsCalled_thenReturnAPageableListOfFiveProducts(){
        Page <Product> products = productRepository.findAll(PageRequest.of(0, 5));
        assertEquals(5, products.getTotalElements());
    }

    @Test
    public void givenFiveProductsRecordedInDB_whenRepositoryFindAllIsCalledWithProductsWithStockSpecification_thenReturnAPageableListOfProductsWithStock_AndSortedBySequence(){
        Page <Product> products = productRepository.findAll(productSpecification.queryProductsWithStock(), PageRequest.of(0, 5));
        assertEquals(3, products.getTotalElements());
        assertEquals(5, products.getContent().get(0).getId());
        assertEquals(1, products.getContent().get(1).getId());
        assertEquals(3, products.getContent().get(2).getId());
    }

}