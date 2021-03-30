package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshots {
    private static String osPath = BaseTest.setFolderPath();
    private static String currentScreenShotReportFolder = ExtentFactory.getCurrentReportFolderPathForScreenShot();

    public static String takeScreenshot(WebDriver driver, String fileName) throws IOException {
        fileName = fileName + ".png";
//        String directory = currentReportFolder + "screenshoots\\";
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(sourceFile, new File(System.getProperty("user.dir") + "/" + osPath + currentScreenShotReportFolder + fileName));
        String destination = fileName;
        return destination;
    }
}