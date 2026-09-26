package login;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(webDriver);

        DashboardPage dashboardPage = loginPage.loginAs("Admin", "admin123");

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                "El login con credenciales validas deberia mostrar el Dashboard");
        Assert.assertEquals(dashboardPage.getDashboardTitleText(), "Dashboard");
    }

    @Test
    public void testLoginWithInvalidCredentialsShowsError() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLogin("usuarioInvalido", "passwordInvalido");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "El login con credenciales invalidas deberia mostrar un mensaje de error");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Invalid credentials");
    }

    @Test
    public void testLoginWithUsernameOnlyShowsPasswordRequired() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLoginWithUsernameOnly("Admin");

        Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed(),
                "El login sin password deberia mostrar el mensaje Required en el campo password");
        Assert.assertEquals(loginPage.getPasswordRequiredMessageText(), "Required");
    }

    @Test
    public void testLoginWithPasswordOnlyShowsUsernameRequired() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLoginWithPasswordOnly("admin123");

        Assert.assertTrue(loginPage.isUsernameRequiredMessageDisplayed(),
                "El login sin username deberia mostrar el mensaje Required en el campo username");
        Assert.assertEquals(loginPage.getUsernameRequiredMessageText(), "Required");
    }

    @Test
    public void testLoginWithEmptyFieldsShowsRequiredMessages() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitEmptyLogin();

        Assert.assertTrue(loginPage.isUsernameRequiredMessageDisplayed(),
                "El login sin username deberia mostrar Required en el campo username");
        Assert.assertEquals(loginPage.getUsernameRequiredMessageText(), "Required");
        Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed(),
                "El login sin password deberia mostrar Required en el campo password");
        Assert.assertEquals(loginPage.getPasswordRequiredMessageText(), "Required");
    }

    @Test
    public void testLoginWithInvalidUsernameAndValidPasswordShowsError() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLogin("usuarioInvalido", "admin123");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "El login con username invalido y password valido deberia mostrar error");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Invalid credentials");
    }

    @Test
    public void testLoginWithValidUsernameAndInvalidPasswordShowsError() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLogin("Admin", "passwordInvalido");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "El login con username valido y password invalido deberia mostrar error");
        Assert.assertEquals(loginPage.getErrorMessageText(), "Invalid credentials");
    }

    @Test
    public void testLoginWithBlankUsernameAndValidPasswordShowsUsernameRequired() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLogin("   ", "admin123");

        Assert.assertTrue(loginPage.isUsernameRequiredMessageDisplayed(),
                "El login con username compuesto por espacios deberia mostrar Required en username");
        Assert.assertEquals(loginPage.getUsernameRequiredMessageText(), "Required");
    }

    @Test
    public void testLoginWithValidUsernameAndBlankPasswordShowsPasswordRequired() {
        LoginPage loginPage = new LoginPage(webDriver);

        loginPage.submitLogin("Admin", "   ");

        Assert.assertTrue(loginPage.isPasswordRequiredMessageDisplayed(),
                "El login con password compuesto por espacios deberia mostrar Required en password");
        Assert.assertEquals(loginPage.getPasswordRequiredMessageText(), "Required");
    }
}
