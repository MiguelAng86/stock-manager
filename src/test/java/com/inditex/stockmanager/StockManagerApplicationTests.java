package com.inditex.stockmanager;

import com.inditex.stockmanager.constants.StockManagerConstants;
import com.inditex.stockmanager.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StockManagerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private IProductRepository productRepository;

    @Test
    void givenAFiveProductsRecordedInDB_whenEndpointProductsIsCalled_thenReturn200OKWithTheListOfProductsInJSON() throws Exception {
        mockMvc.perform(get(StockManagerConstants.V1_PRODUCTS_ENDPOINT)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.pageable.pageSize",is(5)))
                .andExpect(jsonPath("$.numberOfElements",is(5)));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void givenAFiveProductsRecordedInDB_whenEndpointProductsIsCalledWithPageQueryParameters_thenReturn200OKWithTheListOfProductsOfASpecificPage() throws Exception {
        mockMvc.perform(get(StockManagerConstants.V1_PRODUCTS_ENDPOINT+"?page=2&size=2")
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.pageable.pageSize",is(2)))
                .andExpect(jsonPath("$.totalPages",is(3)))
                .andExpect(jsonPath("$.numberOfElements",is(1)))
                .andExpect(jsonPath("$.totalElements",is(5)));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void givenAFiveProductsRecordedInDB_whenEndpointProductsStockIsCalled_thenReturn200OKWithTheListOfProductsWithStockInJSON() throws Exception {
        mockMvc.perform(get(StockManagerConstants.V1_PRODUCTS_STOCK_ENDPOINT)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content.[*].id", containsInRelativeOrder(5,1,3)))
                .andExpect(jsonPath("$.pageable.pageSize",is(5)))
                .andExpect(jsonPath("$.totalPages",is(1)))
                .andExpect(jsonPath("$.numberOfElements",is(3)))
                .andExpect(jsonPath("$.totalElements",is(3)));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void givenAFiveProductsRecordedInDB_whenEndpointProductsIsCalled_thenAExceptionIsThrew_AndReturn500InternalServerWithTheErrorMessagenInJSON() throws Exception {
        when(productRepository.findAll()).thenThrow(new DataAccessException("Database connection failed") {});
        mockMvc.perform(get(StockManagerConstants.V1_PRODUCTS_ENDPOINT)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("Database call error: Database connection failed")))
                .andExpect(jsonPath("$.status", is(500)));
    }

}
