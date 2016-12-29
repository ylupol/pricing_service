package com.example.domain;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.service.CustomOffsetDateTimeDeserializer;
import com.example.service.CustomOffsetDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
public class Price
{
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Product product;

    @JsonSerialize(using = CustomOffsetDateTimeSerializer.class)
    @JsonDeserialize(using = CustomOffsetDateTimeDeserializer.class)
    private OffsetDateTime date;

    private double price;

    public Price()
    {
    }

    public Price(Product product, double price, OffsetDateTime date)
    {
        this.product = product;
        this.date = date;
        this.price = price;
    }

    public Long getId()
    {
        return id;
    }

    public Product getProduct()
    {
        return product;
    }

    public OffsetDateTime getDate()
    {
        return date;
    }

    public double getPrice()
    {
        return price;
    }
}
