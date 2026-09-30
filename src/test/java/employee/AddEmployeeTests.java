package employee;

import base.BaseTest;
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

    @Test
    public void testAddEmployeeSuccessfully() {
        AddEmployeePage addEmployeePage = openAddEmployeePage();
        String lastName = "Perez" + System.currentTimeMillis();

        addEmployeePage.fillEmployeeName("Juan", "Carlos", lastName);
        addEmployeePage.clickSaveButton();

        // El mensaje desaparece al redirigir a Personal Details, por eso se valida antes
        Assert.assertTrue(addEmployeePage.isSuccessMessageDisplayed(),
                "Al guardar el empleado deberia mostrarse el mensaje Successfully Saved");
        PersonalDetailsPage personalDetailsPage = addEmployeePage.goToPersonalDetails();
        Assert.assertTrue(personalDetailsPage.isPersonalDetailsDisplayed(),
                "Despues de guardar deberia mostrarse la pagina Personal Details");
        Assert.assertEquals(personalDetailsPage.getEmployeeFullNameText(), "Juan " + lastName);
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

    @Test
    public void testAddEmployeeWithoutLastNameShowsRequired() {
        AddEmployeePage addEmployeePage = openAddEmployeePage();

        addEmployeePage.submitEmployee("Juan", "");

        Assert.assertTrue(addEmployeePage.isLastNameRequiredMessageDisplayed(),
                "Guardar sin Last Name deberia mostrar Required en el campo Last Name");
        Assert.assertEquals(addEmployeePage.getLastNameRequiredMessageText(), "Required");
    }

    @Test
    public void testAddEmployeeWithoutFirstNameShowsRequired() {
        AddEmployeePage addEmployeePage = openAddEmployeePage();

        addEmployeePage.submitEmployee("", "Perez");

        Assert.assertTrue(addEmployeePage.isFirstNameRequiredMessageDisplayed(),
                "Guardar sin First Name deberia mostrar Required en el campo First Name");
        Assert.assertEquals(addEmployeePage.getFirstNameRequiredMessageText(), "Required");
    }
}
