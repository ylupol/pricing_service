package com.example.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Price;

public interface PriceRepository extends JpaRepository<Price, Long>
{
    Collection<Price> findByProductName(String productName);
}
