package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {

    private By dashboardTitle = By.xpath("//h6[text()='Dashboard']");
    private By pimMenu = By.xpath("//span[text()='PIM']");

    public DashboardPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isDashboardDisplayed() {
        return isVisible(dashboardTitle);
    }

    public String getDashboardTitleText() {
        return getText(dashboardTitle);
    }

    public EmployeeListPage goToPim() {
        waitForVisibility(pimMenu);
        webDriver.findElement(pimMenu).click();
        return new EmployeeListPage(webDriver);
    }
}
