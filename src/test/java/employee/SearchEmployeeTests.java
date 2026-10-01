package employee;

import base.BaseTest;
import data.EmployeeDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.EmployeeListPage;
import pages.LoginPage;

public class SearchEmployeeTests extends BaseTest {

    private EmployeeListPage openEmployeeListPage() {
        LoginPage loginPage = new LoginPage(webDriver);
        return loginPage.loginAs("Admin", "admin123").goToPim();
    }

    private CreatedEmployee createEmployeeAndGoToEmployeeList(
            String firstName, String middleName, String lastName) {
        AddEmployeePage addEmployeePage = openEmployeeListPage().goToAddEmployee();
        String uniqueLastName = lastName + System.currentTimeMillis();
        String employeeId = addEmployeePage.getEmployeeId();
        EmployeeListPage employeeListPage = addEmployeePage
                .saveEmployee(firstName, middleName, uniqueLastName)
                .goToPim();

        return new CreatedEmployee(employeeListPage, employeeId, firstName, uniqueLastName);
    }

    @Test(dataProvider = "employees", dataProviderClass = EmployeeDataProvider.class)
    public void testSearchEmployeeByName(String firstName, String middleName, String lastName) {
        CreatedEmployee employee = createEmployeeAndGoToEmployeeList(firstName, middleName, lastName);

        employee.employeeListPage.searchByEmployeeName(employee.firstName + " " + employee.lastName);

        Assert.assertEquals(employee.employeeListPage.getRecordsFoundText(), "(1) Record Found",
                "La busqueda por nombre deberia encontrar solo al empleado creado");
        Assert.assertTrue(employee.employeeListPage.isEmployeeDisplayedInGrid(
                        employee.employeeId, employee.firstName, employee.lastName),
                "El empleado creado deberia aparecer en la grilla de resultados");
    }

    @Test(dataProvider = "employees", dataProviderClass = EmployeeDataProvider.class)
    public void testSearchEmployeeById(String firstName, String middleName, String lastName) {
        CreatedEmployee employee = createEmployeeAndGoToEmployeeList(firstName, middleName, lastName);

        employee.employeeListPage.searchByEmployeeId(employee.employeeId);

        Assert.assertEquals(employee.employeeListPage.getRecordsFoundText(), "(1) Record Found",
                "La busqueda por Employee Id deberia encontrar solo al empleado creado");
        Assert.assertTrue(employee.employeeListPage.isEmployeeDisplayedInGrid(
                        employee.employeeId, employee.firstName, employee.lastName),
                "El empleado creado deberia aparecer en la grilla de resultados");
    }

    @Test
    public void testSearchNonExistingEmployeeShowsNoRecords() {
        EmployeeListPage employeeListPage = openEmployeeListPage();

        employeeListPage.searchByEmployeeName("EmpleadoInexistente" + System.currentTimeMillis());

        Assert.assertEquals(employeeListPage.getRecordsFoundText(), "No Records Found",
                "La busqueda de un empleado inexistente deberia mostrar No Records Found");
    }

    private static class CreatedEmployee {

        private final EmployeeListPage employeeListPage;
        private final String employeeId;
        private final String firstName;
        private final String lastName;

        private CreatedEmployee(EmployeeListPage employeeListPage, String employeeId,
                                String firstName, String lastName) {
            this.employeeListPage = employeeListPage;
            this.employeeId = employeeId;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
