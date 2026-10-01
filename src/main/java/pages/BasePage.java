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

    protected void waitForInvisibility(By elementBy) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementBy));
    }

    protected void waitForUrlToContain(String urlPart) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    protected void waitForLoaderToFinish(By loaderBy) {
        try {
            WebDriverWait shortWait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(loaderBy));
        } catch (Exception e) {
            // El loader pudo aparecer y desaparecer antes de verlo
        }
        waitForInvisibility(loaderBy);
    }

    protected void waitForNotEmptyText(By elementBy) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));
        wait.until(driver -> !driver.findElement(elementBy).getText().trim().isEmpty());
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
