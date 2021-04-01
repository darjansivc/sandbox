package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExtentFactory;
import utils.Screenshots;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest {
    ExtentReports report;
    ExtentTest test;
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeClass
    public void beforeClass() {
        report = ExtentFactory.getInstance();
        test = report.startTest("<b>Login - Enter Credentials</b><br/>(" + getClass().getSimpleName() + ")");
        test.setDescription("This test will login user into 'QA Sandbox' application. Steps:<br/>" +
                "- Go to QA Sandbox login page.</br>- Enter USERNAME and PASSWORD<br/>- Login</br>- Verify login ");
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @Test
    public void login() {
        loginPage.setUserNameAndPasswordAndLogin();
        test.log(LogStatus.INFO, "Login credentials entered and 'Submit' button clicked.");
    }

    @Test(dependsOnMethods = "login")
    public void verifyIfLoginWasSuccessful() {
        assertTrue(dashboardPage.isDashboardSection());
        test.log(LogStatus.PASS, "URL belongs to 'Dashboard' page.");

        assertTrue(dashboardPage.isDashboardPage(), "'Dashboard' is not displayed as expected.");
        test.log(LogStatus.PASS, "'Dashboard' page is displayed.");

        test.log(LogStatus.PASS, "Login was successful!");

    }

    @AfterMethod
    public void reporting(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String screenshotName = testResult.getName();
            String path = Screenshots.takeScreenshot(driver, screenshotName);
            String imagePath = test.addScreenCapture(path);
            String methodName = testResult.getName().toUpperCase();
            test.log(LogStatus.FAIL, "<b>[" + methodName + "]</b>" + "</br>" + testResult.getThrowable());
            test.log(LogStatus.FAIL, "<b>[" + methodName + "]</b>" + imagePath);
        }

        report.endTest(test);
        report.flush();
    }
}
