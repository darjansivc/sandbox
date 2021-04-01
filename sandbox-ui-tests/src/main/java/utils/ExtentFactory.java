package utils;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
    private static String OS_PATH = BaseTest.setFolderPath();
    private static String REPORT_FOLDER = pages.utils.BasePage.getCurrentTimeAndDate();
    private static String REPORT_NAME = pages.utils.BasePage.getCurrentTimeAndDate();


    public static String getCurrentReportFolderPath() {
        return "/" + currentReportFolderPath;
    }

    private static String currentReportFolderPath = "";

    public static String getCurrentReportFolderPathForScreenShot() {
        return currentReportFolderPathForScreenShot;
    }

    private static String currentReportFolderPathForScreenShot = "";

    public static ExtentReports getInstance() {
        ExtentReports extent;

        String reportFolderPath = OS_PATH + REPORT_FOLDER + "/";
        currentReportFolderPath = reportFolderPath;

        currentReportFolderPathForScreenShot = REPORT_FOLDER + "/";

        String Path = reportFolderPath + REPORT_NAME + ".html";
        extent = new ExtentReports(Path, false);
        extent.addSystemInfo("Selenium Version", "3.7.1").addSystemInfo("Platform", System.getProperty("os.name"));

        return extent;
    }
}