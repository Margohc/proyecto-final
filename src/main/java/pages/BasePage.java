package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver webDriver;

    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    protected void waitForVisibility(By elementBy) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
    }

    protected boolean isVisible(By elementBy) {
        try {
            waitForVisibility(elementBy);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected String getText(By elementBy) {
        waitForVisibility(elementBy);
        return webDriver.findElement(elementBy).getText();
    }
}
