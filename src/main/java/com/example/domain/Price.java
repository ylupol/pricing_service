package com.example.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Price
{
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Product product;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private Date date;

    private double price;

    public Price()
    {
    }

    public Price(Product product, double price, Date date)
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

    public Date getDate()
    {
        return date;
    }

    public double getPrice()
    {
        return price;
    }
}
