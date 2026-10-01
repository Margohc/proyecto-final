package data;

import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class EmployeeDataProvider {

    private static final String EMPLOYEE_DATA_FILE = "employee-data.csv";

    private EmployeeDataProvider() {
    }

    @DataProvider(name = "employees")
    public static Object[][] employees() {
        InputStream inputStream = EmployeeDataProvider.class.getClassLoader()
                .getResourceAsStream(EMPLOYEE_DATA_FILE);

        if (inputStream == null) {
            throw new IllegalStateException("No se encontro el archivo" + EMPLOYEE_DATA_FILE);
        }

        List<Object[]> employees = new ArrayList<>(); //lista de empleados

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream,StandardCharsets.UTF_8))) {
            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] employee = line.split(",", -1);
                if (employee.length != 3) {
                    throw new IllegalStateException("Fila invalida en " + EMPLOYEE_DATA_FILE + ": " + line);
                }

                employees.add(new Object[]{
                        employee[0].trim(),
                        employee[1].trim(),
                        employee[2].trim()
                });
            }
        } catch (IOException exception) {
            throw new IllegalStateException("No se pudo leer el archivo " + EMPLOYEE_DATA_FILE, exception);
        }

        if (employees.isEmpty()) {
            throw new IllegalStateException("El archivo " + EMPLOYEE_DATA_FILE + " no contiene empleados ");
        }

        return employees.toArray(new Object[0][]);
    }
}
