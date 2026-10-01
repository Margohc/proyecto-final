# Proyecto final

Proyecto de automatización web para la página OrangeHRM Demo, desarrollado con Java, Maven, Selenium WebDriver y TestNG, aplicando el patrón Page Object Model.

Página utilizada: https://opensource-demo.orangehrmlive.com

## Caso de negocio automatizado

Una sola prueba (`CreateAndSearchEmployeeTest`) recorre el flujo completo:

1. Iniciar sesión como administrador.
2. Ir al módulo **PIM** desde el menú.
3. Crear un empleado nuevo con:
   - nombre, segundo nombre y apellido
   - ID de empleado
   - datos de usuario (switch **Create Login Details**): usuario, contraseña, confirmación y estado
4. Buscar al empleado por nombre en el listado de empleados.
5. Verificar que aparece en la grilla con su ID, nombre y apellido.

La prueba se ejecuta con **dos empleados** (DataProvider) en **dos navegadores** (Chrome y Edge).

## Tecnologías

- Java 11
- Maven
- Selenium WebDriver
- TestNG
- ExtentReports
- Gson (lectura del archivo de datos)

## Estructura del proyecto

```text
src/main/java
├── helper/ScreenShotHelper.java     captura de pantalla para el reporte
├── model/
│   ├── Employee.java                datos de un empleado
│   └── EmployeeRow.java             una fila de la grilla de resultados
├── pages/                           Page Objects (locators + acciones)
│   ├── BasePage.java                esperas comunes
│   ├── MenuPage.java                menú lateral (PIM)
│   ├── LoginPage.java
│   ├── DashboardPage.java
│   ├── EmployeeListPage.java        búsqueda y lectura de la grilla
│   ├── AddEmployeePage.java         formulario de alta + datos de usuario
│   └── PersonalDetailsPage.java
└── report/ReportManager.java        ExtentReports

src/test/java
├── base/BaseTest.java               abre/cierra el navegador según el parámetro de la suite
├── data/EmployeeDataProvider.java   lee employees.json y genera los datos únicos
├── employee/CreateAndSearchEmployeeTest.java   caso de negocio
├── employee/AddEmployeeTests.java, SearchEmployeeTests.java   pruebas extra (regresión)
└── login/LoginTests.java                                      pruebas extra (regresión)

src/test/resources
├── employees.json                   datos de los empleados
├── testng.xml                       suite del caso de negocio (Chrome + Edge)
└── regression.xml                   suite con las pruebas extra
```

### Reglas del Page Object Model

- La prueba no tiene locators ni búsquedas de elementos: se lee como el caso de negocio.
- Los locators son campos `private By` dentro de cada página, separados de las acciones.
- Las aserciones están en la prueba, nunca en las páginas.

## Datos de prueba

Los empleados se cargan desde `src/test/resources/employees.json`.

| Viene del archivo | Se genera al ejecutar |
|---|---|
| Nombre, segundo nombre | — |
| Apellido base (ej. `Perez`) | Sufijo aleatorio de letras → `PerezKqzmta` |
| Usuario base (ej. `jperez`) | Mismo sufijo en minúsculas → `jperezkqzmta` |
| Contraseña y estado (Enabled/Disabled) | — |
| — | ID de empleado (8 dígitos del timestamp + índice, máx. 10 caracteres) |

Así el nombre, el usuario y el ID son únicos en cada corrida, y la búsqueda devuelve un solo resultado.

## Cómo ejecutar las pruebas

Se necesita Java 11+, Maven, Google Chrome y Microsoft Edge.

Suite del caso de negocio (Chrome + Edge):

```bash
mvn test
```

Suite con las pruebas extra de login, alta y búsqueda:

```bash
mvn test -DsuiteXmlFile=src/test/resources/regression.xml
```

Para cambiar los navegadores, editar el parámetro `browser` en `testng.xml`. Valores soportados: `chrome`, `edge`, `firefox`, `safari`. En Safari primero hay que activar *Develop → Allow Remote Automation*.

## Reportes

Después de ejecutar las pruebas se genera un reporte HTML con ExtentReports en:

```text
reports/OrangeHRM.html              suite del caso de negocio
reports/OrangeHRM-regression.html   suite de regresión
```

Cada ejecución aparece con el navegador y el empleado usado, por ejemplo `testCreatedEmployeeAppearsInEmployeeList [edge] [Maria Elena GomezDsvtgy]`. Si una prueba falla, se adjunta una captura de pantalla.

Para verlo, abrir el archivo `.html` con cualquier navegador.
