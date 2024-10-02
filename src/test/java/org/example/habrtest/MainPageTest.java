package org.example.habrtest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest {
    private WebDriver driver;
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://habr.com/ru/feed/");
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @DisplayName("Проверка обязательных полей")
    @RepeatedTest(5)
    public void sendTest() {
        WebElement siteHelp = driver.findElement(By.xpath(
                "//div[@class='tm-footer__container']//a[contains(text(), 'Техническая поддержка')]"));
        siteHelp.click();
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        List <WebElement> errorMessage = driver.findElements(By.xpath("//p[contains(text(), 'Обязательное поле')]"));
        assertFalse(errorMessage.isEmpty(),"Не заполненны обязательные формы");
    }
    @DisplayName("Проверка заголовка страницы")
    @Test
    public void feedbackTest() {
        WebElement siteHelp = driver.findElement(By.xpath(
                "//div[@class='tm-footer__container']//a[contains(text(), 'Техническая поддержка')]"));
        siteHelp.click();
        WebElement heading = driver.findElement(By.xpath("//h1[contains(text(), 'Техническая поддержка')]"));
        assertTrue(heading.isDisplayed(), "Заголовок Техническая поодержка не найден");
    }
}
