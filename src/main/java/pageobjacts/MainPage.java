package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCookieButton() {
        // Проверяем наличие кнопки куки, чтобы она не блокировала клики
        if (!driver.findElements(By.id("rcc-confirm-button")).isEmpty()) {
            driver.findElement(By.id("rcc-confirm-button")).click();
        }
    }

    public void clickOrderButtonTop() {
        // Уточняем локатор, чтобы не нажать на другую кнопку в хедере
        driver.findElement(By.xpath(".//div[contains(@class, 'Header_Nav')]//button")).click();
    }

    public void clickOrderButtonBottom() {
        WebElement button = driver.findElement(By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button"));
        // Прокрутка до кнопки
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);
        // Используем JS-клик, если обычный клик перекрыт другими элементами
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public void clickAccordionQuestion(int index) {
        WebElement question = driver.findElements(By.className("accordion__button")).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
        // Ждем кликабельности и кликаем через JS (это лечит ошибку ElementClickInterceptedException)
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(question));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
    }

    public String getAccordionAnswerText(int index) {
        WebElement answer = driver.findElements(By.className("accordion__panel")).get(index);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(answer));
        return answer.getText();
    }
}