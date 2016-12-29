package com.example.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Product
{
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Price> prices;

    public Product()
    {
    }

    public Product(String name)
    {
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public Set<Price> getPrices()
    {
        return prices;
    }
}
