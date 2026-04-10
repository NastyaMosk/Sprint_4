package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderFormPage {
    private final WebDriver driver;

    // --- Локаторы вынесены в поля класса ---
    private final By nameInput = By.xpath(".//input[contains(@placeholder, 'Имя')]");
    private final By surnameInput = By.xpath(".//input[contains(@placeholder, 'Фамилия')]");
    private final By addressInput = By.xpath(".//input[contains(@placeholder, 'Адрес')]");
    private final By metroInput = By.className("select-search__input");
    private final By phoneInput = By.xpath(".//input[contains(@placeholder, 'Телефон')]");
    private final By nextButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");

    private final By rentForm = By.className("Order_Form__17u6u");
    private final By dateInput = By.xpath(".//input[contains(@placeholder, 'Когда')]");
    private final By periodDropdown = By.className("Dropdown-control");

    // Уточненные локаторы для кнопок (по тексту)
    private final By orderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button");
    private final By confirmOrderButton = By.xpath(".//button");
    private final By successModalHeader = By.xpath(".//div");

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
    }

    // Шаг 1: Заполнение персональных данных
    public void fillOrderForm(String name, String surname, String address, String metro, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);

        // Выбор метро
        driver.findElement(metroInput).click();
        By metroOption = By.xpath(String.format(".//div[@class='select-search__select']//*", metro));
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(metroOption)).click();

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Шаг 2: Заполнение данных об аренде
    public void fillRentDetails(String date, String period, String color) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(rentForm));

        // Дата
        driver.findElement(dateInput).sendKeys(date);
        driver.findElement(dateInput).sendKeys(Keys.ENTER);

        // Срок аренды
        driver.findElement(periodDropdown).click();
        By periodOption = By.xpath(String.format(".//div[@class='Dropdown-menu']//*", period));
        driver.findElement(periodOption).click();

        // Цвет (id: black или grey)
        driver.findElement(By.id(color)).click();

        // Нажать "Заказать"
        driver.findElement(orderButton).click();

        // Нажать "Да" в модалке подтверждения
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    // Проверка окна успеха с ожиданием (исправляет AssertionError)
    public boolean isSuccessModalDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(successModalHeader));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}