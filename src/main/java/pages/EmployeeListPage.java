package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EmployeeListPage extends MenuPage {

    private By employeeInformationTitle = By.xpath("//h5[text()='Employee Information']");
    private By addEmployeeLink = By.xpath("//a[text()='Add Employee']");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private By employeeIdInput = By.xpath("//label[text()='Employee Id']/../following-sibling::div/input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By tableLoader = By.className("oxd-table-loader");
    private By recordsFoundText = By.xpath("//span[contains(normalize-space(.),'Record')]");

    public EmployeeListPage(WebDriver webDriver) {
        super(webDriver);

        waitForVisibility(employeeInformationTitle);
        waitForInvisibility(tableLoader);
    }

    public boolean isEmployeeListDisplayed() {
        return isVisible(employeeInformationTitle);
    }

    public AddEmployeePage goToAddEmployee() {
        webDriver.findElement(addEmployeeLink).click();
        return new AddEmployeePage(webDriver);
    }

    public void setEmployeeName(String employeeName) {
        webDriver.findElement(employeeNameInput).sendKeys(employeeName);
    }

    public void setEmployeeId(String employeeId) {
        webDriver.findElement(employeeIdInput).sendKeys(employeeId);
    }

    public void clickSearchButton() {
        webDriver.findElement(searchButton).click();
        waitForLoaderToFinish(tableLoader);
    }

    public void searchByEmployeeName(String employeeName) {
        setEmployeeName(employeeName);
        clickSearchButton();
    }

    public void searchByEmployeeId(String employeeId) {
        setEmployeeId(employeeId);
        clickSearchButton();
    }

    public String getRecordsFoundText() {
        return getText(recordsFoundText);
    }
}
