package employee;

import base.BaseTest;
import data.EmployeeDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.LoginPage;
import pages.PersonalDetailsPage;

public class AddEmployeeTests extends BaseTest {

    private AddEmployeePage openAddEmployeePage() {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.loginAs("Admin", "admin123")
                .goToPim()
                .goToAddEmployee();
    }

    @Test(dataProvider = "employees", dataProviderClass = EmployeeDataProvider.class)
    public void testAddEmployeeSuccessfully(String firstName, String middleName, String lastName) {
        AddEmployeePage addEmployeePage = openAddEmployeePage();
        String uniqueLastName = lastName + System.currentTimeMillis();

        addEmployeePage.setFirstName(firstName);
        addEmployeePage.setMiddleName(middleName);
        addEmployeePage.setLastName(uniqueLastName);
        addEmployeePage.clickSaveButton();

        Assert.assertTrue(addEmployeePage.isSuccessMessageDisplayed(),
                "Al guardar el empleado deberia mostrarse el mensaje Successfully Saved");

        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(webDriver);

        Assert.assertTrue(personalDetailsPage.isPersonalDetailsDisplayed(),
                "Despues de guardar deberia mostrarse la pagina Personal Details");
        Assert.assertEquals(personalDetailsPage.getEmployeeFullNameText(), firstName + " " + uniqueLastName);
    }

    @Test
    public void testAddEmployeeWithEmptyFieldsShowsRequiredMessages() {
        AddEmployeePage addEmployeePage = openAddEmployeePage();

        addEmployeePage.clickSaveButton();

        Assert.assertTrue(addEmployeePage.isFirstNameRequiredMessageDisplayed(),
                "Guardar sin First Name deberia mostrar Required en el campo First Name");
        Assert.assertEquals(addEmployeePage.getFirstNameRequiredMessageText(), "Required");
        Assert.assertTrue(addEmployeePage.isLastNameRequiredMessageDisplayed(),
                "Guardar sin Last Name deberia mostrar Required en el campo Last Name");
        Assert.assertEquals(addEmployeePage.getLastNameRequiredMessageText(), "Required");
    }

    @Test(dataProvider = "employees", dataProviderClass = EmployeeDataProvider.class)
    public void testAddEmployeeWithoutLastNameShowsRequired(
            String firstName, String middleName, String lastName) {
        AddEmployeePage addEmployeePage = openAddEmployeePage();

        addEmployeePage.submitEmployee(firstName, "");

        Assert.assertTrue(addEmployeePage.isLastNameRequiredMessageDisplayed(),
                "Guardar sin Last Name deberia mostrar Required en el campo Last Name");
        Assert.assertEquals(addEmployeePage.getLastNameRequiredMessageText(), "Required");
    }

    @Test(dataProvider = "employees", dataProviderClass = EmployeeDataProvider.class)
    public void testAddEmployeeWithoutFirstNameShowsRequired(
            String firstName, String middleName, String lastName) {
        AddEmployeePage addEmployeePage = openAddEmployeePage();

        addEmployeePage.submitEmployee("", lastName);

        Assert.assertTrue(addEmployeePage.isFirstNameRequiredMessageDisplayed(),
                "Guardar sin First Name deberia mostrar Required en el campo First Name");
        Assert.assertEquals(addEmployeePage.getFirstNameRequiredMessageText(), "Required");
    }
}
