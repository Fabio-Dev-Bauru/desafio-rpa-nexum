package com.desafio_rpa.nexum.pages;

import com.desafio_rpa.nexum.interfaces.IPageAction;
import com.desafio_rpa.nexum.models.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CasasBahiaPage implements IPageAction {
    private static final String URL = "https://casasbahia.com.br/";

    @Override
    public List<Product> getProducts(WebDriver driver, String productName) {
        List<Product> products = new ArrayList<>();
        try {
            driver.get(URL + productName + "/b");
            System.out.println("Iniciando coleta CasasBahia");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 500)");

            List<WebElement> productsSearched =
                    wait.until(ExpectedConditions.
                            visibilityOfAllElementsLocatedBy
                                    (By.className("css-1enexmx")));

            for (WebElement product : productsSearched) {
                String description = product.findElement(By.xpath("//h3[@class='product-card__title']/a")).getAttribute("title");
                String price = product.findElement(By.className("product-card__highlight-price")).getText();
                String link = product.findElement(By.className("dsvia-link-overlay")).getAttribute("href");

                if (description.contains("Console")) {
                    products.add(
                            new Product(description,
                                    Double.parseDouble(price
                                                        .replace("R$ ", "")
                                                        .replace(".", "")
                                                        .replace(",", "."))
                                    , link));
                }
            }

        } catch (TimeoutException e) {
            System.out.println("Houve um problema ao realizar a captura de preços no mercado livre." + e.getMessage());
        }

        products.sort(Comparator.comparingDouble(p -> p.price));
        return products.subList(0, Math.min(products.size(), 3));
    }
}
