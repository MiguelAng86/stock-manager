package com.inditex.stockmanager.controller;

import com.inditex.stockmanager.service.IStockManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("products")
@RequestMapping("/v1")
@Tag(name = "Stock Manager", description = "Stock manager for inditex")
public class StockManagerController {

    private final IStockManagerService stockManagerService;

    @Autowired
    public StockManagerController(IStockManagerService stockManagerService){
        this.stockManagerService = stockManagerService;
    }

    /**
     * findAll method publish /products endpoint
     * @param pageable - Received the query parameters for pagination
     * @return - Paged List of all products
     */
    @Operation(summary = "Get List of products", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/products", produces="application/json")
    public ResponseEntity<Object> findAll(@ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(stockManagerService.findAll(pageable), HttpStatus.OK);
    }

    /**
     * findProductsWithStock method publish /products/stock endpoint
     * @param pageable - Received the query parameters for pagination
     * @return - Paged List of all products with stock
     */
    @Operation(summary = "Get List of products with stock", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/products/stock", produces="application/json")
    public ResponseEntity<Object> findProductsWithStock(@ParameterObject @PageableDefault(size = 5) Pageable pageable) {
        return new ResponseEntity<>(stockManagerService.findProductsWithStock(pageable), HttpStatus.OK);
    }
}

