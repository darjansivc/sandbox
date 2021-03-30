package loginTests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.UseCasePage;
import utils.BaseTest;
import utils.ExcelUtility;
import utils.ExtentFactory;

public class UseCaseTest extends BaseTest {
    ExtentReports report;
    ExtentTest test;
    UseCasePage useCasePage;

    @BeforeClass
    public void beforeClass() throws Exception {
        report = ExtentFactory.getInstance();
        test = report.startTest("<b>Use Cases</b><br/>(" + getClass().getSimpleName() + ")");
        test.setDescription("Create use cases");
        useCasePage = new UseCasePage(driver);


        String excelSheetName = "UseCases";
        String excelFilePath = "src/main/resources/";
        String excelFileName = "UseCases.xlsx";
        ExcelUtility.setExcelFile(excelFilePath + excelFileName, excelSheetName);
    }

    @Test
    public void goToUseCasesPage() throws InterruptedException {
        useCasePage.goToUseCasesPage();
        useCasePage.isUseCasesPage();
//        Thread.sleep(2000);
    }

    @DataProvider(name = "useCases")
    public Object[][] dataProvider() {
        Object[][] testData = ExcelUtility.getTestData("UseCases");
        return testData;
    }

//    @Test(dependsOnMethods = "goToUseCasesPage", dataProvider = "useCases")
//    public void createUseCase(String title, String description, String expectedResult, String stepId) {
//        useCasePage.createUseCase(title, description, expectedResult, stepId);
//
//    }

    //    @Test(dependsOnMethods = "createUseCase")
    @Test(dependsOnMethods = "goToUseCasesPage")
    public void editUseCase() {
        useCasePage.getUseCaseText();
    }

//    @Test(dependsOnMethods = "editUseCase", dataProvider = "useCases")
//    public void nesto(String title, String description, String expectedResult, String stepId) {
//        System.out.println("Just titles: " + title);
//    }

}
