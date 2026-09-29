package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonalDetailsPage extends BasePage {

    private By personalDetailsTitle = By.xpath("//h6[text()='Personal Details']");
    private By employeeFullName = By.cssSelector(".orangehrm-edit-employee-name h6");

    public PersonalDetailsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isPersonalDetailsDisplayed() {
        return isVisible(personalDetailsTitle);
    }

    public String getEmployeeFullNameText() {
        waitForVisibility(employeeFullName);
        waitForNotEmptyText(employeeFullName);
        return getText(employeeFullName);
    }
}
