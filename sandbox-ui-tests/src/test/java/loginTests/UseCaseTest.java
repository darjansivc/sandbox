package loginTests;

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
        test.setDescription("This test will: \ncreate use cases\n edit use cases\n delete use cases");
        useCasePage = new UseCasePage(driver);


//        String excelSheetName = "UseCases";
//        String excelFilePath = "src/main/resources/";
//        String excelFileName = "UseCases.xlsx";
        ExcelUtility.setExcelFile("src/main/resources/" + "UseCases.xlsx", "UseCases");
    }

    @Test
    public void goToUseCasesPage() {
        useCasePage.goToUseCasesPage();
        assertTrue(useCasePage.isUseCasesPage(), "It is not 'Use Cases' page");
    }

    @DataProvider(name = "useCases")
    public Object[][] dataProvider() {
        Object[][] testData = ExcelUtility.getTestData("UseCases");
        return testData;
    }

    @Test(dependsOnMethods = "goToUseCasesPage", dataProvider = "useCases")
    public void createUseCase(String title, String description, String expectedResult, String stepId) {
        useCasePage.createUseCase(title, description, expectedResult, stepId);
        useCasePage.getAddedUseCasesTitles(title);
        test.log(LogStatus.INFO, "Use cases are added.");
    }

    @Test(dependsOnMethods = "createUseCase")
    public void areCasesAvailable() {
        assertTrue(useCasePage.darjanVerifyIfUseCasesAreAdded());
        test.log(LogStatus.PASS, "Use cases are successfully added.");

    }

    @Test(dependsOnMethods = "createUseCase", dataProvider = "useCases")
    public void editUseCases(String title, String description, String expectedResult, String stepId) {
        useCasePage.editUseCase(title);
//        useCasePage.verifyIfUseCaseTitlesAreChanged();
    }

    @Test(dependsOnMethods = "editUseCases")
    public void deleteUseCase() {
        useCasePage.deleteUseCase();
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
