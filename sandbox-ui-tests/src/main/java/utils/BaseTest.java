package utils;

import utils.ExtentFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import utils.PropertyFile;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class BaseTest {
    public static WebDriver driver;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private final String chromeDriverWin = new PropertyFile().get("chromeDriverPath");
    private final String chromeDriverMac = new PropertyFile().get("chromeDriverPathMac");
    private final String chromeDriverLinux = new PropertyFile().get("chromeDriverLinux");

    private final String firefoxDriverWin = new PropertyFile().get("firefoxDriverPath");
    private final String firefoxDriverMac = new PropertyFile().get("firefoxDriverPathMac");
    private final String firefoxDriverLinux = new PropertyFile().get("firefoxDriverLinux");

    private final String internetExplorerDriver = new PropertyFile().get("internetExplorerDriverPath");

    private static final Logger log = LogManager.getLogger("Base");

    @BeforeSuite
//    @Parameters({"browser", "baseUrl", "loginLink"})
    public void setUp() {
        driver = createDriver("Chrome", "https://qa-sandbox.apps.htec.rs/","login");
//        log.info("Login link: " + baseUrl + loginLink);
    }

    private WebDriver createDriver(String browserName, String baseUrl, String loginLink) {
        if (browserName.toUpperCase().equals("FIREFOX"))
            return firefoxDriver(baseUrl, loginLink);

        if (browserName.toUpperCase().equals("CHROME"))
            return chromeDriver(baseUrl, loginLink);

        if (browserName.toUpperCase().equals("IE"))
            return internetExplorerDriver(baseUrl, loginLink);

        throw new RuntimeException("invalid browser name");
    }

    @Parameters({"baseUrl", "loginLink"})
    private WebDriver chromeDriver(String baseUrl, String loginLink){
        if (isWindows()) {
            return loadChromeWebDriver(chromeDriverWin,baseUrl, loginLink);
        }
        else if (isMac()){
            return loadChromeWebDriver(chromeDriverMac,baseUrl, loginLink);
        }
        else if (isLinux()){
            return loadChromeWebDriver(chromeDriverLinux,baseUrl, loginLink);
        }
        return null;
    }

    private WebDriver loadChromeWebDriver(String webDriverLocation, String baseUrl, String loginLink){
        if (!new File(webDriverLocation).exists()){
            try {
                System.out.println("Driver exists on this path: " + webDriverLocation);
            }catch (Exception ex){
                throw new RuntimeException("WebDriver does not exist!" + ex);
            }
        }


        try {
            System.setProperty("webdriver.chrome.driver", webDriverLocation);
            System.setProperty("webdriver.chrome.logfile", "C:\\extent-reports\\chromedriver.log");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            ChromeOptions options = new ChromeOptions();

            boolean isDebug =
                    java.lang.management.ManagementFactory.getRuntimeMXBean().
                            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
            if(!isDebug) {
//                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");
            }
            options.addArguments("--disable-gpu");

            options.addArguments("disable-infobars");
            options.addArguments("--start-maximized");

            driver = new ChromeDriver(options);
            driver.get(baseUrl + loginLink);

            return driver;

        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create chrome driver" + ex);
        }
    }

    @Parameters({"baseUrl", "loginLink"})
    private WebDriver firefoxDriver(String baseUrl, String loginLink){
        if (isWindows()) {
            return loadFirefoxWebDriver(firefoxDriverWin,baseUrl, loginLink);
        }
        else if (isMac()){
            return loadFirefoxWebDriver(firefoxDriverMac,baseUrl, loginLink);
        }
        else if (isLinux()){
            return loadFirefoxWebDriver(firefoxDriverLinux,baseUrl, loginLink);
        }
        return null;
    }

    private WebDriver loadFirefoxWebDriver(String webDriverLocation, String baseUrl, String loginLink) {
        if (!new File(webDriverLocation).exists()) {
            try {
                System.out.println("Firefox driver exist on this path: " + webDriverLocation);
            } catch (Exception ex) {
                throw new RuntimeException("geckodriver does not exist!" + ex);
            }
        }

        try {
            System.setProperty("webdriver.gecko.driver", webDriverLocation);
            FirefoxOptions options = new FirefoxOptions();

            boolean isDebug =
                    java.lang.management.ManagementFactory.getRuntimeMXBean().
                            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
            if(!isDebug) {
                options.setHeadless(true);
//                options.addArguments("--headless");
                options.addArguments("--width=1920");
                options.addArguments("--height=1080");

            }

//            options.addArguments("-headless");

            driver = new FirefoxDriver(options);

            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//            driver.manage().window().maximize();
            driver.get(baseUrl + loginLink);
            return driver;
        } catch (Exception ex) {
            throw new RuntimeException("could not create the firefox driver" + ex);
        }
    }

    private static boolean isWindows() {
        return (OS.contains("win"));
    }

    private static boolean isMac() {
        return (OS.contains("mac"));
    }

    private static boolean isLinux(){
        return (OS.contains("linux"));
    }

    @Parameters({"baseUrl", "loginLink"})
    private WebDriver internetExplorerDriver(String baseUrl, String loginLink) {
        if (!new File(internetExplorerDriver).exists())
            throw new RuntimeException("iedriver does not exist!");

        try {
            System.setProperty("webdriver.ie.driver", internetExplorerDriver);

            driver = new InternetExplorerDriver();
            driver.get(baseUrl + loginLink);

            return driver;

        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create IE driver");
        }
    }

    @AfterSuite
    public void tearDown() {
        // SendReportToEmail sendReport = new SendReportToEmail();
        driver.quit();

        System.out.println("[INFO]Test results folder: " + System.getProperty("user.dir") + ExtentFactory.getCurrentReportFolderPath());
        // sendReport.SendReportAfterSuite();

    }

    public static String setFolderPath(){
        if(isWindows()){
            return "\\extent-reports\\";
        }
        else if (isLinux()){
            return "extent-reports/";
        }
        else if (isMac()){
            return "extent-reports/";
        }
        return null;
    }
}
