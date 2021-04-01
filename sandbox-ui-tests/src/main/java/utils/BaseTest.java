package utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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

    private final String browserName = new PropertyFile().get("browserName");
    private final String baseUrl = new PropertyFile().get("baseUrl");
    private final String loginLink = new PropertyFile().get("loginLink");


    @BeforeSuite
    public void setUp() {
        driver = createDriver(browserName, baseUrl, loginLink);
    }

    private WebDriver createDriver(String browserName, String baseUrl, String loginLink) {

        if (browserName.equalsIgnoreCase("CHROME")) {
            return chromeDriver(baseUrl, loginLink);
        } else if (browserName.equalsIgnoreCase("FIREFOX")) {
            return firefoxDriver(baseUrl, loginLink);
        } else {
            throw new RuntimeException("invalid browser name");
        }

    }

    private WebDriver chromeDriver(String baseUrl, String loginLink) {
        if (isWindows()) {
            return loadChromeWebDriver(chromeDriverWin, baseUrl, loginLink);
        } else if (isMac()) {
            return loadChromeWebDriver(chromeDriverMac, baseUrl, loginLink);
        } else if (isLinux()) {
            return loadChromeWebDriver(chromeDriverLinux, baseUrl, loginLink);
        }
        return null;
    }

    private WebDriver loadChromeWebDriver(String webDriverLocation, String baseUrl, String loginLink) {
        if (!new File(webDriverLocation).exists()) {
            try {
                System.out.println("Driver exists on this path: " + webDriverLocation);
            } catch (Exception ex) {
                throw new RuntimeException("WebDriver does not exist!" + ex);
            }
        }

        try {
            System.setProperty("webdriver.chrome.driver", webDriverLocation);
            System.setProperty("webdriver.chrome.logfile", "logs/chromedriver.log");
            System.setProperty("webdriver.chrome.verboseLogging", "true");
            ChromeOptions options = new ChromeOptions();

            boolean isDebug =
                    java.lang.management.ManagementFactory.getRuntimeMXBean().
                            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
            if (!isDebug) {
//                options.addArguments("--headless");
            }
            options.addArguments("--window-size=1920,1080");

            options.addArguments("--disable-gpu");

            options.addArguments("disable-infobars");

            driver = new ChromeDriver(options);
            driver.get(baseUrl + loginLink);

            return driver;

        } catch (Exception ex) {
            throw new RuntimeException("Couldn't create chrome driver" + ex);
        }
    }

    private WebDriver firefoxDriver(String baseUrl, String loginLink) {
        if (isWindows()) {
            return loadFirefoxWebDriver(firefoxDriverWin, baseUrl, loginLink);
        } else if (isMac()) {
            return loadFirefoxWebDriver(firefoxDriverMac, baseUrl, loginLink);
        } else if (isLinux()) {
            return loadFirefoxWebDriver(firefoxDriverLinux, baseUrl, loginLink);
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
            if (!isDebug) {
                options.setHeadless(true);
//                options.addArguments("--headless");
            }
            options.addArguments("--window-size=1920,1080");
            driver = new FirefoxDriver(options);

            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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

    private static boolean isLinux() {
        return (OS.contains("linux"));
    }


    @AfterSuite
    public void tearDown() {
        driver.quit();

        System.out.println("[INFO]Test results folder: " + System.getProperty("user.dir") + ExtentFactory.getCurrentReportFolderPath());
    }

    public static String setFolderPath() {
        if (isWindows()) {
            return "\\extent-reports\\";
        } else if (isLinux()) {
            return "extent-reports/";
        } else if (isMac()) {
            return "extent-reports/";
        }
        return null;
    }
}
