package com.desafio_rpa.nexum.models;

import java.math.BigDecimal;

public class Product {
    public String description;
    public Double price;
    public String link;

    public Product(String description, Double price, String link) {
        this.description = description;
        this.price = price;
        this.link = link;
    }
}
