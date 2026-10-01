package base;

import com.aventstack.extentreports.Status;
import helper.ScreenShotHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import report.ReportManager;

import java.util.Arrays;

public abstract class BaseTest {

    protected WebDriver webDriver;

    private String baseUrl = System.getProperty("baseUrl", "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    private String browser;

    // El nombre del reporte se puede cambiar por suite con <parameter name="reportName" .../>
    @BeforeSuite
    @Parameters("reportName")
    public static void setUpSuite(@Optional("OrangeHRM") String reportName) {
        ReportManager.init("reports/" + reportName + ".html", "OrangeHRM");
    }

    // El navegador llega como parametro desde el testng.xml; si no hay, se usa -Dbrowser o chrome
    @BeforeClass
    @Parameters("browser")
    public void setUpBrowser(@Optional("") String browser) {
        this.browser = browser.isEmpty() ? System.getProperty("browser", "chrome") : browser;
    }

    @BeforeMethod
    public void setUp(ITestResult iTestResult) throws Exception {
        String testName = iTestResult.getMethod().getMethodName() + " [" + browser + "]";
        if (iTestResult.getParameters().length > 0) {
            testName += " " + Arrays.toString(iTestResult.getParameters());
        }
        ReportManager.getInstance().startTest(testName).assignCategory(browser);

        switch (browser.toLowerCase()) {
            case "chrome":
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;
            case "edge":
                webDriver = new EdgeDriver();
                break;
            case "safari":
                webDriver = new SafariDriver();
                break;
            default:
                throw new Exception(browser + " no soportado");
        }

        webDriver.manage().window().maximize();
        webDriver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown(ITestResult iTestResult) {
        try {
            switch (iTestResult.getStatus()) {
                case ITestResult.FAILURE:
                    ReportManager.getInstance().getTest().log(Status.FAIL, "Test failed");
                    if (iTestResult.getThrowable() != null) {
                        ReportManager.getInstance().getTest().log(Status.FAIL, iTestResult.getThrowable().getMessage());
                    }
                    ScreenShotHelper.takeScreenShotAndAddToHTMLReport(webDriver, Status.FAIL, "Failure image");
                    break;
                case ITestResult.SKIP:
                    ReportManager.getInstance().getTest().log(Status.SKIP, "Test skipped");
                    break;
                case ITestResult.SUCCESS:
                    ReportManager.getInstance().getTest().log(Status.PASS, "Test passed");
                    break;
                default:
                    ReportManager.getInstance().getTest().log(Status.FAIL, "Test incomplete");
                    break;
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
    }

    @AfterSuite
    public static void tearDownSuite() {
        ReportManager.getInstance().flush();
    }
}
