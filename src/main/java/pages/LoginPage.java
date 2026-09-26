package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginButton = By.className("orangehrm-login-button");
    private By loginErrorMessage = By.cssSelector("[role='alert'] p");
    private By usernameRequiredMessage = By.xpath("//input[@name='username']/ancestor::div[contains(@class,'oxd-input-group')][1]//span[contains(@class,'oxd-input-field-error-message')]");
    private By passwordRequiredMessage = By.xpath("//input[@name='password']/ancestor::div[contains(@class,'oxd-input-group')][1]//span[contains(@class,'oxd-input-field-error-message')]");

    public LoginPage(WebDriver webDriver) {
        super(webDriver);

        waitForVisibility(usernameInput);
    }

    public void setUsername(String username) {
        webDriver.findElement(usernameInput).clear();
        webDriver.findElement(usernameInput).sendKeys(username);
    }

    public void setPassword(String password) {
        webDriver.findElement(passwordInput).clear();
        webDriver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLoginButton() {
        webDriver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        submitLogin(username, password);
    }

    public void submitLogin(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLoginButton();
    }

    public void submitLoginWithUsernameOnly(String username) {
        setUsername(username);
        clickLoginButton();
    }

    public void submitLoginWithPasswordOnly(String password) {
        setPassword(password);
        clickLoginButton();
    }

    public void submitEmptyLogin() {
        clickLoginButton();
    }

    public DashboardPage loginAs(String username, String password) {
        submitLogin(username, password);
        return new DashboardPage(webDriver);
    }

    public boolean isErrorMessageDisplayed() {
        return isVisible(loginErrorMessage);
    }

    public String getErrorMessageText() {
        return getText(loginErrorMessage);
    }

    public boolean isUsernameRequiredMessageDisplayed() {
        return isVisible(usernameRequiredMessage);
    }

    public String getUsernameRequiredMessageText() {
        return getText(usernameRequiredMessage);
    }

    public boolean isPasswordRequiredMessageDisplayed() {
        return isVisible(passwordRequiredMessage);
    }

    public String getPasswordRequiredMessageText() {
        return getText(passwordRequiredMessage);
    }
}
