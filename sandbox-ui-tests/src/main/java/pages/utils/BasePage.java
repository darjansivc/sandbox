package pages.utils;


import org.openqa.selenium.*;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BasePage {
    protected static WebDriver driver;
    private static final int WAITING_TIME_IN_SECONDS =30;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static String getCurrentTimeAndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmssddMMyyyy");
        Date date = new Date();
        String currentDateAndTime = formatter.format(date);
        return currentDateAndTime;
    }


    public static void clickWithJavaScript(WebElement webElement) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", webElement);
    }
    public static boolean isExpectedUrl(String pageLink){
        return driver.getCurrentUrl().equals(pageLink);
    }

    public static void waitForElementToBeClickable(WebElement selector) {
        new WebDriverWait(driver, WAITING_TIME_IN_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(selector));
    }

    public static void waitForElementToBeVisible(WebElement selector) {
        (new WebDriverWait(driver, WAITING_TIME_IN_SECONDS)).until(ExpectedConditions.visibilityOf(selector));
    }

}
