package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EmployeeListPage extends MenuPage {

    private By employeeInformationTitle = By.xpath("//h5[text()='Employee Information']");
    private By addEmployeeLink = By.xpath("//a[text()='Add Employee']");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private By employeeIdInput = By.xpath("//label[text()='Employee Id']/../following-sibling::div/input");
    private By employeeInformationToggle = By.cssSelector(".oxd-table-filter-header-options button");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By tableLoader = By.className("oxd-table-loader");
    private By recordsFoundText = By.xpath("//span[contains(normalize-space(.),'Record')]");
    private By employeeRows = By.cssSelector(".oxd-table-body .oxd-table-card");

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

    private void expandEmployeeInformationIfCollapsed() {
        if (!webDriver.findElement(employeeNameInput).isDisplayed()) {
            webDriver.findElement(employeeInformationToggle).click();
            waitForVisibility(employeeNameInput);
        }
    }

    public void setEmployeeName(String employeeName) {
        expandEmployeeInformationIfCollapsed();
        webDriver.findElement(employeeNameInput).sendKeys(employeeName);
    }

    public void setEmployeeId(String employeeId) {
        expandEmployeeInformationIfCollapsed();
        webDriver.findElement(employeeIdInput).sendKeys(employeeId);
    }

    public void clickSearchButton() {
        WebElement previousRecordsFoundText = webDriver.findElement(recordsFoundText);
        webDriver.findElement(searchButton).click();
        waitForStaleness(previousRecordsFoundText);
        waitForInvisibility(tableLoader);
        waitForVisibility(recordsFoundText);
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

    public boolean isEmployeeDisplayedInGrid(String employeeId, String firstName, String lastName) {
        waitForVisibility(employeeRows);

        return webDriver.findElements(employeeRows).stream()
                .anyMatch(row -> row.getText().contains(employeeId)
                        && row.getText().contains(firstName)
                        && row.getText().contains(lastName));
    }
}
