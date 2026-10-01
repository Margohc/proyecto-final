package pages;

import model.Employee;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddEmployeePage extends MenuPage {

    private By addEmployeeTitle = By.xpath("//h6[text()='Add Employee']");
    private By formLoader = By.className("oxd-form-loader");
    private By firstNameInput = By.name("firstName");
    private By middleNameInput = By.name("middleName");
    private By lastNameInput = By.name("lastName");
    private By employeeIdInput = By.xpath("//label[text()='Employee Id']/../following-sibling::div/input");
    private By createLoginDetailsSwitch = By.cssSelector(".oxd-switch-wrapper span");
    private By usernameInput = By.xpath("//label[text()='Username']/../following-sibling::div/input");
    private By passwordInput = By.xpath("//label[text()='Password']/../following-sibling::div/input");
    private By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../following-sibling::div/input");
    private String statusOptionXpath = "//div[contains(@class,'oxd-radio-wrapper')]/label[normalize-space()='%s']";
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

    public void setEmployeeId(String employeeId) {
        WebElement input = webDriver.findElement(employeeIdInput);
        Keys selectAllKey = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        input.sendKeys(Keys.chord(selectAllKey, "a"), Keys.DELETE);
        input.sendKeys(employeeId);
    }

    public void enableCreateLoginDetails() {
        webDriver.findElement(createLoginDetailsSwitch).click();
        waitForVisibility(usernameInput);
    }

    public void setUsername(String username) {
        webDriver.findElement(usernameInput).sendKeys(username);
    }

    public void setPassword(String password) {
        webDriver.findElement(passwordInput).sendKeys(password);
    }

    public void setConfirmPassword(String password) {
        webDriver.findElement(confirmPasswordInput).sendKeys(password);
    }

    public void selectStatus(String status) {
        webDriver.findElement(By.xpath(String.format(statusOptionXpath, status))).click();
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

    public void fillEmployeeName(String firstName, String middleName, String lastName) {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
    }

    public PersonalDetailsPage saveEmployee(String firstName, String middleName, String lastName) {
        fillEmployeeName(firstName, middleName, lastName);
        clickSaveButton();
        return goToPersonalDetails();
    }

    // Despues de guardar, el sitio redirige solo a Personal Details
    public PersonalDetailsPage goToPersonalDetails() {
        return new PersonalDetailsPage(webDriver);
    }

    public PersonalDetailsPage saveEmployee(Employee employee) {
        fillEmployeeName(employee.getFirstName(), employee.getMiddleName(), employee.getLastName());
        setEmployeeId(employee.getEmployeeId());

        enableCreateLoginDetails();
        setUsername(employee.getUsername());
        selectStatus(employee.getStatus());
        setPassword(employee.getPassword());
        setConfirmPassword(employee.getPassword());

        clickSaveButton();
        return goToPersonalDetails();
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
