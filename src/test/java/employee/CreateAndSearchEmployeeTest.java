package employee;

import base.BaseTest;
import data.EmployeeDataProvider;
import model.Employee;
import model.EmployeeRow;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.EmployeeListPage;
import pages.LoginPage;

import java.util.List;

public class CreateAndSearchEmployeeTest extends BaseTest {

    @Test(dataProvider = "employees", dataProviderClass = EmployeeDataProvider.class)
    public void testCreatedEmployeeAppearsInEmployeeList(Employee employee) {
        LoginPage loginPage = new LoginPage(webDriver);

        EmployeeListPage employeeListPage = loginPage.loginAs("Admin", "admin123")
                .goToPim()
                .goToAddEmployee()
                .saveEmployee(employee)
                .goToPim();

        employeeListPage.searchByEmployeeName(employee.getSearchName());
        List<EmployeeRow> results = employeeListPage.getResultRows();

        Assert.assertEquals(results.size(), 1,
                "La busqueda deberia devolver solo al empleado creado, pero devolvio: " + results);
        EmployeeRow row = results.get(0);
        Assert.assertEquals(row.getId(), employee.getEmployeeId(), "El Id de la grilla no coincide");
        Assert.assertEquals(row.getFirstAndMiddleName(), employee.getFirstAndMiddleName(),
                "El nombre y segundo nombre de la grilla no coinciden");
        Assert.assertEquals(row.getLastName(), employee.getLastName(), "El apellido de la grilla no coincide");
    }
}
