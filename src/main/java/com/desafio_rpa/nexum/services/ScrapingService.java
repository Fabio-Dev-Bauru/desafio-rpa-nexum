package com.desafio_rpa.nexum.services;

import com.desafio_rpa.nexum.helpers.ExcelGenerator;
import com.desafio_rpa.nexum.models.Product;
import com.desafio_rpa.nexum.pages.CasasBahiaPage;
import com.desafio_rpa.nexum.pages.MercadoLivrePage;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ScrapingService {

    private final WebDriver driver;
    private static final String PRODUCT_NAME = "Xbox Series S";
    private static final String EXCEL_FILE = "c:/temp/cotacaoXbox.xlsx";

    @PostConstruct
    void postConstruct() throws InterruptedException {
        runScrape(PRODUCT_NAME);
    }

    public void runScrape(final String productName) throws InterruptedException {
        CasasBahiaPage casasBahiaPage = new CasasBahiaPage();
        MercadoLivrePage mercadoLivrePage = new MercadoLivrePage();

        List<Product> productsMercadoLivre = mercadoLivrePage.getProducts(driver, productName);
        List<Product> productsCasasBahia = casasBahiaPage.getProducts(driver, productName);

        if (!productsCasasBahia.isEmpty() && !productsMercadoLivre.isEmpty()) {
            List<List<String>> mlSheet = new ArrayList<List<String>>();
            mlSheet.add(Arrays.asList("Descrição", "Preço", "Link"));

            for (Product product : productsMercadoLivre) {
                List<String> row = Arrays.asList(
                        product.description,
                        "R$ " + String.valueOf(product.price),
                        product.link
                );
                mlSheet.add(row);
            }

            List<List<String>> cbSheet = new ArrayList<List<String>>();
            cbSheet.add(Arrays.asList("Descrição", "Preço", "Link"));

            for (Product product : productsCasasBahia) {
                List<String> row = Arrays.asList(
                        product.description,
                        "R$ " + String.valueOf(product.price),
                        product.link
                );
                cbSheet.add(row);
            }

            ExcelGenerator.exportToExcel(EXCEL_FILE, mlSheet, cbSheet);
        } else {
            System.out.println("Não foi encontrado nenhum produto nos sites.");
        }
    }

}