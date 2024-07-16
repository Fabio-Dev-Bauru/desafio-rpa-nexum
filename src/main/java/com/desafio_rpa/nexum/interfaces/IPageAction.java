package com.desafio_rpa.nexum.interfaces;

import com.desafio_rpa.nexum.models.Product;
import org.openqa.selenium.WebDriver;

import java.util.List;

public interface IPageAction {
    public List<Product> getProducts(WebDriver driver, String productName);
}
