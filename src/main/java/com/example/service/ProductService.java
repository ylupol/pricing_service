package com.example.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Price;
import com.example.domain.Product;
import com.example.repository.PriceRepository;
import com.example.repository.ProductRepository;

@Service
public class ProductService
{
    private ProductRepository productRepository;

    private PriceRepository priceRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
            PriceRepository priceRepository)
    {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    public Collection<Product> findAll()
    {
        return productRepository.findAll();
    }

    public Product findOne(Long id)
    {
        return productRepository.findOne(id);
    }

    public Product save(Product product)
    {
        return productRepository.save(product);
    }

    public void delete(Long id)
    {
        productRepository.delete(id);
    }

    public Optional<Price> createPriceForProduct(Long productId, Price price)
    {
        OffsetDateTime utcDate = price.getDate()
                .atZoneSameInstant(ZoneId.of("UTC")).toOffsetDateTime();

        return Optional.of(productRepository.findOne(productId)).map(
                product -> priceRepository.save(new Price(product, price
                        .getPrice(), utcDate)));
    }

    public Collection<Price> findPrices(Long productId)
    {
        return Optional.of(productRepository.findOne(productId))
                .map(Product::getPrices).orElse(Collections.emptySet());
    }
}
