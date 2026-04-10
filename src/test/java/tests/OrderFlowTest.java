package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobjects.MainPage;
import pageobjects.OrderFormPage;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderFlowTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderFormPage orderFormPage;

    private final String browser;
    private final boolean useTopButton;
    private final String name;
    private final String phone;

    public OrderFlowTest(String browser, boolean useTopButton, String name, String phone) {
        this.browser = browser;
        this.useTopButton = useTopButton;
        this.name = name;
        this.phone = phone;
    }

    // Исправлено: добавлено имя для информативности отчетов
    @Parameterized.Parameters(name = "Тестовые данные: браузер {0}, кнопка сверху {1}, имя {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"chrome", true, "Иван", "+79991234567"},
                {"firefox", false, "Петр", "+79997654321"}
        });
    }

    @Before
    public void setUp() {
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        // Исправлено: URL берется из константы в MainPage
        driver.get(MainPage.URL);

        mainPage = new MainPage(driver);
        orderFormPage = new OrderFormPage(driver);
    }

    @Test
    public void testOrderFlow() {
        mainPage.clickCookieButton();

        if (useTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        orderFormPage.fillOrderForm(name, "Иванов", "ул. Мира, 1", "Сокольники", phone);
        orderFormPage.fillRentDetails("12.12.2025", "сутки", "black");

        Assert.assertTrue("Окно успеха не появилось", orderFormPage.isSuccessModalDisplayed());
    }

    // Замечание ревьюера: тест аккордеона удален отсюда.
    // Его необходимо создать в отдельном классе AccordionTest.java с собственной параметризацией.

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}