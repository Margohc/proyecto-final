package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddEmployeePage extends MenuPage {

    private By addEmployeeTitle = By.xpath("//h6[text()='Add Employee']");
    private By formLoader = By.className("oxd-form-loader");
    private By firstNameInput = By.name("firstName");
    private By middleNameInput = By.name("middleName");
    private By lastNameInput = By.name("lastName");
    private By employeeIdInput = By.xpath("//label[text()='Employee Id']/../following-sibling::div/input");
    private By saveButton = By.xpath("//button[@type='submit']");
    private By successMessage = By.xpath("//div[contains(@class,'oxd-toast')]//p[text()='Successfully Saved']");
    private By firstNameRequiredMessage = By.xpath("//input[@name='firstName']/ancestor::div[contains(@class,'oxd-input-group')][1]//span[contains(@class,'oxd-input-field-error-message')]");
    private By lastNameRequiredMessage = By.xpath("//input[@name='lastName']/ancestor::div[contains(@class,'oxd-input-group')][1]//span[contains(@class,'oxd-input-field-error-message')]");

    public AddEmployeePage(WebDriver webDriver) {
        super(webDriver);

        waitForVisibility(addEmployeeTitle);
        waitForInvisibility(formLoader);
    }

    public boolean isAddEmployeeDisplayed() {
        return isVisible(addEmployeeTitle);
    }

    public void setFirstName(String firstName) {
        webDriver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void setMiddleName(String middleName) {
        webDriver.findElement(middleNameInput).sendKeys(middleName);
    }

    public void setLastName(String lastName) {
        webDriver.findElement(lastNameInput).sendKeys(lastName);
    }

    public String getEmployeeId() {
        return webDriver.findElement(employeeIdInput).getAttribute("value");
    }

    public void clickSaveButton() {
        webDriver.findElement(saveButton).click();
    }

    public void submitEmployee(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
        clickSaveButton();
    }

    public PersonalDetailsPage saveEmployee(String firstName, String middleName, String lastName) {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        clickSaveButton();
        return new PersonalDetailsPage(webDriver);
    }

    public boolean isSuccessMessageDisplayed() {
        return isVisible(successMessage);
    }

    public boolean isFirstNameRequiredMessageDisplayed() {
        return isVisible(firstNameRequiredMessage);
    }

    public String getFirstNameRequiredMessageText() {
        return getText(firstNameRequiredMessage);
    }

    public boolean isLastNameRequiredMessageDisplayed() {
        return isVisible(lastNameRequiredMessage);
    }

    public String getLastNameRequiredMessageText() {
        return getText(lastNameRequiredMessage);
    }
}
