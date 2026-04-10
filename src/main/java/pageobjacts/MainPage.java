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

    // Константа URL для использования в тестах (согласно замечанию ревьюера)
    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    // Локаторы в полях класса (для переиспользования)
    private final By cookieButton = By.id("rcc-confirm-button");
    private final By orderButtonTop = By.xpath(".//div[contains(@class, 'Header_Nav')]//button");
    private final By orderButtonBottom = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button");
    private final By accordionButtons = By.className("accordion__button");
    private final By accordionPanels = By.className("accordion__panel");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCookieButton() {
        if (!driver.findElements(cookieButton).isEmpty()) {
            driver.findElement(cookieButton).click();
        }
    }

    public void clickOrderButtonTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderButtonBottom() {
        WebElement button = driver.findElement(orderButtonBottom);
        // Прокрутка до кнопки
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);

        // Ожидание кликабельности (важно для Firefox)
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(button));

        // Используем JS-клик для нижней кнопки, чтобы избежать ElementClickInterceptedException в Firefox
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public void clickAccordionQuestion(int index) {
        WebElement question = driver.findElements(accordionButtons).get(index);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(question));

        // Используем JS-клик для вопросов аккордеона
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", question);
    }

    public String getAccordionAnswerText(int index) {
        WebElement answer = driver.findElements(accordionPanels).get(index);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(answer));
        return answer.getText();
    }
}