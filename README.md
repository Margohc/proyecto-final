# Proyecto final

Proyecto de automatización web para la página OrangeHRM Demo, desarrollado con Java, Maven, Selenium WebDriver y TestNG.

## Objetivo

Automatizar pruebas del flujo de login, agregar empleado nuevo, buscar empleado y verificar que aparece en la grilla aplicando el patrón Page Object Model.

Página utilizada:

https://opensource-demo.orangehrmlive.com

## Tecnologías

- Java 11
- Maven
- Selenium WebDriver
- TestNG
- ExtentReports
- IntelliJ IDEA
- Chrome
- Firefox

## Estructura del proyecto


## Datos de prueba

Los datos base de los empleados se encuentran en :

```text
src/test/resources/employee-data.csv
```

Un `DataProvider` lee el firstName, middleName y lastName desde ese archivo. Las pruebas de alta y busqueda se ejecutan con los dos empleados configurados. Para evitar empleados duplicados entre corridas y navegadores, al lastName base se le agrega la marca de tiempo actual durante la ejecucion.

## Casos de prueba incluidos

### Login exitoso

Valida que un usuario con credenciales correctas pueda ingresar al sistema y visualizar el Dashboard.

Credenciales:

```text
Admin / admin123
```

### Credenciales inválidas

Valida que se muestre el mensaje:

```text
Invalid credentials
```

Casos cubiertos:

- usuario inválido y password inválido
- usuario inválido y password válido
- usuario válido y password inválido

### Campos requeridos

Valida que se muestre el mensaje:

```text
Required
```

Casos cubiertos:

- Username vacío
- password vacío
- username y password vacíos
- username con espacios
- password con espacios

## Page Objects

### BasePage

Contiene métodos comunes para las páginas:

- esperar visibilidad
- validar si un elemento está visible
- obtener texto de un elemento

### LoginPage

Representa la página de login.

Incluye acciones como:

- escribir username
- escribir password
- hacer clic en Login
- enviar formulario vacío
- validar mensajes de error

### DashboardPage

Representa la página principal después de un login exitoso.

Valida que el título:

```text
Dashboard
```

esté visible.

## Reportes

El proyecto genera un reporte HTML con ExtentReports en:

```text
target/reports/OrangeHRM.html
```

Si una prueba falla, se adjunta una captura de pantalla al reporte.

### Cómo ver el reporte

Primero se deben ejecutar las pruebas. Luego abrir el archivo:

```text
target/reports/OrangeHRM.html
```

Desde IntelliJ IDEA:

1. Abrir la carpeta `target`.
2. Abrir la carpeta `reports`.
3. Hacer clic derecho sobre `OrangeHRM.html`.
4. Seleccionar `Open in Browser`.

Desde Finder:

1. Ir a la carpeta del proyecto.
2. Abrir `target/reports`.
3. Abrir el archivo `OrangeHRM.html` con el navegador.

## Cómo ejecutar las pruebas

Desde IntelliJ IDEA:

1. Abrir el proyecto.
2. Verificar que el SDK sea Java 11.
3. Abrir `LoginTests.java`.
4. Ejecutar la clase completa o un test individual.

Desde Maven:

```bash
mvn test
```

Este comando ejecutara la suite completa en secuencia en Chrome y Firefox.

También se puede ejecutar usando el `testng.xml`:

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## Configuración importante

El proyecto debe ejecutarse con Java 11.

En IntelliJ IDEA configurar:

```text
Project SDK: openjdk-11
Language level: 11
```

Chrome y Firefox deben estar instalados en el equipo.

\
