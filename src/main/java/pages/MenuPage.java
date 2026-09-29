package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class MenuPage extends BasePage {

    private By pimMenu = By.xpath("//span[text()='PIM']");

    public MenuPage(WebDriver webDriver) {
        super(webDriver);
    }

    public EmployeeListPage goToPim() {
        waitForVisibility(pimMenu);
        webDriver.findElement(pimMenu).click();
        return new EmployeeListPage(webDriver);
    }
}
