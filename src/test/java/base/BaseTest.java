package base;

import com.aventstack.extentreports.Status;
import helper.ScreenShotHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import report.ReportManager;

public abstract class BaseTest {

    protected WebDriver webDriver;

    private String baseUrl = System.getProperty("baseUrl", "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    private String browser = System.getProperty("browser", "chrome");

    @BeforeSuite
    public static void setUpSuite() {
        ReportManager.init("target/reports/OrangeHRM.html", "OrangeHRM");
    }

    @BeforeMethod
    public void setUp(ITestResult iTestResult) throws Exception {
        ReportManager.getInstance().startTest(iTestResult.getMethod().getMethodName());

        switch (browser.toLowerCase()) {
            case "chrome":
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
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
