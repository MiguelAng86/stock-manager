package com.inditex.stockmanager.repository;

import com.inditex.stockmanager.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IProductRepositoryTest {

    @Autowired
    private IProductRepository productRepository;

    @Test
    public void givenFiveProductsRecordedInDB_whenRepositoryFindAllIsCalled_thenReturnAPageableListOfFiveProducts(){
        Page <Product> products = productRepository.findAll(PageRequest.of(0, 5));
        assertEquals(5, products.getTotalElements());
    }

}