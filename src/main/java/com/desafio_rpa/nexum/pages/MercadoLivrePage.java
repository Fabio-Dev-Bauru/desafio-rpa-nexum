package com.desafio_rpa.nexum.pages;

import com.desafio_rpa.nexum.interfaces.IPageAction;
import com.desafio_rpa.nexum.models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class MercadoLivrePage implements IPageAction {

    private static final String URL = "https://lista.mercadolivre.com.br/";

    @Override
    public List<Product> getProducts(WebDriver driver, String productName) {
        List<Product> products = new ArrayList<>();
        try {
            driver.get(URL + productName);
            System.out.println("Iniciando coleta MercadoLivre");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            List<WebElement> productsSearched =
                    wait.until(ExpectedConditions.
                            visibilityOfAllElementsLocatedBy
                                    (By.xpath("//*[@id='root-app']/div/div[3]/section/ol/li")));

            for (WebElement product : productsSearched) {
                String description = product.findElement(By.className("ui-search-item__title")).getText();
                String price = product.findElement(By.className("andes-money-amount__fraction")).getText();
                String link = product.findElement(By.className("ui-search-link")).getAttribute("href");

                if (description.contains(productName)) {
                    products.add(new Product(description, Double.parseDouble(price), link));
                }
            }

        } catch (TimeoutException e) {
            System.out.println("Houve um problema ao realizar a captura de preços no mercado livre." + e.getMessage());
        }

        products.sort(Comparator.comparingDouble(p -> p.price));
        return products.subList(0, Math.min(products.size(), 3));
    }
}
