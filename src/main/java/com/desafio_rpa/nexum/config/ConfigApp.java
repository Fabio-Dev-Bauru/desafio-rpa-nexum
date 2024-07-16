package com.desafio_rpa.nexum.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigApp {

    private static final String KEY_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String PATH_CHROME_DRIVER = "C:\\Users\\FaBia\\Desktop\\Projetos\\nexum\\src\\main\\resources\\chromedriver.exe";

    @PostConstruct
    void postConstruct() {
        System.setProperty(KEY_CHROME_DRIVER, PATH_CHROME_DRIVER);
    }

    @Bean
    public WebDriver driver() {
        System.out.println("Inicializando Chrome WebDriver");
        final ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless");
        return new ChromeDriver();
    }
}
