package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.concurrent.ConcurrentHashMap;

public class ReportManager {

    private static ExtentReports extentReport;
    private static String reportPath;
    private static String reportName;
    private static ReportManager instance;
    private static ConcurrentHashMap<Long, ExtentTest> extentTestMap = new ConcurrentHashMap<>();

    private ReportManager() {
        extentReport = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        htmlReporter.config().setDocumentTitle("Automation Report " + reportName);
        htmlReporter.config().setReportName(reportName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("utf-8");
        extentReport.attachReporter(htmlReporter);
    }

    public static void init(String reportPath, String reportName) {
        if (extentReport == null) {
            ReportManager.reportPath = reportPath;
            ReportManager.reportName = reportName;
        }
    }

    public static ReportManager getInstance() {
        if (instance == null) {
            instance = new ReportManager();
        }
        return instance;
    }

    public ExtentTest startTest(String testName) {
        ExtentTest test = extentReport.createTest(testName);
        extentTestMap.put(Thread.currentThread().getId(), test);
        return test;
    }

    public ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().getId());
    }

    public void flush() {
        extentReport.flush();
    }
}
