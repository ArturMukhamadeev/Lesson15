package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class OnlinePaymentPage {
    private WebDriver driver;
    private By blockTitleLocator = By.xpath("//*[contains(text(), 'Онлайн пополнение без комиссии')]");
    private By continueButtonLocator = By.xpath("//button[contains(text(),'Продолжить')]");
    private By phoneInputLocator = By.xpath("//input[@type='tel']");
    private By displayedAmountLocator = By.id("displayedAmount");
    private By confirmedPhoneLocator = By.id("confirmedPhone");
    private By cardExpiryLocator = By.id("cardExpiry");
    private By paymentIconsLocator = By.cssSelector(".payment-icons img");

    public OnlinePaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isBlockTitleDisplayed() {
        return driver.findElement(blockTitleLocator).isDisplayed();
    }

    public void selectPaymentOption(String optionName) {
        WebElement optionLabel = driver.findElement(By.xpath("//label[contains(text(), '" + optionName + "')]"));
        optionLabel.click();
    }

    public String getInputFieldValue(By locator) {
        return driver.findElement(locator).getAttribute("value");
    }

    public void fillPhoneInput(String phone) {
        WebElement phoneInput = driver.findElement(phoneInputLocator);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public void fillInputForOption(String optionName, String value) {
        WebElement input = driver.findElement(By.xpath("//label[contains(text(), '" + optionName + "')]/following-sibling::input"));
        input.clear();
        input.sendKeys(value);
    }

    public void clickContinue() {
        driver.findElement(continueButtonLocator).click();
    }

    public String getDisplayedAmount() {
        return driver.findElement(displayedAmountLocator).getText();
    }

    public String getConfirmedPhone() {
        return driver.findElement(confirmedPhoneLocator).getText();
    }

    public String getCardExpiry() {
        return driver.findElement(cardExpiryLocator).getText();
    }

    public boolean arePaymentIconsDisplayed() {
        List<WebElement> icons = driver.findElements(paymentIconsLocator);
        return !icons.isEmpty() && icons.stream().allMatch(WebElement::isDisplayed);
    }
}
