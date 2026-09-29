package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmployeeListPage extends BasePage {

    private By employeeInformationTitle = By.xpath("//h5[text()='Employee Information']");
    private By addEmployeeLink = By.xpath("//a[text()='Add Employee']");

    public EmployeeListPage(WebDriver webDriver) {
        super(webDriver);

        waitForVisibility(employeeInformationTitle);
    }

    public boolean isEmployeeListDisplayed() {
        return isVisible(employeeInformationTitle);
    }

    public AddEmployeePage goToAddEmployee() {
        webDriver.findElement(addEmployeeLink).click();
        return new AddEmployeePage(webDriver);
    }
}
