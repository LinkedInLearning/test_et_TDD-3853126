package com.syllab.fruits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AccueilTest {
  private WebDriver driver;

  @BeforeEach
  void initWebDriver() {
    var options = new ChromeOptions();

    options.addArguments("--headless");
    driver = new ChromeDriver(options);
    driver.get("https://labasse.github.io/fruits/");
  }

  @Test
  void recherche_ana_AnanasBanane() {
    
  }
}