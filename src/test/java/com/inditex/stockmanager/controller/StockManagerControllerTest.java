package com.inditex.stockmanager.controller;

import com.inditex.stockmanager.constants.StockManagerConstants;
import com.inditex.stockmanager.model.Product;
import com.inditex.stockmanager.service.IStockManagerService;
import com.inditex.stockmanager.utils.BuildUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockManagerController.class)
class StockManagerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IStockManagerService stockManagerService;

    @Test
    void givenAListOfProducts_whenEndpointProductsIsCalled_ThenReturnAJSONBodyWithTheOFProducts() throws Exception {
        Page<Product> pagedProducts = new PageImpl<>(BuildUtils.buildProducts(10), PageRequest.of(0,5), 10);
        when(stockManagerService.findAll(any())).thenReturn(pagedProducts);
        mvc.perform(MockMvcRequestBuilders.get(StockManagerConstants.V1_PRODUCTS_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.content[0].id",is(0)))
                .andExpect(jsonPath("$.pageable.pageSize",is(5)))
                .andExpect(jsonPath("$.numberOfElements",is(10)));
    }

    @Test
    void givenACustomException_whenEndpointProductsIsCalled_ThenReturn400BadRequest() throws Exception {
        when(stockManagerService.findAll(any())).thenThrow(new CustomException("Custom Error"));
        mvc.perform(MockMvcRequestBuilders.get(StockManagerConstants.V1_PRODUCTS_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message",is("Custom Error")))
                .andExpect(jsonPath("$.status",is(400)));
    }

    @Test
    void givenAException_whenEndpointProductsIsCalled_ThenReturn500InternalServerError() throws Exception {
        when(stockManagerService.findAll(any())).thenThrow(new RuntimeException("Internal Error"));
        mvc.perform(MockMvcRequestBuilders.get(StockManagerConstants.V1_PRODUCTS_ENDPOINT).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message",is("Internal Error")))
                .andExpect(jsonPath("$.status",is(500)));
    }
}