package com.example.web.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.net.URI;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domain.Price;
import com.example.domain.Product;
import com.example.service.ProductService;

@RestController
@RequestMapping(value = "/api/products", produces = APPLICATION_JSON_VALUE)
public class ProductController
{
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Product> readProducts()
    {
        return productService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product readProduct(@PathVariable Long id)
    {
        return productService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createProduct(@Validated @RequestBody Product input)
    {
        Product saved = productService.save(new Product(input.getName()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id)
    {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(value = "/{productId}/prices", method = RequestMethod.POST)
    public ResponseEntity createPriceForProduct(@PathVariable Long productId,
            @Validated @RequestBody Price price)
    {
        return productService
                .createPriceForProduct(productId, price)
                .map(createdPrice -> {

                    URI location = ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(createdPrice.getId()).toUri();

                    return ResponseEntity.created(location).build();
                }).orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "/{productId}/prices", method = RequestMethod.GET)
    public Collection<Price> readPricesForProduct(@PathVariable Long productId)
    {
        return productService.findPrices(productId).stream()
                .sorted(Comparator.comparing(Price::getId))
                .collect(Collectors.toList());
    }
}
