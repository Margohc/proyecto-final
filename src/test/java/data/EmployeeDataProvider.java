package data;

import com.google.gson.Gson;
import model.Employee;
import org.testng.annotations.DataProvider;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

public class EmployeeDataProvider {

    private static final String EMPLOYEES_FILE = "employees.json";

    @DataProvider(name = "employees")
    public static Object[][] employees() throws Exception {
        Employee[] employees = readEmployees();
        Object[][] data = new Object[employees.length][1];

        for (int i = 0; i < employees.length; i++) {
            employees[i].makeUnique(generateNameSuffix(), generateEmployeeId(i));
            data[i][0] = employees[i];
        }
        return data;
    }

    private static Employee[] readEmployees() throws Exception {
        try (Reader reader = new InputStreamReader(
                EmployeeDataProvider.class.getClassLoader().getResourceAsStream(EMPLOYEES_FILE),
                StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, Employee[].class);
        }
    }

    // Sufijo de letras para que el apellido siga pareciendo un nombre, ej: PerezKqzmta
    private static String generateNameSuffix() {
        StringBuilder suffix = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            suffix.append((char) ('a' + ThreadLocalRandom.current().nextInt(26)));
        }
        return Character.toUpperCase(suffix.charAt(0)) + suffix.substring(1);
    }

    // Employee Id admite hasta 10 caracteres: 8 digitos del timestamp + indice del empleado
    private static String generateEmployeeId(int index) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp.substring(timestamp.length() - 8) + index;
    }
}
