package tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.UseCasePage;
import utils.BaseTest;
import utils.ExcelUtility;
import utils.ExtentFactory;
import utils.Screenshots;

import java.io.IOException;

import static org.testng.Assert.assertTrue;


public class UseCaseTest extends BaseTest {
    ExtentReports report;
    ExtentTest test;
    UseCasePage useCasePage;

    @BeforeClass
    public void beforeClass() throws Exception {
        report = ExtentFactory.getInstance();
        test = report.startTest("<b>Use Cases</b><br/>(" + getClass().getSimpleName() + ")");
        test.setDescription("This test will perform several Use case-related operations:</br>- Create use cases</br>- Edit use cases</br>- Delete use cases");
        useCasePage = new UseCasePage(driver);

        ExcelUtility.setExcelFile("src/main/resources/" + "UseCases.xlsx", "UseCases");
    }

    @Test
    public void goToUseCasesPage() {
        useCasePage.goToUseCasesPage();

        assertTrue(useCasePage.isUseCasesPage(), "It is not the 'Use Cases' page");
        test.log(LogStatus.PASS, "You are on the 'Use cases' page.");
    }

    @DataProvider(name = "useCases")
    public Object[][] dataProvider() {
        Object[][] testData = ExcelUtility.getTestData("UseCases");
        return testData;
    }

    //  CREATE/VERIFY USE CASES
    @Test(dependsOnMethods = "goToUseCasesPage", dataProvider = "useCases")
    public void createUseCase(String title, String description, String expectedResult, String stepId) {
        useCasePage.createUseCase(title, description, expectedResult, stepId);

        test.log(LogStatus.INFO, "Use cases are created.");
    }

    @Test(dependsOnMethods = "createUseCase")
    public void verifyIfUseCasesAreAdded() {
        assertTrue(useCasePage.verifyIfUseCasesAreAdded());

        test.log(LogStatus.PASS, "Use cases are added successfully.");
    }

    //  EDIT/VERIFY USE CASES
    @Test(dependsOnMethods = "verifyIfUseCasesAreAdded", dataProvider = "useCases")
    public void editUseCases(String title, String description, String expectedResult, String stepId) {
        useCasePage.editUseCase(title);

        test.log(LogStatus.INFO, "Use case editing is done.");
    }

    @Test(dependsOnMethods = "editUseCases")
    public void verifyIfUseCasesAreEdited(){
        assertTrue(useCasePage.verifyIfUseCasesAreEdited(), "Use cases are not edited.");

        test.log(LogStatus.PASS, "Use cases editing was successful!");
    }

    //  DELETE/VERIFY USE CASES
    @Test(dependsOnMethods = "verifyIfUseCasesAreEdited")
    public void deleteUseCase() {
        useCasePage.deleteUseCase();

        test.log(LogStatus.INFO, "Use cases deletion is done.");
    }

    @Test(dependsOnMethods = "deleteUseCase")
    public void verifyIfAddedUseCasesAreDeleted(){
        useCasePage.verifyIfAddedUseCasesAreDeleted();

        test.log(LogStatus.PASS, "Use cases deletion was successful!");
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
