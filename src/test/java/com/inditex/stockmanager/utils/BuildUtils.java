package com.inditex.stockmanager.utils;

import com.inditex.stockmanager.model.Product;
import com.inditex.stockmanager.model.Size;
import com.inditex.stockmanager.model.Stock;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuildUtils {

    public static List<Product> buildProducts(int size){
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    Product p = new Product();
                    p.setId(i);
                    p.setSequence(i);
                    var backsoon = i % 2 == 0;
                    p.setSizes(buildSizes(i, backsoon));
                    return p;
                }).collect(Collectors.toList());
    }

    public static Set<Size> buildSizes(int size, boolean backsoon){
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    Size s = new Size();
                    s.setId(i);
                    s.setBacksoon(backsoon);
                    s.setSpecial(i % 2 != 0);
                    Stock stock = new Stock();
                    stock.setSize_id(s);
                    stock.setQuantity(backsoon ? 0 : 1);
                    s.setStock(stock);
                    return s;
                }).collect(Collectors.toSet());
    }
}
