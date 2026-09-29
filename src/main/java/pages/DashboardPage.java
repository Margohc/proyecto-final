package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends MenuPage {

    private By dashboardTitle = By.xpath("//h6[text()='Dashboard']");

    public DashboardPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isDashboardDisplayed() {
        return isVisible(dashboardTitle);
    }

    public String getDashboardTitleText() {
        return getText(dashboardTitle);
    }
}
