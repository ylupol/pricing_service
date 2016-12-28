package com.example.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>
{
    Collection<Product> findByName(String productName);
}
