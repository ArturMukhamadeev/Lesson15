package org.example.tests;

import org.example.pages.OnlinePaymentPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class PaymentTests {
    private WebDriver driver;
    private OnlinePaymentPage paymentPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.mts.by");
        paymentPage = new OnlinePaymentPage(driver);
    }

    @After
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPaymentOptionsAndUslugiSvyaziConfirmation() {
        paymentPage.selectPaymentOption("Услуги связи");
        String phoneInitial = paymentPage.getInputFieldValue(By.xpath("//input[@type='tel']"));
        assertEquals("", phoneInitial);
        paymentPage.fillPhoneInput("297777777");
        String phoneFilled = paymentPage.getInputFieldValue(By.xpath("//input[@type='tel']"));
        assertEquals("297777777", phoneFilled);
        paymentPage.selectPaymentOption("Домашний интернет");
        paymentPage.fillInputForOption("Домашний интернет", "TestInternet");
        String internetValue = paymentPage.getInputFieldValue(By.xpath("//label[contains(text(),'Домашний интернет')]/following-sibling::input"));
        assertEquals("TestInternet", internetValue);
        paymentPage.selectPaymentOption("Услуги, связанные");
        paymentPage.fillInputForOption("Услуги, связанные", "TestService");
        String serviceValue = paymentPage.getInputFieldValue(By.xpath("//label[contains(text(),'Услуги, связанные')]/following-sibling::input"));
        assertEquals("TestService", serviceValue);
        paymentPage.selectPaymentOption("Услуги связи");
        paymentPage.fillPhoneInput("297777777");
        paymentPage.clickContinue();
        String expectedAmount = "100.00 руб";
        String expectedPhone = "297777777";
        String expectedCardExpiry = "12/25";
        assertEquals(expectedAmount, paymentPage.getDisplayedAmount());
        assertEquals(expectedPhone, paymentPage.getConfirmedPhone());
        assertEquals(expectedCardExpiry, paymentPage.getCardExpiry());
        assertTrue(paymentPage.arePaymentIconsDisplayed());
    }
}
