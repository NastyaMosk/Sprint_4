package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderFormPage {
    private final WebDriver driver;

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
    }

    // Шаг 1: Для кого самокат
    public void fillOrderForm(String name, String surname, String address, String metro, String phone) {
        // Имя
        driver.findElement(By.xpath(".//input[contains(@placeholder, 'Имя')]")).sendKeys(name);
        // Фамилия
        driver.findElement(By.xpath(".//input[contains(@placeholder, 'Фамилия')]")).sendKeys(surname);
        // Адрес
        driver.findElement(By.xpath(".//input[contains(@placeholder, 'Адрес')]")).sendKeys(address);

        // Метро
        driver.findElement(By.className("select-search__input")).click();
        String metroOption = String.format(".//div[@class='select-search__select']//*", metro);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(metroOption))).click();

        // Телефон
        driver.findElement(By.xpath(".//input[contains(@placeholder, 'Телефон')]")).sendKeys(phone);
        // Кнопка Далее
        driver.findElement(By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM")).click();
    }

    // Шаг 2: Про аренду
    public void fillRentDetails(String date, String period, String color) {
        // Ожидание загрузки второй страницы
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Order_Form__17u6u")));

        // Поле даты (вводим и жмем Enter)
        driver.findElement(By.xpath(".//input[contains(@placeholder, 'Когда')]")).sendKeys(date);
        driver.findElement(By.xpath(".//input[contains(@placeholder, 'Когда')]")).sendKeys(Keys.ENTER);

        // Срок аренды
        driver.findElement(By.className("Dropdown-control")).click();
        String periodOption = String.format(".//div[@class='Dropdown-menu']//*", period);
        driver.findElement(By.xpath(periodOption)).click();

        // Цвет (black / grey)
        driver.findElement(By.id(color)).click();

        // Нажать Заказать
        driver.findElement(By.xpath(".//div[contains(@class, 'Order_Buttons')]//button")).click();

        // Нажать Да в модалке
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(".//button"))).click();
    }

    public boolean isSuccessModalDisplayed() {
        return !driver.findElements(By.className("Order_ModalHeader__3FIIc")).isEmpty();
    }
}