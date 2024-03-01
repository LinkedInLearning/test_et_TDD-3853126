package com.syllab.fruits;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
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
    var recherche = driver.findElement(By.cssSelector("input[type=search]"));
    recherche.click();
    recherche.sendKeys("ana");

    var cartes = driver.findElements(By.className("card"));
    var titres = cartes.stream()
        .map(c -> c.findElement(By.className("card-title")).getText())
        .toArray();
    assertArrayEquals(new String[]{"Ananas", "Banane"}, titres);
  }
}