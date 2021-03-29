package utils;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
    private static String osPath = BaseTest.setFolderPath();
    private static String reportFolder = pages.utils.BasePage.getCurrentTimeAndDate();
    private static String reportName = pages.utils.BasePage.getCurrentTimeAndDate();


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

        String reportFolderPath = osPath + reportFolder + "/";
        currentReportFolderPath = reportFolderPath;

        currentReportFolderPathForScreenShot = reportFolder + "/";

        String Path = reportFolderPath + reportName + ".html";
        extent = new ExtentReports(Path, false);
        extent.addSystemInfo("Selenium Version", "3.7.1").addSystemInfo("Platform", System.getProperty("os.name"));

        return extent;
    }
}