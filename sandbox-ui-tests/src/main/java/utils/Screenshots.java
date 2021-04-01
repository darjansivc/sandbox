package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshots {
    private static String OS_PATH = BaseTest.setFolderPath();
    private static String CURRENT_SCREENSHOT_REPORT_FOLDER = ExtentFactory.getCurrentReportFolderPathForScreenShot();

    public static String takeScreenshot(WebDriver driver, String fileName) throws IOException {
        fileName = fileName + ".png";
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir") + "/" + OS_PATH + CURRENT_SCREENSHOT_REPORT_FOLDER + fileName));
        String destination = fileName;
        return destination;
    }
}