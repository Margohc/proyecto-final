package employee;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.EmployeeListPage;
import pages.LoginPage;

public class SearchEmployeeTests extends BaseTest {

    private String firstName = "Juan";
    private String lastName;
    private String employeeId;

    private EmployeeListPage openEmployeeListPage() {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.loginAs("Admin", "admin123").goToPim();
    }

    private EmployeeListPage createEmployeeAndGoToEmployeeList() {
        AddEmployeePage addEmployeePage = openEmployeeListPage().goToAddEmployee();
        lastName = "Perez" + System.currentTimeMillis();
        employeeId = addEmployeePage.getEmployeeId();

        return addEmployeePage.saveEmployee(firstName, "Carlos", lastName).goToPim();
    }

    @Test
    public void testSearchEmployeeByName() {
        EmployeeListPage employeeListPage = createEmployeeAndGoToEmployeeList();

        employeeListPage.searchByEmployeeName(firstName + " " + lastName);

        Assert.assertEquals(employeeListPage.getRecordsFoundText(), "(1) Record Found",
                "La busqueda por nombre deberia encontrar solo al empleado creado");
    }

    @Test
    public void testSearchEmployeeById() {
        EmployeeListPage employeeListPage = createEmployeeAndGoToEmployeeList();

        employeeListPage.searchByEmployeeId(employeeId);

        Assert.assertEquals(employeeListPage.getRecordsFoundText(), "(1) Record Found",
                "La busqueda por Employee Id deberia encontrar solo al empleado creado");
    }

    @Test
    public void testSearchNonExistingEmployeeShowsNoRecords() {
        EmployeeListPage employeeListPage = openEmployeeListPage();

        employeeListPage.searchByEmployeeName("EmpleadoInexistente" + System.currentTimeMillis());

        Assert.assertEquals(employeeListPage.getRecordsFoundText(), "No Records Found",
                "La busqueda de un empleado inexistente deberia mostrar No Records Found");
    }
}
