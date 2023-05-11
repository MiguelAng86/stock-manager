package com.inditex.stockmanager.utils;

import com.inditex.stockmanager.model.Product;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuildUtils {

    public static List<Product> buildProducts(int size){
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    Product p = new Product();
                    p.setId(i);
                    p.setSequence(i);
                    return p;
                }).collect(Collectors.toList());
    }
}
