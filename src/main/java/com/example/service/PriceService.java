package com.example.service;

import com.example.domain.Price;
import com.example.domain.Product;
import com.example.domain.ProductPrice;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductPrice> getProductPricesByDate(OffsetDateTime date) {
        List<Product> products = productRepository.findAll();

        List<ProductPrice> productPrices = new ArrayList<>();
        for (Product product : products) {
            product.getPrices().stream()
                    .filter(price -> price.getDate().isBefore(date))
                    .max(Comparator.comparing(Price::getDate))
                    .map((price) -> productPrices.add(new ProductPrice(product.getName(), price.getPrice())));

        }
        return productPrices;
    }
}
