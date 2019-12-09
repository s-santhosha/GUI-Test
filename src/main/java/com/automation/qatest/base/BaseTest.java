package com.automation.qatest.base;

import com.automation.qatest.excelReader.ExcelReader;
import com.automation.qatest.utils.ExtentReportFactory;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class BaseTest implements IHookable {

    public static WebDriver driver;
    public static Properties prop;
    public static long PAGE_LOAD_TIMEOUT = 20;
    public static long IMPLICIT_WAIT = 15;

    protected ExtentTest testReporter;

    ExcelReader excel;

    public BaseTest() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initialization() {
        String browserName = prop.getProperty("browser");

        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\browserDrivers\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\main\\resources\\browserDrivers\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browserName.equals("headless")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/browserDrivers/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors");
            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(prop.getProperty("url"));

    }

    @AfterMethod
    public void cleanUp(ITestResult result, Method method) {
        String testname = getTestName(result);
        testReporter.log(LogStatus.INFO, "After test:" + testname);
        if (result.getStatus() == ITestResult.FAILURE) {
            testReporter.log(LogStatus.FAIL, "Test Failed: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            testReporter.log(LogStatus.PASS, "Test Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            testReporter.log(LogStatus.SKIP, "Test Skipped");
        } else {
            testReporter.log(LogStatus.ERROR, "Error while executing test");
        }
        testReporter = null;
        ExtentReportFactory.closeTest(testname);
//        driver.close();

    }


    private String getTestName(ITestResult testResult) {
        String name = testResult.getName();
        Object[] parameters = testResult.getParameters();
        StringBuilder testName = new StringBuilder();

        testName.append(name);
        testName.append("(");
        for (int i = 0; i < parameters.length; i++) {
            testName.append("[" + parameters[i].toString() + "]");
            if (i != parameters.length - 1) {
                testName.append(",");
            }
        }
        testName.append(")");

        return testName.toString();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportFactory.closeReport();
    }

    public void run(IHookCallBack callBack, ITestResult testResult) {
        String description = testResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description();
        String testName = getTestName(testResult);
        ExtentReportFactory.getTest(testName, description);
        testReporter = ExtentReportFactory.getTest();
        testReporter.log(LogStatus.INFO, "Started test :" + testName);
        System.out.println("Method: " + testName);
        callBack.runTestMethod(testResult);

    }

    //Load all the data in the String[][] using the getDataFromSheet method of ExcelReader class
    public String[][] getData(String sheetname, String excelname) {
        String path = System.getProperty("user.dir") + "/src/main/resources/testData/" + excelname;
        excel = new ExcelReader(path);
        String[][] data = excel.getDataFromSheet(sheetname, excelname);
        return data;
    }


    public void waitforElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitOnClick(By locator, WebDriver driver)
    {
        final WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(locator)));
        driver.findElement(locator).click();
    }


    public void tearDownBrowserDriver() {
        driver.quit();
    }


}
