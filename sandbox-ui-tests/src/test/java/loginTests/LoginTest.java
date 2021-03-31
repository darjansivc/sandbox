package loginTests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import test_data.PageLink;
import pages.utils.BasePage;
import utils.BaseTest;
import utils.ExtentFactory;
import utils.Screenshots;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static pages.utils.BasePage.waitForElementToBeVisible;

public class LoginTest extends BaseTest {
    ExtentReports report;
    ExtentTest test;
    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeClass
    public void beforeClass() {
        report = ExtentFactory.getInstance();
        test = report.startTest("<b>Login - Enter Credentials</b><br/>(" + getClass().getSimpleName() + ")");
        test.setDescription("First step in Login process - Entering the user name and password.<br/>This test will:<br/>" +
                "- Go to ZUGSEIL login page.</br>- Enter USERNAME and PASSWORD<br/>");
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);

    }

    @Test
    public void login() {
        loginPage.setUserNameAndPassword();
        test.log(LogStatus.PASS, "Test Passed");
    }

    @Test(dependsOnMethods = "login")
    public void verify() {
        waitForElementToBeVisible(dashboardPage.getUseCasesCard());
        dashboardPage.isInitialized();

        assertEquals(driver.getCurrentUrl(), PageLink.DASHBOARD);
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
